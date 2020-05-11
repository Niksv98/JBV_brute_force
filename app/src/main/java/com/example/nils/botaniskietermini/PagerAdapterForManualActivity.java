package com.example.nils.botaniskietermini;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Nils on 12.12.2017.
 */

public class PagerAdapterForManualActivity extends FragmentStatePagerAdapter {

    int numOfTabs;

    public PagerAdapterForManualActivity(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabUserManualLV tabLV = new TabUserManualLV();
                return tabLV;
            case 1:
                TabUserManualEN tabEN = new TabUserManualEN();
                return tabEN;
            case 2:
                TabUserManualRU tabRU = new TabUserManualRU();
                return tabRU;
            case 3:
                TabUserManualDE tabDE = new TabUserManualDE();
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
