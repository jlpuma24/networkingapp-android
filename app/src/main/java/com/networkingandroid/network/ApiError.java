package com.networkingandroid.network;

/**
 * Created by Jose Rodriguez on 20/05/2016.
 */
public class ApiError {

    private String error;
    private String message;
    private long status;
    private Object data;

    public ApiError() {
    }

    public ApiError(String message){
        this.message = message;
    }

    public long status() {
        return status;
    }

    public String message() {
        return message;
    }

    public Object data(){
        return data;
    }

}