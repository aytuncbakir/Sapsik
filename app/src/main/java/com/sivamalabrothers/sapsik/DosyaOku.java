package com.sivamalabrothers.sapsik;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DosyaOku{

    private Context context;

    public DosyaOku(Context context){
        this.context = context;
    }

    public ArrayList<String> dosyadanyukle(String okunacakDosya){
        String dosyaYolu = "data/"+okunacakDosya;
        BufferedReader okumaTamponu = null;
        try{

            AssetManager manager = context.getAssets();
            okumaTamponu = new BufferedReader(
                        new InputStreamReader(manager.open(dosyaYolu), "UTF-8"));

                ArrayList<String> datalar = new ArrayList<String>();
                String satir = okumaTamponu.readLine();
                while (satir != null) {

                    if (!satir.equals(""))
                        datalar.add(satir);
                    satir = okumaTamponu.readLine();
                }
            okumaTamponu.close();
            return datalar;


        }catch(FileNotFoundException ex){
           ex.printStackTrace();
        }
        catch(IOException ex1){
            ex1.printStackTrace();
        }finally {
            if (okumaTamponu != null) {
                try {
                    okumaTamponu.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
