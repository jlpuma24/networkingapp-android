package com.networkingandroid.network;

import android.content.Context;

import com.networkingandroid.network.events.AttendanceEventResponse;
import com.networkingandroid.network.events.AttendanceResponse;
import com.networkingandroid.network.events.EventsResponse;
import com.networkingandroid.network.events.RequestAreas;
import com.networkingandroid.network.events.RequestAttendanceEvent;
import com.networkingandroid.network.events.RequestAttendances;
import com.networkingandroid.network.events.RequestEvents;
import com.networkingandroid.network.events.RequestEventsByNameEvent;
import com.networkingandroid.network.events.RequestFilterEvents;
import com.networkingandroid.network.events.RequestIndustries;
import com.networkingandroid.network.events.SuccessAreasResponseEvent;
import com.networkingandroid.network.events.SuccessIndustriesResponseEvent;
import com.networkingandroid.network.events.UserProfileRequest;
import com.networkingandroid.network.model.LoginRequest;
import com.networkingandroid.network.events.SuccessLoginResponseEvent;
import com.networkingandroid.network.model.UserResponseDetail;
import com.networkingandroid.network.model.UserUpdateObjectRequest;
import com.networkingandroid.network.model.UserUpdateResponse;
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
        mApiClient.getEvents(requestEvents).enqueue(new Callback<EventsResponse>() {
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

    @Subscribe
    public void doLogin(LoginRequest loginRequest){
        mApiClient.doLogin(loginRequest).enqueue(new Callback<SuccessLoginResponseEvent>() {
            @Override
            public void onResponse(Call<SuccessLoginResponseEvent> call, Response<SuccessLoginResponseEvent> response) {
                if (response.isSuccessful()){
                    mBus.post(response.body());
                }
                else {
                    mBus.post(ErrorUtils.parseRretrofitError(response, mApiClient.getRetrofitAdapter()));
                }
            }
            @Override
            public void onFailure(Call<SuccessLoginResponseEvent> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void doGetIndustries(RequestIndustries requestIndustries){
        mApiClient.getIndustries().enqueue(new Callback<SuccessIndustriesResponseEvent>() {
            @Override
            public void onResponse(Call<SuccessIndustriesResponseEvent> call, Response<SuccessIndustriesResponseEvent> response) {
                if (response.isSuccessful()){
                    mBus.post(response.body());
                }
                else {
                    mBus.post(ErrorUtils.parseRretrofitError(response, mApiClient.getRetrofitAdapter()));
                }
            }

            @Override
            public void onFailure(Call<SuccessIndustriesResponseEvent> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void doGetAreas(RequestAreas requestAreas){
        mApiClient.getAreas().enqueue(new Callback<SuccessAreasResponseEvent>() {
            @Override
            public void onResponse(Call<SuccessAreasResponseEvent> call, Response<SuccessAreasResponseEvent> response) {
                if (response.isSuccessful()){
                    mBus.post(response.body());
                }
                else {
                    mBus.post(ErrorUtils.parseRretrofitError(response, mApiClient.getRetrofitAdapter()));
                }
            }

            @Override
            public void onFailure(Call<SuccessAreasResponseEvent> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void getAttendances(RequestAttendances requestAttendances){
        mApiClient.getAttendances(requestAttendances.getIdUser()).enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                if (response.isSuccessful()){
                    mBus.post(response.body());
                }
                else {
                    mBus.post(ErrorUtils.parseRretrofitError(response, mApiClient.getRetrofitAdapter()));
                }
            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void getRequestAttendance(RequestAttendanceEvent requestAttendanceEvent){
        mApiClient.getAttendanceResponse(requestAttendanceEvent.getUser_id(), requestAttendanceEvent.getEvent_id()).enqueue(new Callback<AttendanceEventResponse>() {
            @Override
            public void onResponse(Call<AttendanceEventResponse> call, Response<AttendanceEventResponse> response) {
                if (response.isSuccessful()){
                    mBus.post(response.body());
                }
                else {
                    mBus.post(ErrorUtils.parseRretrofitError(response, mApiClient.getRetrofitAdapter()));
                }
            }

            @Override
            public void onFailure(Call<AttendanceEventResponse> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void getRequestEventsByName(RequestEventsByNameEvent requestEventsByNameEvent){
        mApiClient.doGetEventsByName(requestEventsByNameEvent.getName()).enqueue(new Callback<EventsResponse>() {
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

    @Subscribe
    public void getEvents(RequestFilterEvents requestEvents){
        mApiClient.getEvents(requestEvents).enqueue(new Callback<EventsResponse>() {
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

    @Subscribe
    public void onUserUpdateRequest(UserUpdateObjectRequest userUpdateRequest){
        mApiClient.doUpdateUser(userUpdateRequest).enqueue(new Callback<UserUpdateResponse>() {
            @Override
            public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
                if (response.isSuccessful()){
                    mBus.post(response.body());
                }
                else {
                    mBus.post(ErrorUtils.parseRretrofitError(response, mApiClient.getRetrofitAdapter()));
                }
            }

            @Override
            public void onFailure(Call<UserUpdateResponse> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void onRequestUserDetail(UserProfileRequest userProfileRequest){
        mApiClient.doGetUser().enqueue(new Callback<UserResponseDetail>() {
            @Override
            public void onResponse(Call<UserResponseDetail> call, Response<UserResponseDetail> response) {
                if (response.isSuccessful()){
                    mBus.post(response.body());
                }
                else {
                    mBus.post(ErrorUtils.parseRretrofitError(response, mApiClient.getRetrofitAdapter()));
                }
            }

            @Override
            public void onFailure(Call<UserResponseDetail> call, Throwable t) {

            }
        });
    }
}