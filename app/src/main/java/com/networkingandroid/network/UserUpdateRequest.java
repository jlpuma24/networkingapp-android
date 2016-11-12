package com.networkingandroid.network;

import com.networkingandroid.network.model.IndustryAreaUser;

import java.util.ArrayList;

/**
 * Created by Usuario on 12/11/2016.
 */

public class UserUpdateRequest {

    private String name;
    private String last_name;
    private String profile;
    private String avatar;
    private long phone;
    private String location;
    private String position;
    private String company;
    private ArrayList<IndustryAreaUser> industry_areas;

    public UserUpdateRequest(){
        
    }

    public UserUpdateRequest(String name, String last_name, String profile, String avatar, long phone, String location, String position, String company, ArrayList<IndustryAreaUser> industry_areas) {
        this.name = name;
        this.last_name = last_name;
        this.profile = profile;
        this.avatar = avatar;
        this.phone = phone;
        this.location = location;
        this.position = position;
        this.company = company;
        this.industry_areas = industry_areas;
    }
}
