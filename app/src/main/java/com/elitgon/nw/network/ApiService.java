package com.elitgon.nw.network;

import com.elitgon.nw.network.events.AttendanceEventResponse;
import com.elitgon.nw.network.events.AttendanceResponse;
import com.elitgon.nw.network.events.EventsResponse;
import com.elitgon.nw.network.events.SuccessAreasResponseEvent;
import com.elitgon.nw.network.events.SuccessIndustriesResponseEvent;
import com.elitgon.nw.network.events.SuccessLoginResponseEvent;
import com.elitgon.nw.network.model.UserResponseDetail;
import com.elitgon.nw.network.model.UserUpdateObjectRequest;
import com.elitgon.nw.network.model.UserUpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jose Rodriguez on 20/05/2016.
 */
public interface ApiService {
    @GET("api/events")
    Call<EventsResponse> getEvents(@Query("page") long page, @Query("offset") long offset);

    @POST("sign_in")
    Call<SuccessLoginResponseEvent> doLogin(@Query("email") String email,
                                            @Query("name") String name,
                                            @Query("last_name") String last_name,
                                            @Query("avatar") String avatar,
                                            @Query("profile") String profile,
                                            @Query("uid") String uid,
                                            @Query("company") String company,
                                            @Query("position") String position,
                                            @Query("location") String location);

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

    @PUT("api/users/{id}")
    Call<UserUpdateResponse> doUpdateUser(@Path("id") long id,
                                          @Body UserUpdateObjectRequest userUpdateRequest);

    @GET("api/users/{id}")
    Call<UserResponseDetail> doGetUser(@Path("id") long id);
}