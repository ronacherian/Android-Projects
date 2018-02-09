package com.example.roncherian.mytrips;

/**
 * Created by roncherian on 04/12/17.
 */

public class Places {
    String placeId;

    public String getGeoPlaceId() {
        return geoPlaceId;
    }

    public void setGeoPlaceId(String geoPlaceId) {
        this.geoPlaceId = geoPlaceId;
    }

    String geoPlaceId;
    String plcaeName;


    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    String tripId;

    public String getPlcaeName() {
        return plcaeName;
    }

    public void setPlcaeName(String plcaeName) {
        this.plcaeName = plcaeName;
    }

    public String getPlaceId() {

        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
