package com.networkingandroid.network;

import com.networkingandroid.network.events.EventsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jose Rodriguez on 20/05/2016.
 */
public interface ApiService {

    @GET("events")
    Call<EventsResponse> getEvents();

}