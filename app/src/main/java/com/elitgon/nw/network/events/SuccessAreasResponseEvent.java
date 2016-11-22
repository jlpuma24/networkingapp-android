package com.elitgon.nw.network.events;

import com.elitgon.nw.network.model.Area;

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
