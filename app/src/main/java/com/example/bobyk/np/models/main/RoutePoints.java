package com.example.bobyk.np.models.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bobyk on 6/14/17.
 */

public class RoutePoints {

    @SerializedName("snappedPoints")
    @Expose
    private List<SnappedPoint> snappedPoints;

    public List<SnappedPoint> getSnappedPoints() {
        return snappedPoints;
    }

    public void setSnappedPoints(List<SnappedPoint> snappedPoints) {
        this.snappedPoints = snappedPoints;
    }
}
