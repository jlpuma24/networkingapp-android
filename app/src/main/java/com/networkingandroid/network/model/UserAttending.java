package com.networkingandroid.network.model;

import java.io.Serializable;

/**
 * Created by Usuario on 11/10/2016.
 */
public class UserAttending implements Serializable {
    private long id;
    private long user_id;
    private long event_id;
    private String name;
    private String last_name;
    private boolean status;
    private String created_at;
    private String updated_at;
    private String position;
    private String company;

    public long getId() {
        return id;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getEvent_id() {
        return event_id;
    }

    public boolean isStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getName() {
        return name;
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
}