package com.example.nils.botaniskietermini;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Locale;

public class ConstructionOfFlower extends AppCompatActivity {

    ImageView flowerImage;
    Button pedicelButton;
    Button receptacleButton;
    Button sepalButton;
    Button petalButton;
    Button perianthButton;
    Button stamenButton;
    Button antherButton;
    Button filamentButton;
    Button pistilButton;
    Button stigmaButton;
    Button styleButton;
    Button ovaryButton;
    Button ovuleButton;
    int leftPadding, topPadding;
    int imageHeight, imageWidth;
    float screenHeight, screenWidth;
    static int BUTTON_RADIUS = 20;
    static double POSITION_DEVIATION = 1.33;
    static String PART_OF_FLOWER = "partOfPlant";
    private static String term = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_of_flower);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        updateCurrentContent(false);
        setTitle(R.string.appName);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = ((float) displayMetrics.widthPixels);
        screenHeight = ((float) displayMetrics.heightPixels);


        flowerImage = (ImageView) findViewById(R.id.flowerImageView);

        if(getIntent().getStringExtra("term")!=null) {
            term = getIntent().getStringExtra("term");
            RelativeLayout ll = (RelativeLayout) findViewById(R.id.relLayFlower);
            Button b = ConstructionOfPlantAll.getButtonForFoundTerm(getIntent().getStringExtra("term"), this, ll );
            b.setBackgroundColor(R.drawable.roundedbutton_r);
        }
    }




    public void buttonZiedkatsClicked(View view) {
        openFlowerInfo("pedicel");
    }

    public void buttonZiedgultneClicked(View view) {
        openFlowerInfo("receptacle");
    }

    public void buttonKauslapaClicked(View view) {
        openFlowerInfo("sepal");
    }

    public void buttonVainaglapaClicked(View view) {
        openFlowerInfo("petal");
    }

    public void buttonApziednisClicked(View view) {
        openFlowerInfo("perianth");
    }

    public void buttonPuteksnlapaClicked(View view) {
        openFlowerInfo("stamen");
    }

    public void buttonPuteksnicaClicked(View view) {
        openFlowerInfo("anther");
    }

    public void buttonPuteksnlapasKatinsClicked(View view) {
        openFlowerInfo("filament");
    }

    public void buttonAuglenicaClicked(View view) {
        openFlowerInfo("pistil");
    }

    public void buttonDriksnaClicked(View view) {
        openFlowerInfo("stigma");
    }

    public void buttonIrbulisClicked(View view) {
        openFlowerInfo("style");
    }

    public void buttonSeklotneClicked(View view) {
        openFlowerInfo("ovary");
    }

    public void buttonSeklaizmetnisClicked(View view) {
        openFlowerInfo("ovule");
    }

    private void openFlowerInfo(String extra){
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(PART_OF_FLOWER, extra);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        imageHeight = flowerImage.getMeasuredHeight();
        imageWidth = flowerImage.getMeasuredWidth();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        float x = 0.59336f;
        float y = 0.79668f;



        pedicelButton = (Button) findViewById(R.id.pedicelButton);
        setButtonPosition(0.59336f, 0.79668f, pedicelButton);

        receptacleButton = (Button) findViewById(R.id.receptacleButton);
        setButtonPosition(0.59336f, 0.6556f, receptacleButton);

        sepalButton = (Button) findViewById(R.id.sepalButton);
        setButtonPosition(0.09751f, 0.61411f, sepalButton);

        petalButton = (Button) findViewById(R.id.petalButton);
        setButtonPosition(0.09129f, 0.25519f, petalButton);

        perianthButton = (Button) findViewById(R.id.perianthButton);
        setButtonPosition(0.0332f, 0.44606f, perianthButton);

        stamenButton = (Button) findViewById(R.id.stamenButton);
        setButtonPosition(0.25104f, 0.06864f, stamenButton);

        antherButton = (Button) findViewById(R.id.antherButton);
        setButtonPosition(0.17427f, 0.1639f, antherButton);

        filamentButton = (Button) findViewById(R.id.filamentButton);
        setButtonPosition(0.33402f, 0.1473f, filamentButton);

        pistilButton = (Button) findViewById(R.id.pistilButton);
        setButtonPosition(0.94398f, 0.32573f, pistilButton);

        stigmaButton = (Button) findViewById(R.id.stigmaButton);
        setButtonPosition(0.861f, 0.11203f, stigmaButton);

        styleButton = (Button) findViewById(R.id.styleButton);
        setButtonPosition(0.84855f, 0.20747f, styleButton);

        ovaryButton = (Button) findViewById(R.id.ovaryButton);
        setButtonPosition(0.86722f, 0.45851f, ovaryButton);

        ovuleButton = (Button) findViewById(R.id.ovuleButton);
        setButtonPosition(0.85477f, 0.56017f, ovuleButton);


    }

    public void setButtonPosition(float x, float y, Button button){
        int resource = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resource > 0){
            int statusBarHeight = this.getResources().getDimensionPixelSize(resource);
            float statusBarHeightDPI = convertPixelsToDp(statusBarHeight, this);

            RelativeLayout flowerLayout = (RelativeLayout) findViewById(R.id.relLayFlower);
            int layoutHeight = flowerLayout.getHeight();

            leftPadding = (int) (((screenWidth - imageWidth) / 2) + (imageWidth * x) - BUTTON_RADIUS);
            topPadding = (int) (((layoutHeight - imageHeight) / 2) + (imageHeight * y) - BUTTON_RADIUS);
/*
            Toast.makeText(this, statusBarHeight + " px; "
                    + statusBarHeightDPI + " dpi\n"
                    + screenHeight + " - scr; "
                    + imageHeight + " - img\n"
                    + (screenHeight-imageHeight) + " - scr-img", Toast.LENGTH_SHORT).show();
*/
            button.setTranslationX(leftPadding);
            button.setTranslationY(topPadding);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_from_construction_of_each, menu);
        return true;
    }
    public static float convertPixelsToDp(float px, Context context){
        /*Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);*/
        float dp = px / context.getResources().getDisplayMetrics().density;
        return dp;
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


