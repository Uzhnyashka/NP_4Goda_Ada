package com.example.bobyk.np.models.api;

import com.example.bobyk.np.models.main.RoutePoints;
import com.example.bobyk.np.models.main.SnappedPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bobyk on 6/14/17.
 */

public interface ApiInterface {

    @GET("snapToRoads?interpolate=true")
    Call<RoutePoints> getRoutePoints(@Query("path") String points, @Query("key") String tokenAPI);
}
