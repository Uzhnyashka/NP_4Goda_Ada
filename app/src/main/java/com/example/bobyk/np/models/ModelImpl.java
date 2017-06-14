package com.example.bobyk.np.models;

import com.example.bobyk.np.models.api.ApiInterface;
import com.example.bobyk.np.models.api.ApiModule;
import com.example.bobyk.np.models.main.RoutePoints;
import com.example.bobyk.np.models.main.SnappedPoint;

import java.util.List;

import retrofit2.Call;

/**
 * Created by bobyk on 6/14/17.
 */

public class ModelImpl implements Model {

    ApiInterface apiInterface = ApiModule.getApiInterface();

    @Override
    public Call<RoutePoints> getRoutePoints(String points, String tokenAPI) {
        return apiInterface.getRoutePoints(points, tokenAPI);
    }
}
