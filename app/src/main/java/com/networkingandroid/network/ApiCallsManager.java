package com.networkingandroid.network;

import android.content.Context;

import com.squareup.otto.Bus;

/**
 * Created by Usuario on 17/09/2016.
 */
public class ApiCallsManager {

    private Context mContext;
    private Bus mBus;
    private ApiClient mApiClient;

    public ApiCallsManager(Context mContext, Bus mBus) {
        this.mContext = mContext;
        this.mBus = mBus;
        mApiClient = ApiClient.getInstance(mContext);
    }

}
