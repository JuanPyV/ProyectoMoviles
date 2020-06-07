package com.example.traverio.AuxiliaryClasses;

public class PersonRating {

    private double rating;
    private String UID;

    public PersonRating(double rating, String UID) {
        this.rating = rating;
        this.UID = UID;
    }

    public PersonRating(){

    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
