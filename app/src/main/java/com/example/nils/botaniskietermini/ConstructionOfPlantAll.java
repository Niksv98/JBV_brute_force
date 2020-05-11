package com.example.nils.botaniskietermini;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class ConstructionOfPlantAll extends AppCompatActivity {

//    float screenWidth, screenHeight; // Supposed for tabbed layout implementation
    private Button plantButton;
    private Button leafButton;
    private Button cloverleafButton;
    private Button rootButton;
    private Button seedButton;
    private Button flowerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_of_plant_all);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        updateCurrentContent(false);
        setTitle(R.string.appName);


        plantButton = (Button) findViewById(R.id.plantButton);
        flowerButton = (Button) findViewById(R.id.flowerButton);
        rootButton = (Button) findViewById(R.id.rootButton);
        leafButton = (Button) findViewById(R.id.leafButton);
        seedButton = (Button) findViewById(R.id.seedButton);
        cloverleafButton = (Button) findViewById(R.id.cloverLeafButton);

        ((TextView)findViewById(R.id.planContruction)).setText(R.string.constructionOfPlant);
        ((TextView)findViewById(R.id.seedContruction)).setText(R.string.constructionOfSeed);
        ((TextView)findViewById(R.id.rootContruction)).setText(R.string.constructionOfRoot);
        ((TextView)findViewById(R.id.leafContruction)).setText(R.string.constructionOfSimpleLeaf);
        ((TextView)findViewById(R.id.cloverLeafContruction)).setText(R.string.constructionOfCompoundLeaf);
        ((TextView)findViewById(R.id.flowerContruction)).setText(R.string.constructionOfFlower);

    }

    public void plantButtonClicked(View view){
        Intent intent = new Intent(this, ConstructionOfPlant.class);
        startActivity(intent);
    }
    public void flowerButtonClicked(View view){
        Intent intent = new Intent(this, ConstructionOfFlower.class);
        startActivity(intent);
    }
    public void rootButtonClicked(View view){
        Intent intent = new Intent(this, ConstructionOfRoot.class);
        startActivity(intent);
    }
    public void leafButtonClicked(View view){
        Intent intent = new Intent(this, ConstructionOfLeaf.class);
        startActivity(intent);
    }
    public void seedButtonClicked(View view){
        Intent intent = new Intent(this, ConstructionOfSeed.class);
        startActivity(intent);
    }
    public void cloverLeafButtonClicked(View view){
        Intent intent = new Intent(this, ConstructionOfCloverLeaf.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_from_construction_of_all, menu);
        return true;
    }
    public void updateCurrentContent(boolean needToChange) {
        String currentContent = "";
        if (needToChange)
            currentContent = (MainActivity.isLatvian) ? "us" : "lv";
        else
            currentContent = (MainActivity.isLatvian) ? "lv" : "us";
        try {
            Configuration config = new Configuration(getResources().getConfiguration());
            config.locale = new Locale(currentContent);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            if (needToChange)
                MainActivity.isLatvian = (MainActivity.isLatvian) ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.main_screen:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                intent = new Intent(this, AboutApp.class);
                startActivity(intent);
                break;
            case R.id.gendersTranslations:
                intent = new Intent(this, TranslationOfGenders.class);
                startActivity(intent);
                break;
            /*case R.id.allImages:
                intent = new Intent(this, ConstructionOfPlantAll.class);
                startActivity(intent);
                break;*/
            case R.id.sources:
                intent = new Intent(this, TranslationReferences.class);
                startActivity(intent);
                break;
            case R.id.publications:
                intent = new Intent(this, Publications.class);
                startActivity(intent);
                break;
            case R.id.app_manual:
                intent = new Intent(this, UserManual.class);
                startActivity(intent);
                break;
            case R.id.app_lang:
                updateCurrentContent(true);
                restartActivity();
                 break;

        }
        return true;
    }
    public static Button getButtonForFoundTerm(String term, AppCompatActivity a, RelativeLayout ll)
    {
        Button result = null;

        for (int i = 0; i < ll.getChildCount(); i++) {
            if(ll.getChildAt(i).getTag()!=null)
            {
                if(ll.getChildAt(i).getTag().toString().equalsIgnoreCase(term)){
                    result = (Button) ll.getChildAt(i);
                    break;
                }
            }

        }
        return result;
    }
}
