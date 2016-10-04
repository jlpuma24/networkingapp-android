package com.networkingandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jose Rodriguez on 21/05/2016.
 */
public class PrefsUtil {

    public static final String EMAIL_ACTIVE_ACCOUNT_ID = "com.networkingandroid.EMAIL_ACTIVE_ACCOUNT_ID";
    public static final String STRING_ACTIVE_OAUTH_TOKEN = "com.networkingandroid.STRING_ACTIVE_OAUTH_TOKEN";
    public static final String IS_LOGGED = "com.networkingandroid.IS_LOGGED";
    public static final String USER_OBJECT_DATA = "com.networkingandroid.USER_OBJECT_DATA";
    public static final String NAME_USER_DATA = "com.networkingandroid.NAME_USER_DATA";
    public static final String PICTURE_USER_DATA = "com.networkingandroid.PICTURE_USER_DATA";
    public static final String USER_ID_LOGGED = "com.networkingandroid.USER_ID_LOGGED";
    private static final String PREF_NAME = "com.networkingandroid.NETWORKING_ANDROID_PREFERENCES";
    private static PrefsUtil sInstance;
    private final SharedPreferences mPreferences;

    private PrefsUtil(Context context) {
        mPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PrefsUtil(context);
        }
    }

    public static synchronized PrefsUtil getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PrefsUtil.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setIsLogged(boolean isLogged){
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putBoolean(PrefsUtil.IS_LOGGED, isLogged);
        edit.commit();
    }

    public void setUserIDLogged(long userIDLogged){
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putLong(PrefsUtil.USER_ID_LOGGED, userIDLogged);
        edit.commit();
    }

    public void setActiveAccount(String authToken, String email, String name, String picture) {
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putString(PrefsUtil.STRING_ACTIVE_OAUTH_TOKEN, authToken);
        edit.putString(PrefsUtil.EMAIL_ACTIVE_ACCOUNT_ID, email);
        edit.putString(PrefsUtil.NAME_USER_DATA, name);
        edit.putString(PrefsUtil.PICTURE_USER_DATA, picture);
        edit.commit();
    }

    public void setUserData(String userData) {
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putString(PrefsUtil.USER_OBJECT_DATA, userData);
        edit.commit();
    }

    public void clearUserData(){
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putString(PrefsUtil.USER_OBJECT_DATA, "");
        edit.commit();
    }

    public String getUserStringData(){
        return mPreferences.getString(PrefsUtil.USER_OBJECT_DATA, "");
    }

    public boolean isLogged(){
        return mPreferences.getBoolean(PrefsUtil.IS_LOGGED, false);
    }

    public SharedPreferences.Editor getEditor() {
        return mPreferences.edit();
    }

    public SharedPreferences getPrefs() {
        return mPreferences;
    }
}
