package com.example.bobyk.np.views.main.mainInfo.addDelivery.chooseDriver;

import com.example.bobyk.np.models.authorization.Driver;

import java.util.List;

/**
 * Created by bobyk on 6/10/17.
 */

public interface ChooseDriverView {
    void successLoadDriver(List<Driver> drivers);
    void error();
}
