package com.example.bobyk.np.views.main.mainInfo.addDelivery.chooseCity;

import com.example.bobyk.np.models.main.City;

import java.util.List;

/**
 * Created by bobyk on 6/13/17.
 */

public interface ChooseCityView {
    void successLoadCities(List<City> cities);
    void onError();
}
