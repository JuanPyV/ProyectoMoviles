package com.example.aplicacioncitaconmemin;

public class PlaceInformation {


    private double latitude, longitude, rating;
    private String title, imageLink, description, snippet;

    public PlaceInformation(String URL, String title2, String description2) {
        this.imageLink = URL;
        this.title = title2;
        this.description = description2;
    }

    public PlaceInformation(double latitude, double longitude, String title, String imageLink, String description, String snippet) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.imageLink = imageLink;
        this.description = description;
        this.snippet = snippet;
        this.rating = 0;
    }

    public PlaceInformation(){

    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
