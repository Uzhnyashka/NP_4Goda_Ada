package com.example.bobyk.np.models.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bobyk on 6/14/17.
 */

public class ApiModule {

    public static ApiInterface getApiInterface() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://roads.googleapis.com/v1/")
                .addConverterFactory(GsonConverterFactory.create());

        ApiInterface apiInterface = builder.build().create(ApiInterface.class);
        return apiInterface;
    }

}
