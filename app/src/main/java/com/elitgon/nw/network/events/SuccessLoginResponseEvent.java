package com.elitgon.nw.network.events;

import com.elitgon.nw.network.model.UserProfile;

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
