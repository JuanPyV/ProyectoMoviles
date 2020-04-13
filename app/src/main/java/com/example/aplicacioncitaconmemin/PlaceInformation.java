package com.example.aplicacioncitaconmemin;

public class PlaceInformation {

    private String URL;
    private String title;
    private String description;

    public PlaceInformation(String URL, String title, String description) {
        this.URL = URL;
        this.title = title;
        this.description = description;
    }

    public PlaceInformation(){

    }

    public String getURL() {
        return URL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
