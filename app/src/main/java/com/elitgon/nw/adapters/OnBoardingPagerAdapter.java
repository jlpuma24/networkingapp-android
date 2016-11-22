package com.elitgon.nw.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.elitgon.nw.fragments.FragmentOnBoardingOne;
import com.elitgon.nw.fragments.FragmentOnBoardingThree;
import com.elitgon.nw.fragments.FragmentOnBoardingTwo;

/**
 * Created by Jose Rodriguez on 18/05/2016.
 */
public class OnBoardingPagerAdapter extends FragmentPagerAdapter {

    public OnBoardingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount(){
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return new FragmentOnBoardingOne();
            case 1: return new FragmentOnBoardingTwo();
            default : return new FragmentOnBoardingThree();
        }
    }
}