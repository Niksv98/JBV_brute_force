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

public class ConstructionOfLeaf extends AppCompatActivity {

    ImageView leafImage;
    Button leafButton1;
    Button leafButton2;
    Button leafButton3;
    Button leafButton4;
    Button leafButton5;
    private static String PART_OF_LEAF = "partOfPlant";
    private int leftPadding, topPadding;
    private int imageHeight, imageWidth;
    private float screenHeight, screenWidth;
    private static int BUTTON_RADIUS = 20;
    private static float BUTTON_X_POSITION = 0.95173f;
    private static String term = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_of_leaf);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        updateCurrentContent(false);
        setTitle(R.string.appName);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = ((float) displayMetrics.widthPixels);
        screenHeight = ((float) displayMetrics.heightPixels);
//        Log.d("SCREEN", "Height: " + screenHeight);

        leafImage = (ImageView) findViewById(R.id.leafImage);
        if(getIntent().getStringExtra("term")!=null) {
            term = getIntent().getStringExtra("term");
            RelativeLayout ll = (RelativeLayout) findViewById(R.id.relLayLeaf);
            Log.d("term", term);
            Button b = ConstructionOfPlantAll.getButtonForFoundTerm(getIntent().getStringExtra("term"), this, ll );
            if(b!=null)
                b.setBackgroundColor(R.drawable.roundedbutton_r);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        imageHeight = leafImage.getMeasuredHeight();
        imageWidth = leafImage.getMeasuredWidth();
//        Log.d("IMAGE", "Height: " + imageHeight);

        leafButton1 = (Button) findViewById(R.id.leafButton1);
        setButtonPosition(BUTTON_X_POSITION, 0.02472f, leafButton1);

        leafButton2 = (Button) findViewById(R.id.leafButton2);
        setButtonPosition(BUTTON_X_POSITION, 0.21569f, leafButton2);

        leafButton3 = (Button) findViewById(R.id.leafButton3);
        setButtonPosition(BUTTON_X_POSITION, 0.36465f, leafButton3);

        leafButton4 = (Button) findViewById(R.id.leafButton4);
        setButtonPosition(BUTTON_X_POSITION, 0.4382f, leafButton4);

        leafButton5 = (Button) findViewById(R.id.leafButton5);
        setButtonPosition(BUTTON_X_POSITION, 0.75710f, leafButton5);
    }

    public void setButtonPosition(float x, float y, Button button) {
        RelativeLayout leafLayout = (RelativeLayout) findViewById(R.id.relLayLeaf);
        int layoutHeight = leafLayout.getHeight();
//        Log.d("LAYOUT", "Height: " + layoutHeight);

        int layoutImageHeightGap = (layoutHeight - imageHeight) / 2;
        leftPadding = (int) (((screenWidth - imageWidth) / 2) + (imageWidth * x) - BUTTON_RADIUS);
        topPadding = (int) (layoutImageHeightGap + (imageHeight * y) - BUTTON_RADIUS);

        button.setTranslationX(leftPadding);
        button.setTranslationY(topPadding);
    }

//    TEMPORARY
    public void button1Clicked(View view){
        openLeafInfo("tip of leaf blade");
    }
    public void button2Clicked(View view){
        openLeafInfo("margin of leaf blade");
    }
    public void button3Clicked(View view){
        openLeafInfo("leaf blade");
    }
    public void button4Clicked(View view){
        openLeafInfo("leaf vein");
    }
    public void button5Clicked(View view){
        openLeafInfo("petiole");
    }

    /*public void button2Clicked(View view){
        openLeafInfo("");
    }
    public void button3Clicked(View view){
        openLeafInfo("");
    }
    public void button4Clicked(View view){
        openLeafInfo("");
    }
    public void button5Clicked(View view){
        openLeafInfo("");
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_from_construction_of_each, menu);
        return true;
    }
    private void openLeafInfo(String extra){
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(PART_OF_LEAF, extra);
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


