package com.networkingandroid.network;

import com.networkingandroid.network.events.EventsResponse;
import com.networkingandroid.network.events.SuccessAreasResponseEvent;
import com.networkingandroid.network.events.SuccessIndustriesResponseEvent;
import com.networkingandroid.network.events.SuccessLoginResponseEvent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jose Rodriguez on 20/05/2016.
 */
public interface ApiService {
    @GET("api/events")
    Call<EventsResponse> getEvents();

    @POST("sign_in")
    Call<SuccessLoginResponseEvent> doLogin(@Query("email") String email,
                                            @Query("name") String name,
                                            @Query("last_name") String last_name,
                                            @Query("avatar") String avatar,
                                            @Query("profile") String profile,
                                            @Query("uid") String uid);

    @GET("api/industries")
    Call<SuccessIndustriesResponseEvent> doGetIndustries();

    @GET("api/industries/{id}/areas")
    Call<SuccessAreasResponseEvent> doGetAreas(@Path("id") long id);
}