package com.example.nils.botaniskietermini;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Nils on 12.12.2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabAboutAppLV tabLV = new TabAboutAppLV();
                return tabLV;
            case 1:
                TabAboutAppEN tabEN = new TabAboutAppEN();
                return tabEN;
            case 2:
                TabAboutAppRU tabRU = new TabAboutAppRU();
                return tabRU;
            case 3:
                TabAboutAppDE tabDE = new TabAboutAppDE();
                return tabDE;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
