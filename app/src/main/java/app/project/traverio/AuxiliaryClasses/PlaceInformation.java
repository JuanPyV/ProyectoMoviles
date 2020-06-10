package app.project.traverio.AuxiliaryClasses;

import java.util.List;

public class PlaceInformation {


    private double latitude, longitude;
    private String title, imageLink, description, snippet;
    private List<PersonRating> personRatings;



    public PlaceInformation(double latitude, double longitude, String title, String imageLink, String description, String snippet) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.imageLink = imageLink;
        this.description = description;
        this.snippet = snippet;

    }

    public PlaceInformation(){

    }

    public List<PersonRating> getPersonRatings() {
        return personRatings;
    }

    public void setPersonRatings(List<PersonRating> personRatings) {
        this.personRatings = personRatings;
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
