package com.ganesh.nimhans.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
