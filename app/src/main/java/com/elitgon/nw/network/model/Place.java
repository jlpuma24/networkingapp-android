package com.elitgon.nw.network.model;

import java.io.Serializable;

/**
 * Created by Usuario on 17/09/2016.
 */
public class Place implements Serializable{
    private long id;
    private String name;
    private String address;
    private double longitude;
    private double latitude;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
