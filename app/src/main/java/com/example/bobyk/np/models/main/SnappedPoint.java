package com.example.bobyk.np.models.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bobyk on 6/14/17.
 */

public class SnappedPoint {

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("originalIndex")
    @Expose
    private Long originalIndex;

    @SerializedName("placeId")
    @Expose
    private String placeId;

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setOriginalIndex(Long originalIndex) {
        this.originalIndex = originalIndex;
    }

    public Long getOriginalIndex() {
        return originalIndex;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceId() {
        return placeId;
    }
}
