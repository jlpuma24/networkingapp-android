package com.networkingandroid.network;

import android.content.Context;

import com.networkingandroid.network.events.EventsResponse;
import com.networkingandroid.network.events.RequestEvents;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Subscribe
    public void getEvents(RequestEvents requestEvents){
        mApiClient.getEvents().enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                if (response.isSuccessful()){
                    mBus.post(response.body());
                }
                else {
                    mBus.post(ErrorUtils.parseRretrofitError(response, mApiClient.getRetrofitAdapter()));
                }
            }
            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {

            }
        });
    }
}
