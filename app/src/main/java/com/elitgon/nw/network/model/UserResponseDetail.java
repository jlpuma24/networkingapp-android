package com.elitgon.nw.network.model;

/**
 * Created by Usuario on 12/11/2016.
 */

public class UserResponseDetail {
    private boolean success;
    private UserProfile response;

    public boolean isSuccess() {
        return success;
    }

    public UserProfile getResponse() {
        return response;
    }
}
