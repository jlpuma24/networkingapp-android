package com.elitgon.nw.network.events;

import com.elitgon.nw.network.model.Industry;

import java.util.ArrayList;

/**
 * Created by Usuario on 25/09/2016.
 */
public class SuccessIndustriesResponseEvent {
    private boolean success;
    private ArrayList<Industry> response;

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Industry> getResponse() {
        return response;
    }
}
