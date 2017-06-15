package com.example.bobyk.np.views.main.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by bobyk on 6/15/17.
 */

public interface ShowRouteOnMapView {
    void successLoadRoutePoints(List<List<LatLng>> points);
    void onError();
}
