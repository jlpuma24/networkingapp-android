package com.networkingandroid;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.networkingandroid.util.PrefsUtil;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Usuario on 16/09/2016.
 */
public class NetworkingApplication extends MultiDexApplication {

    private static NetworkingApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //Setting up font for all project.
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        //Setting up user preferences.
        PrefsUtil.initializeInstance(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static NetworkingApplication getInstance() {
        return mContext;
    }
}
