package com.networkingandroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.networkingandroid.fragments.FragmentOnBoardingOne;
import com.networkingandroid.fragments.FragmentOnBoardingThree;
import com.networkingandroid.fragments.FragmentOnBoardingTwo;

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