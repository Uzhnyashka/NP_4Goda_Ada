package com.example.bobyk.np.presenters.main.map;

import com.example.bobyk.np.models.main.Point;

import java.util.List;

/**
 * Created by bobyk on 6/15/17.
 */

public interface IShowRouteOnMapPresenter {
    void loadRoutePoints(List<Point> points);
}
