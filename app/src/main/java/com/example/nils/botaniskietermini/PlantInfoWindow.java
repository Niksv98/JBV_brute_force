package com.example.nils.botaniskietermini;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlantInfoWindow extends AppCompatActivity {

    DatabaseHelper myDB;

    TextView deTranslation;
    TextView deGender;
    TextView enTranslation;
    TextView laTranslation;
    TextView lvTranslation;
    TextView lvGender;
    TextView ruTranslation;
    TextView ruGender;
    static String DE = "DE";
    static String DE_GEN = "DE_GEND";
    static String EN = "EN";
    static String LA = "LA";
    static String LV = "LV";
    static String LV_GEN = "LV_GEND";
    static String RU = "RU";
    static String RU_GEN = "RU_GEND";

    static String termF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info_window);

        myDB = new DatabaseHelper(this);

        Intent thisIntent = getIntent();
        String partOfPlant = thisIntent.getStringExtra("partOfPlant");

        deTranslation = (TextView) findViewById(R.id.deTranslationPlantInfoWindow);
        enTranslation = (TextView) findViewById(R.id.enTranslationPlantInfoWindow);
        lvTranslation = (TextView) findViewById(R.id.lvTranslationPlantInfoWindow);
        laTranslation = (TextView) findViewById(R.id.laTranslationPlantInfoWindow);
        ruTranslation = (TextView) findViewById(R.id.ruTranslationPlantInfoWindow);
        deGender = (TextView) findViewById(R.id.deGenderPlantInfoWindow);
        lvGender = (TextView) findViewById(R.id.lvGenderPlantInfoWindow);
        ruGender = (TextView) findViewById(R.id.ruGenderPlantInfoWindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.6));

        fillTranslations(partOfPlant);
        ImageView picto = (ImageView) findViewById(R.id.pictogram);
        picto.setVisibility(View.VISIBLE);
        picto.setOnClickListener(new View.OnClickListener() { //lai izsauktu klasi
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), Class.forName("com.example.nils.botaniskietermini.MainActivity"));
                    intent.putExtra("term",termF);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {//|IllegalAccessException |InstantiationException e) {
                    e.printStackTrace();

                }


            }
        });

    }



    private void fillTranslations(String plantPart){

        ArrayList<TermInfo> terms = myDB.getTermForImages(plantPart);

        for(TermInfo termInfo: terms) {
            if (MainActivity.isLatvian)
                termF = termInfo.getAllTermsInLang()[LangTerm.getLangIndex("LV")].getTerm();
            else
                termF = termInfo.getAllTermsInLang()[LangTerm.getLangIndex("EN")].getTerm();

            deTranslation.setText(termInfo.getAllTermsInLang()[LangTerm.getLangIndex("DE")].getTerm());
            deGender.setText(termInfo.getAllTermsInLang()[LangTerm.getLangIndex("DE")].getTerm_gend());
            enTranslation.setText(termInfo.getAllTermsInLang()[LangTerm.getLangIndex("EN")].getTerm());
            laTranslation.setText(termInfo.getAllTermsInLang()[LangTerm.getLangIndex("LA")].getTerm());
            lvTranslation.setText(termInfo.getAllTermsInLang()[LangTerm.getLangIndex("LV")].getTerm());
            lvGender.setText(termInfo.getAllTermsInLang()[LangTerm.getLangIndex("LV")].getTerm_gend());
            ruTranslation.setText(termInfo.getAllTermsInLang()[LangTerm.getLangIndex("RU")].getTerm());
            ruGender.setText(termInfo.getAllTermsInLang()[LangTerm.getLangIndex("RU")].getTerm_gend());
        }
    }


}
