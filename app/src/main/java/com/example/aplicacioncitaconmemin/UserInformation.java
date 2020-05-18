package com.example.aplicacioncitaconmemin;
import java.util.ArrayList;
import java.util.List;

public class UserInformation {

    private String UID;
    private String username;
    private String location;
    private String firstName;
    private String lastName;
    private String age;
    private String personalInformation;

    public UserInformation(String username, String location, String firstName, String lastName, String age, String UID) {
        this.username = username;
        this.location = location;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.UID = UID;
        this.personalInformation = null;
    }

    public UserInformation() {

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

    public String getUID() { return UID; }

    public String getPersonalInformation() { return personalInformation; }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPersonalInformation(String personalInformation) {
        this.personalInformation = personalInformation;
    }

    public String toString(){
        return username;
    }
}
