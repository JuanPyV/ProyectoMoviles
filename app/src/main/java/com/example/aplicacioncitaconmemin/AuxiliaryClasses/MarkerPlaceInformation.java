package com.example.aplicacioncitaconmemin.AuxiliaryClasses;

import com.google.android.gms.maps.model.Marker;

public class MarkerPlaceInformation {

    private Marker marker;
    private PlaceInformation placeInformation;

    public MarkerPlaceInformation(Marker marker, PlaceInformation placeInformation) {
        this.marker = marker;
        this.placeInformation = placeInformation;
    }

    public MarkerPlaceInformation(){

    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public PlaceInformation getPlaceInformation() {
        return placeInformation;
    }

    public void setPlaceInformation(PlaceInformation placeInformation) {
        this.placeInformation = placeInformation;
    }
}
