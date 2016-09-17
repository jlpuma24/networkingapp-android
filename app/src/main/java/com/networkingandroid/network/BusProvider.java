package com.networkingandroid.network;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Usuario on 17/09/2016.
 */
public class BusProvider {

    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);

    public static Bus getBus() {
        return BUS;
    }

    private BusProvider() {
    }
}
