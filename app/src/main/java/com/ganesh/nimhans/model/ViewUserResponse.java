package com.ganesh.nimhans.model;

import com.google.gson.annotations.SerializedName;

public class ViewUserResponse {
    @SerializedName("userId")
    int userId;
    @SerializedName("name")
    String name;
    @SerializedName("age")
    String age;
    @SerializedName("mobileNumber")
    String mobileNumber;
    @SerializedName("email")
    String email;
    @SerializedName("role")
    String role;
    @SerializedName("username")
    String username;

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
}
