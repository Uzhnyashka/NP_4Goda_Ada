package com.example.bobyk.np.models.main;

/**
 * Created by bobyk on 6/12/17.
 */

public class Point {

    private Double latitude;
    private Double longitude;

    public Point() {

    }

    public Point(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
