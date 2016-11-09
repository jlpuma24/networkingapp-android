package com.networkingandroid.network.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Usuario on 17/09/2016.
 */
public class Event implements Serializable {
    private long id;
    private String name;
    private String description;
    private String schedule;
    private String avatar;
    private String cover;
    private long capacity;
    private Place place;
    private ArrayList<UserAttending> users_attending;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCover() {
        return cover;
    }

    public long getCapacity() {
        return capacity;
    }

    public Place getPlace() {
        return place;
    }

    public ArrayList<UserAttending> getUsers_attending() {
        return users_attending;
    }
}
