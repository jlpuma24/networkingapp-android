package com.elitgon.nw.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.elitgon.R;
import com.elitgon.nw.util.Constants;
import com.elitgon.nw.util.PrefsUtil;
import com.elitgon.nw.util.UtilsMethods;

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
