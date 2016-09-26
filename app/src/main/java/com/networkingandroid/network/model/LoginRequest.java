package com.networkingandroid.network.model;

/**
 * Created by Usuario on 25/09/2016.
 */
public class LoginRequest {
    private String email;
    private String name;
    private String last_name;
    private String avatar;
    private String profile;
    private String uid;

    public LoginRequest(String email, String name, String last_name, String avatar, String profile, String uid) {
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.avatar = avatar;
        this.last_name = last_name;
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getProfile() {
        return profile;
    }

    public String getLast_name() {
        return last_name;
    }
}
