package com.networkingandroid.network.model;

/**
 * Created by Usuario on 04/10/2016.
 */
public class Attendance {
    private long id;
    private UserProfile userProfile;
    private Event event;
    private boolean status;

    public long getId() {
        return id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Event getEvent() {
        return event;
    }

    public boolean isStatus() {
        return status;
    }
}
