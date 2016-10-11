package com.networkingandroid.network.events;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Usuario on 11/10/2016.
 */
public class AreasResultEvents implements Serializable{
    private ArrayList<String> results;

    public AreasResultEvents(ArrayList<String> results) {
        this.results = results;
    }

    public ArrayList<String> getResults() {
        return results;
    }

    public void setResults(ArrayList<String> results) {
        this.results = results;
    }
}
