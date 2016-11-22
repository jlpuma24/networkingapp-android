package com.elitgon.nw.network.events;

import com.elitgon.nw.network.model.Event;

import java.util.ArrayList;

/**
 * Created by Usuario on 04/10/2016.
 */
public class AttendanceResponse {
    private boolean success;
    private ArrayList<Event> response;

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Event> getResponse() {
        return response;
    }
}
