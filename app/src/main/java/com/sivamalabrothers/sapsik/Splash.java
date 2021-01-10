package com.sivamalabrothers.sapsik;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.Random;


public class Splash extends AppCompatActivity implements Animation.AnimationListener {



    private TextView tv1;
    private ImageView img;
    private Animation mTextAnim;
    private Animation mImgAnim;

    private AdView adView,adView1;
    LinearLayout reklam_layout,reklam_layout1;
    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/5791375510";
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/5759585929";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Make to run your application only in LANDSCAPE mode
        //setContentView(R.layout.disable_android_orientation_change);
        setContentView(R.layout.splash);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        img = findViewById(R.id.img);
        tv1 = findViewById(R.id.tv1);

        spanText();
        scaleAnimation(tv1,img);
        reklam_yukle();
    }



    private void scaleAnimation(View view, View view2){

        mTextAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text);
        mTextAnim.setAnimationListener(this);
        tv1.startAnimation(mTextAnim);


        mImgAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text);
        mImgAnim.setAnimationListener(this);
        img.startAnimation(mImgAnim);


    }


    private void reklam_yukle(){

        reklam_layout = findViewById(R.id.reklam_layout);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);

        reklam_layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        reklam_layout1 = findViewById(R.id.reklam_layout1);

        adView1 = new AdView(this);
        adView1.setAdSize(AdSize.BANNER);
        adView1.setAdUnitId(REKLAM_ID1);

        reklam_layout1.addView(adView1);

        AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView1.loadAd(adRequest1);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation.equals(mTextAnim)) {

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash.this);
                Intent intent = new Intent(Splash.this, Tahta.class);
                startActivity(intent,options.toBundle());
            }else {
                Intent intent = new Intent(Splash.this, Tahta.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}


    private void spanText(){



        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fofbb_reg.ttf");
        final SpannableStringBuilder sb = new SpannableStringBuilder("Ş A P Ş İ K !");

        // Span to set text color to some RGB value
        final ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.rgb(205, 105, 201));
        final ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.rgb(255, 64, 129));
        final ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.rgb(255, 185, 15));
        final ForegroundColorSpan fsRed = new ForegroundColorSpan(Color.rgb(255, 95, 0));
        final ForegroundColorSpan fsGreen = new ForegroundColorSpan(Color.rgb(255, 64, 129));
        final ForegroundColorSpan fsBlue = new ForegroundColorSpan(Color.rgb(205, 105, 201));
        final ForegroundColorSpan Red = new ForegroundColorSpan(Color.rgb(255, 185, 15));


        // Span to make text bold
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

        // Set the text color for first 4 characters
        sb.setSpan(fcsRed, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fcsGreen, 2, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 2, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fcsBlue, 4, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 4, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fsRed, 6, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 6, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fsGreen, 8, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 8, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fsBlue, 10, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 10, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(Red, 12, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 12, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);



        tv1.setTextSize(32);
        tv1.setTypeface(font,Typeface.BOLD);
        tv1.setText(sb);
        //yourTextView.setText(sb);

    }



}
