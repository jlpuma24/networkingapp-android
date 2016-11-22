package com.elitgon.nw.network.model;

import java.util.ArrayList;

/**
 * Created by Usuario on 25/09/2016.
 */
public class UserProfile {
    private long id;
    private String email;
    private String name;
    private String last_name;
    private String avatar;
    private String profile;
    private String phone;
    private String created_at;
    private String updated_at;
    private String authentication_token;
    private ArrayList<Industry> industries;
    private ArrayList<Area> areas;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getProfile() {
        return profile;
    }

    public String getPhone() {
        return phone;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getAuthentication_token() {
        return authentication_token;
    }

    public ArrayList<Industry> getIndustries() {
        return industries;
    }

    public ArrayList<Area> getAreas() {
        return areas;
    }
}
