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

public class ConstructionOfCloverLeaf extends AppCompatActivity {

    ImageView cloverLeafImage;
    Button cloverLeafButton1;
    Button cloverLeafButton2;
    private static String PART_OF_CLOVER_LEAF = "partOfPlant";
    private int leftPadding, topPadding;
    private int imageHeight, imageWidth;
    private float screenHeight, screenWidth;
    private static int BUTTON_RADIUS = 20;
    private static float BUTTON_X_POSITION = 0.91053f;
    private static String term = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_of_clover_leaf);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        updateCurrentContent(false);
        setTitle(R.string.appName);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = ((float) displayMetrics.widthPixels);
        screenHeight = ((float) displayMetrics.heightPixels);

        cloverLeafImage = (ImageView) findViewById(R.id.cloverLeafImage);
        if(getIntent().getStringExtra("term")!=null) {
            term = getIntent().getStringExtra("term");
            RelativeLayout ll = (RelativeLayout) findViewById(R.id.relLayCloverLeaf);
            Log.d("term", term);
            Button b = ConstructionOfPlantAll.getButtonForFoundTerm(getIntent().getStringExtra("term"), this, ll );
            if(b!=null)
                b.setBackgroundColor(R.drawable.roundedbutton_r);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        imageHeight = cloverLeafImage.getMeasuredHeight();
        imageWidth = cloverLeafImage.getMeasuredWidth();

        cloverLeafButton1 = (Button) findViewById(R.id.cloverLeafButton1);
        setButtonPosition(BUTTON_X_POSITION, 0.3418f, cloverLeafButton1);

        cloverLeafButton2 = (Button) findViewById(R.id.cloverLeafButton2);
        setButtonPosition(BUTTON_X_POSITION, 0.65184f, cloverLeafButton2);
    }

    public void setButtonPosition(float x, float y, Button button) {
        RelativeLayout cloverLeafLayout = (RelativeLayout) findViewById(R.id.relLayCloverLeaf);
        int layoutHeight = cloverLeafLayout.getHeight();

        int layoutImageHeightGap = (layoutHeight - imageHeight) / 2;
        leftPadding = (int) (((screenWidth - imageWidth) / 2) + (imageWidth * x) - BUTTON_RADIUS);
        topPadding = (int) (layoutImageHeightGap + (imageHeight * y) - BUTTON_RADIUS);

        button.setTranslationX(leftPadding);
        button.setTranslationY(topPadding);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_from_construction_of_each, menu);
        return true;
    }
    public void cloverLeafButton1Clicked(View view){
        openLeafInfo("leaflet");
    }
    public void cloverLeafButton2Clicked(View view){
        openLeafInfo("petiole");
    }

    /*public void cloverLeafButton2Clicked(View view){
        openLeafInfo("fruit");
    }*/

    private void openLeafInfo(String extra){
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(PART_OF_CLOVER_LEAF, extra);
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


