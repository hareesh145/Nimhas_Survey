package com.ganesh.nimhans.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("usercode")
    String usercode;
    @SerializedName("userId")
    int userId;
    @SerializedName("role")
    String role;

    public String getUsercode() {
        return usercode;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
