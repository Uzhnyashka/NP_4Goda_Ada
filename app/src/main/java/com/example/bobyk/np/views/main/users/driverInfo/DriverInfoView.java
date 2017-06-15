package com.example.bobyk.np.views.main.users.driverInfo;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by bobyk on 6/15/17.
 */

public interface DriverInfoView {
    void successLoadRoutePoints(List<List<LatLng>> points);
    void onError();
}
