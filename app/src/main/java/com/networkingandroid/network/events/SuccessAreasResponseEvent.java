package com.networkingandroid.network.events;

import com.networkingandroid.network.model.Area;

import java.util.ArrayList;

/**
 * Created by Usuario on 25/09/2016.
 */
public class SuccessAreasResponseEvent {
    private boolean success;
    private ArrayList<Area> response;

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Area> getResponse() {
        return response;
    }
}
