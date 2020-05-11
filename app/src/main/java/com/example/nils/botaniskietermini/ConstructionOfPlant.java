package com.example.nils.botaniskietermini;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Locale;

public class ConstructionOfPlant extends AppCompatActivity {

    ImageView plantImage;
    Button leafButton;
    Button flowerButton;
    Button stalkButton;
    Button budButton;
    Button fruitButton;
    Button rootButton;
    String partOfPlant = "partOfPlant";
    int leftPadding, topPadding;
    int imageHeight, imageWidth;
    float screenWidth, screenHeight;
    static int BUTTON_RADIUS = 20;
    private static String term="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_of_plant);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        updateCurrentContent(false);
        setTitle(R.string.appName);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = ((float) displayMetrics.widthPixels);
        screenHeight = ((float) displayMetrics.heightPixels);

        plantImage = (ImageView) findViewById(R.id.plantImageView);

        if(getIntent().getStringExtra("term")!=null) {
            term = getIntent().getStringExtra("term");
            RelativeLayout ll = (RelativeLayout) findViewById(R.id.relLayPlant);
            Button b = ConstructionOfPlantAll.getButtonForFoundTerm(getIntent().getStringExtra("term"), this, ll);
            b.setBackgroundColor(R.drawable.roundedbutton_r);
        }

    }

    public void buttonZiedsClicked(View view) {
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(partOfPlant, "flower");
        startActivity(intent);
    }

    public void buttonAuglisClicked(View view) {
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(partOfPlant, "fruit");
        startActivity(intent);
    }

    public void buttonPumpursClicked(View view) {
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(partOfPlant, "bud");
        startActivity(intent);
    }

    public void buttonStublajsClicked(View view) {
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(partOfPlant, "stalk");
        startActivity(intent);
    }

    public void buttonSakneClicked(View view) {
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(partOfPlant, "root");
        startActivity(intent);
    }

    public void buttonLapaClicked(View view) {
        Intent intent = new Intent(this, PlantInfoWindow.class);
        intent.putExtra(partOfPlant, "leaf");
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_from_construction_of_each, menu);
        return true;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        imageHeight = plantImage.getMeasuredHeight();
        imageWidth = plantImage.getMeasuredWidth();

        flowerButton = (Button) findViewById(R.id.flowerButton);
        setButtonPosition(0.74824f, 0.09026f, flowerButton);

        budButton = (Button) findViewById(R.id.budButton);
        setButtonPosition(0.74824f, 0.31235f, budButton);

        fruitButton = (Button) findViewById(R.id.fruitButton);
        setButtonPosition(0.74824f, 0.21734f, fruitButton);

        stalkButton = (Button) findViewById(R.id.stalkButton);
        setButtonPosition(0.74824f, 0.4323f, stalkButton);

        leafButton = (Button) findViewById(R.id.leafButton);
        setButtonPosition(0.74824f, 0.49881f, leafButton);

        rootButton = (Button) findViewById(R.id.rootButton);
        setButtonPosition(0.74824f, 0.6829f, rootButton);
    }

    public void setButtonPosition(float x, float y, Button button){
        leftPadding = (int) (((screenWidth - imageWidth) / 2) + (imageWidth * x) - BUTTON_RADIUS);
        topPadding = (int) ((imageHeight * y) - BUTTON_RADIUS);
        button.setTranslationX(leftPadding);
        button.setTranslationY(topPadding);
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


