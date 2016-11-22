package com.elitgon.nw.network.events;

/**
 * Created by Usuario on 04/10/2016.
 */
public class RequestAttendances {
    private long idUser;

    public RequestAttendances(long idUser){
        this.idUser = idUser;
    }

    public long getIdUser() {
        return idUser;
    }
}
