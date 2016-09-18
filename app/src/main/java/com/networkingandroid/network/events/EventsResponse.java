package com.networkingandroid.network.events;

import com.networkingandroid.network.model.Event;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/09/2016.
 */
public class EventsResponse {
    private boolean success;
    private ArrayList<Event> response;

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Event> getResponse() {
        return response;
    }
}
