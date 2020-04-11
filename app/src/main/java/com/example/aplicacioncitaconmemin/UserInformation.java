package com.example.aplicacioncitaconmemin;

import java.util.Date;

public class UserInformation {

    private String username;
    private String location;
    private String firstName;
    private String lastName;
    private String age;

    public UserInformation(String username, String location, String firstName, String lastName, String age) {
        this.username = username;
        this.location = location;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }
}
