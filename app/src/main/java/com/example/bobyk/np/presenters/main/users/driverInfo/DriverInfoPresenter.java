package com.example.bobyk.np.presenters.main.users.driverInfo;

import android.app.Activity;

import com.example.bobyk.np.models.Model;
import com.example.bobyk.np.models.ModelImpl;
import com.example.bobyk.np.models.main.Point;
import com.example.bobyk.np.models.main.RoutePoints;
import com.example.bobyk.np.models.main.SnappedPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bobyk on 6/14/17.
 */

public class DriverInfoPresenter implements IDriverInfoPresenter {

    private Activity mActivity;
    private Model model = new ModelImpl();
    private Call<RoutePoints> mRoutePointCB;

    public DriverInfoPresenter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void loadRoutePoints(List<Point> points) {
        System.out.println("WWW loadRoutePoints");
        String s = "48.610451,22.262313|48.611345,22.264481|48.608947,22.266626|48.608891,22.269158|48.609557,22.272248";
        mRoutePointCB = model.getRoutePoints(s, "AIzaSyAisRRKbxyglbref6-OrgCnROtXHx83WAg");
        mRoutePointCB.enqueue(new Callback<RoutePoints>() {
            @Override
            public void onResponse(Call<RoutePoints> call, Response<RoutePoints> response) {
                System.out.println("WWW onResponse");
                if (response.code() == 200 && response.body() != null) {
                    RoutePoints snappedPoints = response.body();

                    for (SnappedPoint snappedPoint : snappedPoints.getSnappedPoints()) {
                        System.out.println("WWW snappedPoint " + snappedPoint.getPlaceId());
                    }
                }
            }

            @Override
            public void onFailure(Call<RoutePoints> call, Throwable t) {

            }
        });
    }
}
