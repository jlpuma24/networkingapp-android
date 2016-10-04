package com.networkingandroid.network.events;

import com.networkingandroid.network.model.Attendance;

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
