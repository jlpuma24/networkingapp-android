package com.elitgon.nw.network.model;

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
    private String company;
    private String position;
    private String location;

    public LoginRequest(String email, String name, String last_name, String avatar, String profile, String uid, String company, String position, String location) {
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.avatar = avatar;
        this.last_name = last_name;
        this.profile = profile;
        this.company = company;
        this.position = position;
        this.location = location;
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

    public String getCompany() {
        return company;
    }

    public String getPosition() {
        return position;
    }

    public String getLocation() {
        return location;
    }
}
