package com.elitgon.nw.util;

import com.elitgon.nw.network.events.AreasResultEvents;
import com.elitgon.nw.network.events.IndustriesResultsFilter;

import java.io.Serializable;

/**
 * Created by Usuario on 11/10/2016.
 */
public interface OnFilterEvents extends Serializable{
    void onFilterIndustryEvent(IndustriesResultsFilter industriesResultsFilter);
    void onFilterEventsEvent(AreasResultEvents areasResultEvents);
}
