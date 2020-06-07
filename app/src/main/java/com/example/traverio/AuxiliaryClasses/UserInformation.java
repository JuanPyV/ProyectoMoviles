package com.example.traverio.AuxiliaryClasses;
import java.util.List;

public class UserInformation {

    private String UID, username, location, firstName, lastName, age, personalInformation, profilePicURL;
    private List<PersonRating> personRatings;

    public UserInformation(String username, String location, String firstName, String lastName, String age, String UID, String bio) {
        this.username = username;
        this.location = location;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.UID = UID;
        this.personalInformation = bio;
        this.profilePicURL = "";
    }

    public UserInformation() {

    }

    public List<PersonRating> getPersonRatings() {
        return personRatings;
    }

    public void setPersonRatings(List<PersonRating> personRatings) {
        this.personRatings = personRatings;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
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
