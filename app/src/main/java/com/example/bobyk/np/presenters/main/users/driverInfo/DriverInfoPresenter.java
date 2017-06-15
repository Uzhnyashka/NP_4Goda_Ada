package com.example.bobyk.np.presenters.main.users.driverInfo;

import android.app.Activity;

import com.example.bobyk.np.models.Model;
import com.example.bobyk.np.models.ModelImpl;
import com.example.bobyk.np.models.main.Point;
import com.example.bobyk.np.models.main.RoutePoints;
import com.example.bobyk.np.models.main.SnappedPoint;
import com.example.bobyk.np.views.main.users.driverInfo.DriverInfoView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
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
    private List<List<LatLng>> mPartsOfPoints;
    private DriverInfoView mView;

    public DriverInfoPresenter(Activity activity, DriverInfoView view) {
        mActivity = activity;
        mView = view;
    }


}
