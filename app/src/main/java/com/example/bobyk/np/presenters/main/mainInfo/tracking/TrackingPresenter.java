package com.example.bobyk.np.presenters.main.mainInfo.tracking;

import android.app.Activity;

import com.example.bobyk.np.models.Model;
import com.example.bobyk.np.models.ModelImpl;
import com.example.bobyk.np.models.main.City;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.models.main.Point;
import com.example.bobyk.np.models.main.RoutePoints;
import com.example.bobyk.np.models.main.SnappedPoint;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.mainInfo.tracking.TrackingView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bobyk on 6/9/17.
 */

public class TrackingPresenter implements ITrackingPresenter {

    private Activity mActivity;
    private TrackingView mView;
    private DatabaseReference mDatabase;
    private List<List<LatLng>> mPartsOfPoints = new ArrayList<>();
    private Model model = new ModelImpl();
    private Call<RoutePoints> mRoutePointCB;
    private List<List<Point>> mPoints = new ArrayList<List<Point>>();
    private int kol = -1;

    public TrackingPresenter(Activity activity, TrackingView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void findDelivery(String id) {
        mDatabase.child("deliveries").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Delivery delivery = dataSnapshot.getValue(Delivery.class);
                if (delivery != null) {
                    findDriver(delivery.getDriverId());
                    findRecipientLocation(delivery.getRecipientLocation());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Utils.showToastMessage(mActivity, "Can`t find delivery");
            }
        });
    }

    private void findRecipientLocation(String name) {
        mDatabase.child("cities").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                City city = dataSnapshot.getValue(City.class);
                if (city != null) {
                    mView.setRecipientLocation(new LatLng(city.getLatitude(), city.getLongitude()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void findDriver(String id) {
        mDatabase.child("drivers").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Driver driver = dataSnapshot.getValue(Driver.class);
                if (driver != null) {
                    if (driver.getPoints() == null) {
                        List<Point> points = new ArrayList<>();
                        points.add(new Point(0d, 0d));
                        driver.setPoints(points);
                    }
                    List<Point> points = driver.getPoints();

                    mView.setDeliveryPoint(points);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
