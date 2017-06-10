package com.example.bobyk.np.views.main.users.showDrivers;

import com.example.bobyk.np.models.authorization.Driver;

import java.util.List;

/**
 * Created by bobyk on 6/10/17.
 */

public interface ShowDriversView {
    void onSuccessLoadDrivers(List<Driver> drivers);
    void onError();
}
