package com.networkingandroid.network;

import com.networkingandroid.network.events.AttendanceEventResponse;
import com.networkingandroid.network.events.AttendanceResponse;
import com.networkingandroid.network.events.EventsResponse;
import com.networkingandroid.network.events.SuccessAreasResponseEvent;
import com.networkingandroid.network.events.SuccessIndustriesResponseEvent;
import com.networkingandroid.network.events.SuccessLoginResponseEvent;
import com.networkingandroid.network.model.Event;

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

    @GET("api/areas")
    Call<SuccessAreasResponseEvent> doGetAreas();

    @GET("api/users/{id}/attendances")
    Call<AttendanceResponse> doGetAttendances(@Path("id") long id);

    @GET("api/search/by_text")
    Call<EventsResponse> getEventsByText(@Query("text") String text);

    @POST("http://nwmeeting.com/api/attendances")
    Call<AttendanceEventResponse> doMakeAttendance(@Query("user_id") long user_id, @Query("event_id") long event_id);
}