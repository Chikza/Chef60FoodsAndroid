package com.example.chef60foods;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String email;
    private String phoneNumber;
    private String homeAddress;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String name, String email, String phoneNumber, String homeAddress) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

}
