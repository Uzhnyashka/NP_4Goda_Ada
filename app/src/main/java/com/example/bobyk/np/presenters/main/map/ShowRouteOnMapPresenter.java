package com.example.bobyk.np.presenters.main.map;

import android.app.Activity;

import com.example.bobyk.np.models.Model;
import com.example.bobyk.np.models.ModelImpl;
import com.example.bobyk.np.models.main.Point;
import com.example.bobyk.np.models.main.RoutePoints;
import com.example.bobyk.np.models.main.SnappedPoint;
import com.example.bobyk.np.views.main.map.ShowRouteOnMapView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bobyk on 6/15/17.
 */

public class ShowRouteOnMapPresenter implements IShowRouteOnMapPresenter {

    private Activity mActivity;
    private ShowRouteOnMapView mView;
    private List<List<LatLng>> mPartsOfPoints = new ArrayList<>();
    private Model model = new ModelImpl();
    private Call<RoutePoints> mRoutePointCB;
    private List<List<Point>> mPoints = new ArrayList<List<Point>>();
    private int kol = -1;

    public ShowRouteOnMapPresenter(Activity activity, ShowRouteOnMapView view) {
        mActivity = activity;
        mView = view;
    }

    @Override
    public void loadRoutePoints(List<Point> points) {
        mPoints.clear();
        List<Point> pointList = new ArrayList<>();
        int k = 0;
        for (Point point : points) {
            k++;
            if (pointList.size() < 100) {
                pointList.add(point);
            } else {
                Point point1 = pointList.get(pointList.size() - 1);
                mPoints.add(pointList);
                pointList = new ArrayList<>();
                pointList.add(point1);
                pointList.add(point);
            }
        }
        if (pointList.size() > 1) {
            mPoints.add(pointList);
        }
        next();
    }

    private void loadPartOfPoints(List<Point> points) {
        String s = getPointsString(points);
        mRoutePointCB = model.getRoutePoints(s, "AIzaSyAisRRKbxyglbref6-OrgCnROtXHx83WAg");
        mRoutePointCB.enqueue(new Callback<RoutePoints>() {
            @Override
            public void onResponse(Call<RoutePoints> call, Response<RoutePoints> response) {
                if (response.code() == 200 && response.body() != null) {
                    RoutePoints snappedPoints = response.body();
                    List<LatLng> latLngs = new ArrayList<LatLng>();
                    for (SnappedPoint snappedPoint : snappedPoints.getSnappedPoints()) {
                        latLngs.add(new LatLng(snappedPoint.getLocation().getLatitude(), snappedPoint.getLocation().getLongitude()));
                    }

                    mPartsOfPoints.add(latLngs);
                }
                next();
            }



            @Override
            public void onFailure(Call<RoutePoints> call, Throwable t) {
                mView.onError();
            }
        });
    }

    private String getPointsString(List<Point> points) {
        String res = "";
        for (Point point : points) {
            if (!res.equals("")) {
                res += "|";
            }
            res += point.getLatitude() + "," + point.getLongitude();
        }
        return res;
    }

    private void next() {
        kol++;
        if (kol <= mPoints.size() - 1) {
            loadPartOfPoints(mPoints.get(kol));
        }
        else {
            mView.successLoadRoutePoints(mPartsOfPoints);
        }
    }
}
