package com.networkingandroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.networkingandroid.R;

/**
 * Created by Usuario on 16/09/2016.
 */
public class FragmentOnBoardingThree extends Fragment {

    public FragmentOnBoardingThree(){
        // Empty constructor always by necessary
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.onboarding_three,
                container, false);

        return view;
    }
}
