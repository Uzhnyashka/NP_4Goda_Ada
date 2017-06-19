package com.example.bobyk.np.views.main.mainInfo.tracking;

import com.example.bobyk.np.models.main.Point;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by bobyk on 6/6/17.
 */

public interface TrackingView {
    void setDeliveryPoint(List<Point> points);
    void successLoadRoutePoints(List<List<LatLng>> point);
    void onError();
    void setRecipientLocation(LatLng point);
}
