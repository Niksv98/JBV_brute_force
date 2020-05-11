package com.example.nils.botaniskietermini;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Locale;

public class AboutApp extends AppCompatActivity implements TabAboutAppLV.OnFragmentInteractionListener,
TabAboutAppEN.OnFragmentInteractionListener, TabAboutAppRU.OnFragmentInteractionListener,
TabAboutAppDE.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        updateCurrentContent(false);
        setTitle(R.string.appName);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.LV));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.EN));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.RU));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.DE));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.aboutTabs);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(MainActivity.isLatvian)
            viewPager.setCurrentItem(0);
        else
            viewPager.setCurrentItem(1);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_from_app_info, menu);
        return true;
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
        Intent intent;
        switch (item.getItemId()){
            case R.id.main_screen:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            /*case R.id.about:
                intent = new Intent(this, AboutApp.class);
                startActivity(intent);
                break;*/
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
