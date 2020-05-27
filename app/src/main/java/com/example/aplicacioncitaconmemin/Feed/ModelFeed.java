package com.example.aplicacioncitaconmemin.Feed;

public class ModelFeed {

    String nameF, statusF, dateF;
    int profilePicF, postPicF;

    public ModelFeed(){
        super();
    }

    public ModelFeed(String nameF, String statusF, String dateF, int profilePicF, int postPicF){
        this.nameF = nameF;
        this.statusF = statusF;
        this.dateF = dateF;
        this.profilePicF = profilePicF;
        this.postPicF = postPicF;
    }

    public String getNameF() {
        return nameF;
    }

    public void setNameF(String nameF) {
        this.nameF = nameF;
    }

    public String getStatusF() {
        return statusF;
    }

    public void setStatusF(String statusF) {
        this.statusF = statusF;
    }

    public String getDateF() {
        return dateF;
    }

    public void setDateF(String dateF) {
        this.dateF = dateF;
    }

    public int getProfilePicF() {
        return profilePicF;
    }

    public void setProfilePicF(int profilePicF) {
        this.profilePicF = profilePicF;
    }

    public int getPostPicF() {
        return postPicF;
    }

    public void setPostPicF(int postPicF) {
        this.postPicF = postPicF;
    }
}
