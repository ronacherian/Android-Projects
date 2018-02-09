package com.example.roncherian.mytrips;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by roncherian on 04/12/17.
 */

public class Trip implements Serializable{
    String tripId;
    String tripName;
    String cityName;

    String lat, lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTripGeoId() {
        return tripGeoId;
    }

    public void setTripGeoId(String tripGeoId) {
        this.tripGeoId = tripGeoId;
    }

    String tripGeoId;

    ArrayList<Places>places = new ArrayList<>();

    public ArrayList<Places> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Places> places) {
        this.places = places;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
