package com.example.nils.botaniskietermini;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Locale;

public class ConstructionOfSeed extends AppCompatActivity {

    ImageView seedImage;
    Button seedButton1;
    Button seedButton2;
    Button seedButton3;
    Button seedButton4;
    Button seedButton5;
    private static String PART_OF_SEED = "partOfPlant";
    private int leftPadding, topPadding;
    private int imageHeight, imageWidth;
    private float screenHeight, screenWidth;
    private static int BUTTON_RADIUS = 20;
    private static float BUTTON_X_POSITION = 0.95246f;
    private static String term = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_of_seed);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        updateCurrentContent(false);
        setTitle(R.string.appName);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = ((float) displayMetrics.widthPixels);
        screenHeight = ((float) displayMetrics.heightPixels);

        seedImage = (ImageView) findViewById(R.id.seedImage);

        if(getIntent().getStringExtra("term")!=null) {
            term = getIntent().getStringExtra("term");
            RelativeLayout ll = (RelativeLayout) findViewById(R.id.relLaySeed);
            Log.d("term", term);
            Button b = ConstructionOfPlantAll.getButtonForFoundTerm(getIntent().getStringExtra("term"), this, ll );
            if(b!=null)
                b.setBackgroundColor(R.drawable.roundedbutton_r);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        imageHeight = seedImage.getMeasuredHeight();
        imageWidth = seedImage.getMeasuredWidth();

        seedButton1 = (Button) findViewById(R.id.seedButton1);
        setButtonPosition(BUTTON_X_POSITION, 0.19814f, seedButton1);

        seedButton2 = (Button) findViewById(R.id.seedButton2);
        setButtonPosition(BUTTON_X_POSITION, 0.26842f, seedButton2);

        seedButton3 = (Button) findViewById(R.id.seedButton3);
        setButtonPosition(BUTTON_X_POSITION, 0.38442f, seedButton3);

        seedButton4 = (Button) findViewById(R.id.seedButton4);
        setButtonPosition(BUTTON_X_POSITION, 0.65114f, seedButton4);

        seedButton5 = (Button) findViewById(R.id.seedButton5);
        setButtonPosition(BUTTON_X_POSITION, 0.82981f, seedButton5);
    }

    public void setButtonPosition(float x, float y, Button button) {
        RelativeLayout seedLayout = (RelativeLayout) findViewById(R.id.relLaySeed);
        int layoutHeight = seedLayout.getHeight();

        int layoutImageHeightGap = (layoutHeight - imageHeight) / 2;
        leftPadding = (int) (((screenWidth - imageWidth) / 2) + (imageWidth * x) - BUTTON_RADIUS);
        topPadding = (int) (layoutImageHeightGap + (imageHeight * y) - BUTTON_RADIUS);

        button.setTranslationX(leftPadding);
        button.setTranslationY(topPadding);
    }

    //    TEMPORARY
    public void seedButton1Clicked(View view){
        openSeedInfo("seed leaf, cotyledon");
    }
    public void seedButton2Clicked(View view){
        openSeedInfo("hypocotyl");
    }
    public void seedButton3Clicked(View view){
        openSeedInfo("radicle");
    }
    public void seedButton4Clicked(View view){
        openSeedInfo("endosperm");
    }
    public void seedButton5Clicked(View view){
        openSeedInfo("seed coat");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_from_construction_of_each, menu);
        return true;
    }
    private void openSeedInfo(String extra){
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(PART_OF_SEED, extra);
        startActivity(intent);
    }
    public void updateCurrentContent(boolean needToChange) {
        String currentContent = "";
        if(needToChange)
            currentContent = (MainActivity.isLatvian)? "us":"lv";
        else
            currentContent = (MainActivity.isLatvian)? "lv":"us";
        try {
            Configuration config = new Configuration(getResources().getConfiguration());
            config.locale = new Locale(currentContent);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            if(needToChange)
                MainActivity.isLatvian = (MainActivity.isLatvian)? false : true;
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
        term = "";
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
            case R.id.allImages:
                intent = new Intent(this, ConstructionOfPlantAll.class);
                startActivity(intent);
                break;
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

}


