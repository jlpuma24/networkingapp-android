package com.networkingandroid;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.networkingandroid.network.ApiCallsManager;
import com.networkingandroid.network.BusProvider;
import com.networkingandroid.util.PrefsUtil;
import com.squareup.otto.Bus;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Usuario on 16/09/2016.
 */
public class NetworkingApplication extends MultiDexApplication {

    private static NetworkingApplication mContext;
    private Bus bus = BusProvider.getBus();
    private ApiCallsManager apiCallsManager;

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

        apiCallsManager = new ApiCallsManager(this, bus);
        bus.register(apiCallsManager);
        bus.register(this);
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
