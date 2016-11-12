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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ArrayList<IndustryAreaUser> getIndustry_areas() {
        return industry_areas;
    }

    public void setIndustry_areas(ArrayList<IndustryAreaUser> industry_areas) {
        this.industry_areas = industry_areas;
    }
}
