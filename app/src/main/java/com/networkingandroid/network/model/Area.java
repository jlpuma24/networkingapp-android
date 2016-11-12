package com.networkingandroid.network.model;

import java.io.Serializable;

/**
 * Created by Usuario on 25/09/2016.
 */
public class Area implements Serializable {
    private long id;
    private String name;
    private long industry_id;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getIndustry_id() {
        return industry_id;
    }
}
