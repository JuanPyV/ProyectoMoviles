package com.example.aplicacioncitaconmemin;

public class PlaceInformation {

    private String link;
    private String title;
    private String description;

    public PlaceInformation(String URL, String title2, String description2) {
        this.link = URL;
        this.title = title2;
        this.description = description2;
    }

    public PlaceInformation(){

    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setLink(String link2) {
        this.link = link2;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
