package com.elitgon.nw.network.events;

import com.elitgon.nw.network.model.Attendance;

/**
 * Created by Usuario on 04/10/2016.
 */
public class AttendanceEventResponse {
    private boolean success;
    private Attendance response;

    public boolean isSuccess() {
        return success;
    }

    public Attendance getResponse() {
        return response;
    }
}
