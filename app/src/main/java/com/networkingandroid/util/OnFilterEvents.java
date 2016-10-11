package com.networkingandroid.util;

import com.networkingandroid.network.events.AreasResultEvents;
import com.networkingandroid.network.events.IndustriesResultsFilter;

import java.io.Serializable;

/**
 * Created by Usuario on 11/10/2016.
 */
public interface OnFilterEvents extends Serializable{
    void onFilterIndustryEvent(IndustriesResultsFilter industriesResultsFilter);
    void onFilterEventsEvent(AreasResultEvents areasResultEvents);
}
