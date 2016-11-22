package com.elitgon.nw.network.model;

/**
 * Created by Usuario on 12/11/2016.
 */

public class IndustryAreaUser {
    private long industry_id;
    private long area_id;
    private boolean destroy;

    public IndustryAreaUser(long industry_id, long area_id, boolean destroy) {
        this.industry_id = industry_id;
        this.area_id = area_id;
        this.destroy = destroy;
    }
}
