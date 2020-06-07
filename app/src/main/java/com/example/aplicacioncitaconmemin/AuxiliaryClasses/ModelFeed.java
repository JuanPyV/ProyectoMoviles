package com.example.aplicacioncitaconmemin.AuxiliaryClasses;

public class ModelFeed {

    String nameF, statusF, dateF, UID, postPicF, profilePicF;

    public ModelFeed(){
        super();
    }

    public ModelFeed(String nameF, String statusF, String dateF, String profilePicF, String postPicF, String UID){
        this.nameF = nameF;
        this.statusF = statusF;
        this.dateF = dateF;
        this.profilePicF = profilePicF;
        this.postPicF = postPicF;
        this.UID = UID;
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

    public String getProfilePicF() {
        return profilePicF;
    }

    public void setProfilePicF(String profilePicF) {
        this.profilePicF = profilePicF;
    }

    public String getPostPicF() {
        return postPicF;
    }

    public void setPostPicF(String postPicF) {
        this.postPicF = postPicF;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
