package com.networkingandroid.network.events;

import com.networkingandroid.network.model.UserProfile;

/**
 * Created by Usuario on 25/09/2016.
 */
public class SuccessLoginResponseEvent {
    private boolean success;
    private UserProfile response;

    public boolean isSuccess() {
        return success;
    }

    public UserProfile getResponse() {
        return response;
    }
}
