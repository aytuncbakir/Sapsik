package com.sivamalabrothers.sapsik;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Tahta extends AppCompatActivity implements View.OnClickListener {

    private int tutulanSayi = 0;
    private ArrayList<String> datalar;
    private ArrayList<Integer> oncekiTahminler;
    String gorevText ="";

    Button sayiTut, tamam,nasil,reset;
    TextView aralikGoster;
    EditText tahminText;
    TextView txtsapsik;
    int gorev = 0;
    private int altAralik = -1,ustAralik= -1;

    private static final int  CUSTOM_DIALOG_ID = 1;
    private static final int  CUSTOM_DIALOG_ID1 = 2;

    private AdView adView,adView1;
    LinearLayout reklam_layout,reklam_layout1;
    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/8765023215";
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/8190308142";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tahta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Make to run your application only in LANDSCAPE mode
        //setContentView(R.layout.disable_android_orientation_change);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ilklendirmeleriYap();
        animasyonUygula();
        reklam_yukle();

    }

    private void ilklendirmeleriYap(){

        nasil = findViewById(R.id.nasil);
        nasil.setOnClickListener(this);
        sayiTut = findViewById(R.id.sayiTut);
        sayiTut.setOnClickListener(this);
        tamam = findViewById(R.id.tamam);
        tamam.setOnClickListener(this);

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(this);


        aralikGoster = findViewById(R.id.aralikGoster);
        tahminText = findViewById(R.id.tahminText);

        oncekiTahminler = new ArrayList<>();

        dosyaIslemleri();

    }

    private void dosyaIslemleri(){


        DosyaOku dosyaOku = new DosyaOku(this);
        datalar = dosyaOku.dosyadanyukle("sapsik.txt");


    }


    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Slide enterTransition = new Slide();
            enterTransition.setDuration(300);
            enterTransition.setSlideEdge(Gravity.BOTTOM);
            getWindow().setEnterTransition(enterTransition);
        }
    }
    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
        if(Build.VERSION.SDK_INT >= 21)
            finishAfterTransition();
        else
            finish();
        return true;
    }

    private void reklam_yukle(){

        reklam_layout = findViewById(R.id.reklam_layout);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);

        reklam_layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        if(getScreenHeight()>1200) {
            reklam_layout1 = findViewById(R.id.reklam_layout1);

            adView1 = new AdView(this);
            adView1.setAdSize(AdSize.BANNER);
            adView1.setAdUnitId(REKLAM_ID1);

            reklam_layout1.addView(adView1);

            AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            adView1.loadAd(adRequest1);
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id){
            case R.id.sayiTut:
                oncekiTahminler.removeAll(oncekiTahminler);
                aralikGoster.setText("0 - 1000");
                tahminText.setText("");
                generateRandomNumber();
                sayiTut.setEnabled(false);
                        break;
            case R.id.tamam:

                if(!tahminText.getText().toString().equals("")) {
                    int kontrol = Integer.valueOf(tahminText.getText().toString());
                    if(kontrol == tutulanSayi){
                        aralikGoster.setText("Bildin Şapşik!");
                        setGorev();
                        showDialog(CUSTOM_DIALOG_ID);
                        sayiTut.setEnabled(true);
                    }else {

                        if (kontrol < altAralik && altAralik != -1 || kontrol > ustAralik && ustAralik != -1)
                            Toast.makeText(getApplicationContext(), "Aralıkta tahmin giriniz!", Toast.LENGTH_LONG).show();
                        else
                            kontrolEt(kontrol);
                    }

                }else
                    Toast.makeText( getApplicationContext(), "Tahmin giriniz!", Toast.LENGTH_LONG).show();
                 break;
            case R.id.nasil:
                showDialog(CUSTOM_DIALOG_ID1);
                break;
            case R.id.reset:
                oncekiTahminler.removeAll(oncekiTahminler);
                sayiTut.setEnabled(true);
                aralikGoster.setText("Başlamak için sayı tut.");
                tahminText.setText("");
                altAralik = -1;
                ustAralik = -1;
                tutulanSayi = 0;

                break;
        }

    }



    private void kontrolEt(int tahmin){

        if(oncekiTahminler.size() == 0) {
            if (tahmin == tutulanSayi) {
                aralikGoster.setText("Bildin Şapşik!");
                setGorev();
                showDialog(CUSTOM_DIALOG_ID);
                sayiTut.setEnabled(true);
            }else if (tahmin < tutulanSayi){
                aralikGoster.setText(tahmin+" - "+"1000");
                oncekiTahminler.add(1000);
            }else  if (tahmin > tutulanSayi){
                aralikGoster.setText("0"+" - "+tahmin);
                oncekiTahminler.add(0);
            }
           oncekiTahminler.add(tahmin);
        }else{
            if (tahmin == tutulanSayi) {
                aralikGoster.setText("Bildin Şapşik!");
                setGorev();
                showDialog(CUSTOM_DIALOG_ID);
                sayiTut.setEnabled(true);
            }else{
                oncekiTahminler.add(tahmin);

                araligiAyarla(oncekiTahminler);
            }
        }

    }

    private void araligiAyarla(ArrayList<Integer> oncekiTahminler){

        ArrayList<Integer> gecici = new ArrayList<>();
        gecici.removeAll(gecici);
        for(int i = 0; i < oncekiTahminler.size(); i++)
            gecici.add(oncekiTahminler.get(i));

        gecici.add(tutulanSayi);

        gecici = sortGecici(gecici);

        String s="";
        for(int i = 0; i<gecici.size();i++){
             s= s+ gecici.get(i)+" ";


        }
        //Toast.makeText( getApplicationContext(), s+"", Toast.LENGTH_SHORT).show();

        int konum = 0;
        for(int i = 0; i<gecici.size();i++){
            if(gecici.get(i)== tutulanSayi) {
                konum = i;
                break;
            }
        }
        altAralik = gecici.get(konum-1);
        ustAralik = gecici.get(konum+1);
        aralikGoster.setText(gecici.get(konum-1)+" - "+gecici.get(konum+1));
    }

    private ArrayList<Integer> sortGecici(ArrayList<Integer> list){
        int temp = 0;
        if (list.size()>1) // check if the number of orders is larger than 1
        {
            for (int i= list.size()-1; i>=0; i--) // bubble sort outer loop
            {
                for (int j=0; j < i; j++) {
                    if (list.get(j) > list.get(j+1) )
                    {
                        temp = list.get(j);
                        list.set(j,list.get(j+1) );
                        list.set(j+1, temp);
                    }
                }
            }
        }
        return  list;

    }

    private void generateRandomNumber() {

        Random rand = new Random();
        tutulanSayi = rand.nextInt(1000) + 1;
        //Toast.makeText( getApplicationContext(), tutulanSayi+"", Toast.LENGTH_SHORT).show();

    }

    private void generateRandomNumber(int size) {

        Random rand = new Random();
        gorev = rand.nextInt(size-1)+1;
        //Toast.makeText( getApplicationContext(), gorev+"  gorev ", Toast.LENGTH_SHORT).show();

    }


    public static int getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private void setGorev(){

        if(datalar.size() > 0) {

           generateRandomNumber(datalar.size());
           gorevText = "";
           gorevText  = datalar.get(gorev) ;
           //Toast.makeText( getApplicationContext(), gorevText+"  gorevText ", Toast.LENGTH_SHORT).show();

            datalar.remove(gorev);

            if(datalar.size() < 5)
                dosyaIslemleri();
        }
    }

    @Override
    protected Dialog onCreateDialog(final int id) {

        switch (id){

            case CUSTOM_DIALOG_ID1:

                final Dialog kaydetDialog = new Dialog(this);


                kaydetDialog.setContentView(R.layout.bilgilendirme);
                kaydetDialog.setTitle(Html.fromHtml(getResources().getString(R.string.html_title)));

                final EditText bilgilendirme = kaydetDialog.findViewById(R.id.bilgilendirme);
                String s = "Sayı tut dediğinizde oyun 1 ile 1000 arasında bir sayı tutar. " +
                        "Amaç tutulan sayıyı tahmin etmemektir. " +
                        "Her tahmin sonrası sırası gelen oyunucu verilen aralıkta yeni tahmin yapmalıdır. " +
                        "Sayıyı tahmin eden şapşik olur, şapşik verilecek görevi yapmak zorundadır. " +
                        "Yeni oyun şapşikten başlar belirlediğiniz sıraya göre devam eder. " +
                        "Oyun en az iki kişi ile oynanır. " +
                        "Üst limit yoktur ama en fazla 8 kişi ile oynanması tavsiye olunur.";
                bilgilendirme.setText(s);

                return kaydetDialog;
            case CUSTOM_DIALOG_ID:

                final Dialog kaydetDialog1 = new Dialog(this);
                kaydetDialog1.setCancelable(false);
                kaydetDialog1.setCanceledOnTouchOutside(false);


                kaydetDialog1.setContentView(R.layout.sapsik);
                TextView txttitle = kaydetDialog1.findViewById(R.id.txttitle);
                ImageView imgsapsik = kaydetDialog1.findViewById(R.id.imgsapsik);
                txtsapsik = kaydetDialog1.findViewById(R.id.txtsapsik);
                Button tmmsapsik = kaydetDialog1.findViewById(R.id.tmmsapsik);

                txtsapsik.setText(gorevText);

                txttitle.setText(Html.fromHtml(getResources().getString(R.string.html_title1)));

               tmmsapsik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        removeDialog(1);

                    }
                });
                return kaydetDialog1;
            default:
                return super.onCreateDialog(id);
        }

    }

}

