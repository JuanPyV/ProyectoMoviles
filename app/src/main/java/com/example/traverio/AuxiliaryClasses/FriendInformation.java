package com.example.traverio.AuxiliaryClasses;

import androidx.annotation.NonNull;

public class FriendInformation {

    private String UID;
    private String username;

    public FriendInformation(String UID, String username) {
        this.UID = UID;
        this.username = username;
    }

    public String getUID() {
        return UID;
    }

    public String getUsername() {
        return username;
    }

    @NonNull
    @Override
    public String toString() {
        return username;
    }
}
