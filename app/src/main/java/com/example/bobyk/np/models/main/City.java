package com.example.bobyk.np.models.main;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by bobyk on 6/13/17.
 */

@IgnoreExtraProperties
public class City {

    private String name;
    private Double latitude;
    private Double longitude;

    public City() {

    }

    public City(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
