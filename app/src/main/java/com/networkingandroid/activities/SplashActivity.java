package com.networkingandroid.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.networkingandroid.R;
import com.networkingandroid.util.Constants;
import com.networkingandroid.util.PrefsUtil;
import com.networkingandroid.util.UtilsMethods;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UtilsMethods.getPackageId();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (!PrefsUtil.getInstance().isLogged()) {
                    startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
                    finish();
                }
                else  {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            }
        }, Constants.SPLASH_DISPLAY_LENGTH);
    }
}
