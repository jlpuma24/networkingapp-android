package com.elitgon.nw.network.events;

/**
 * Created by Usuario on 11/10/2016.
 */
public class RequestEventsByNameEvent {
    private String name;

    public RequestEventsByNameEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
