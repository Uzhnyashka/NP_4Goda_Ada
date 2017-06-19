package com.example.bobyk.np.presenters.main.mainInfo.tracking;

import com.example.bobyk.np.models.main.Point;

import java.util.List;

/**
 * Created by bobyk on 6/9/17.
 */

public interface ITrackingPresenter {
    void findDelivery(String id);
    void loadRoutePoints(List<Point> points);
}
