package com.elitgon.nw.network;

import android.content.Context;

import com.elitgon.nw.network.events.AttendanceEventResponse;
import com.elitgon.nw.network.events.AttendanceResponse;
import com.elitgon.nw.network.events.EventsResponse;
import com.elitgon.nw.network.events.RequestEvents;
import com.elitgon.nw.network.events.RequestFilterEvents;
import com.elitgon.nw.network.events.SuccessAreasResponseEvent;
import com.elitgon.nw.network.events.SuccessIndustriesResponseEvent;
import com.elitgon.nw.network.model.LoginRequest;
import com.elitgon.nw.network.events.SuccessLoginResponseEvent;
import com.elitgon.nw.network.model.UserResponseDetail;
import com.elitgon.nw.network.model.UserUpdateObjectRequest;
import com.elitgon.nw.network.model.UserUpdateResponse;
import com.elitgon.nw.util.PrefsUtil;
import com.squareup.otto.Bus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jose Rodriguez on 20/05/2016.
 */
public class ApiClient {

    private static final String BASE_URL = "http://nwmeeting.com/";
    private static ApiClient mApiClient;
    private static Retrofit retrofitAdapter;
    private static HttpLoggingInterceptor interceptor;
    private static OkHttpClient client;
    private Bus mBus = BusProvider.getBus();

    public static ApiClient getInstance(Context mContext) {
        if (mApiClient == null)
            mApiClient = new ApiClient(mContext);
        return mApiClient;
    }

    private ApiClient(Context mContext) {

        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(interceptor);

        Interceptor agentInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder();

                if (getActiveAccountAuthToken() != null) {
                    //requestBuilder.addHeader("X-User-Token", getActiveAccountAuthToken());
                    //requestBuilder.addHeader("X-User-Email", getActiveAccountEmail());
                    requestBuilder.addHeader("X-User-Token", "RzH8xtXyg9RH5hH--k8U");
                    requestBuilder.addHeader("X-User-Email", "zaeta88@gmail.com");

                }
                return chain.proceed(requestBuilder.build());
            }
        };

        clientBuilder.addInterceptor(agentInterceptor);
        client = clientBuilder.build();
        retrofitAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public String getActiveAccountAuthToken() {
        try {
            String token = PrefsUtil.getInstance().getPrefs().getString(PrefsUtil.STRING_ACTIVE_OAUTH_TOKEN, null);
            if (token != null && !token.isEmpty()) {
                StringBuilder tokenSB = new StringBuilder();
                tokenSB.append(token);
                return tokenSB.toString();
            } else return null;
        } catch (RuntimeException e) {
            return null;
        }
    }

    public String getActiveAccountEmail() {
        try {
            String token = PrefsUtil.getInstance().getPrefs().getString(PrefsUtil.EMAIL_ACTIVE_ACCOUNT_ID, null);
            if (token != null && !token.isEmpty()) {
                StringBuilder tokenSB = new StringBuilder();
                tokenSB.append(token);
                return tokenSB.toString();
            } else return null;
        } catch (RuntimeException e) {
            return null;
        }
    }

    public Call<EventsResponse> getEvents(RequestEvents requestEvents){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.getEvents(requestEvents.getPage(), requestEvents.getOffset());
    }

    public Call<EventsResponse> getEvents(RequestFilterEvents requestEvents){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.getEvents(requestEvents.getPage(), requestEvents.getOffset());
    }

    public Call<SuccessIndustriesResponseEvent> getIndustries(){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.doGetIndustries();
    }

    public Call<SuccessAreasResponseEvent> getAreas(){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.doGetAreas();
    }

    public Call<AttendanceResponse> getAttendances(long id){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.doGetAttendances(id);
    }

    public Call<AttendanceEventResponse> getAttendanceResponse(long user_id, long event_id){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.doMakeAttendance(user_id, event_id);
    }

    public Call<SuccessLoginResponseEvent> doLogin(LoginRequest loginRequest){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.doLogin(loginRequest.getEmail(), loginRequest.getName(), loginRequest.getLast_name(), loginRequest.getAvatar(), loginRequest.getProfile(), loginRequest.getUid(), loginRequest.getCompany(), loginRequest.getPosition(), loginRequest.getLocation());
    }

    public Call<UserUpdateResponse> doUpdateUser(UserUpdateObjectRequest userUpdateRequest){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.doUpdateUser(PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED, 0), userUpdateRequest);
    }

    public Call<EventsResponse> doGetEventsByName(String name){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.getEventsByText(name);
    }

    public Call<UserResponseDetail> doGetUser(){
        ApiService apiService = retrofitAdapter.create(ApiService.class);
        return apiService.doGetUser(PrefsUtil.getInstance().getPrefs().getLong(PrefsUtil.USER_ID_LOGGED, 0));
    }

    public static Retrofit getRetrofitAdapter() {
        return retrofitAdapter;
    }

    public static void setRetrofitAdapter(Retrofit retrofitAdapter) {
        ApiClient.retrofitAdapter = retrofitAdapter;
    }
}