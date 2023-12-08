package com.ganesh.nimhans.model;

public class UserRequest {
     final String name;
     final String age;
     final String mobileNumber;
     final String email;
     final String role;
     final String username;
     final String password;

    public UserRequest(String name, String age, String mobileNumber, String email, String role, String username, String password) {
        this.name = name;
        this.age = age;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.role = role;
        this.username = username;
        this.password = password;
    }
}
