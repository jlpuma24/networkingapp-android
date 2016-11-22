package com.elitgon.nw.network.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Usuario on 10/10/2016.
 */
public class ApplicationData implements Serializable{
    private ArrayList<Industry> industryArrayList;
    private ArrayList<Area> areaArrayList;

    public ApplicationData(){

    }

    public ArrayList<Industry> getIndustryArrayList() {
        return industryArrayList;
    }

    public void setIndustryArrayList(ArrayList<Industry> industryArrayList) {
        this.industryArrayList = industryArrayList;
    }

    public ArrayList<Area> getAreaArrayList() {
        return areaArrayList;
    }

    public void setAreaArrayList(ArrayList<Area> areaArrayList) {
        this.areaArrayList = areaArrayList;
    }
}
