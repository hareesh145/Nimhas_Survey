package com.ganesh.nimhans.model;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("userId")
    int userId;

    public int getUserId() {
        return userId;
    }
}
