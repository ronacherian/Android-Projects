package com.example.roncherian.mytrips;

/**
 * Created by roncherian on 04/12/17.
 */

public class TripAndPlaces {

    String tripId;

    public String getTripGeoId() {
        return tripGeoId;
    }

    public void setTripGeoId(String tripGeoId) {
        this.tripGeoId = tripGeoId;
    }

    String tripGeoId;
    String placeId;
    String tripName;
    String cityName;
    String placeName;
    boolean isTripObj = false;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }



    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public boolean isTripObj() {
        return isTripObj;
    }

    public void setTripObj(boolean tripObj) {
        isTripObj = tripObj;
    }

    @Override
    public boolean equals(Object obj) {
        TripAndPlaces tripAndPlaces = (TripAndPlaces) obj;
        return this.getTripId().equals(tripAndPlaces.getTripId());
    }
}
