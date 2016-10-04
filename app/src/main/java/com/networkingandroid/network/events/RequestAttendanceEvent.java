package com.networkingandroid.network.events;

/**
 * Created by Usuario on 04/10/2016.
 */
public class RequestAttendanceEvent {
    private long user_id;
    private long event_id;

    public RequestAttendanceEvent(long user_id, long event_id) {
        this.user_id = user_id;
        this.event_id = event_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getEvent_id() {
        return event_id;
    }
}
