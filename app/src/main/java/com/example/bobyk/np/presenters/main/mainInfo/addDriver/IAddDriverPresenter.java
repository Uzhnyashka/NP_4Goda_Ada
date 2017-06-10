package com.example.bobyk.np.presenters.main.mainInfo.addDriver;

import com.example.bobyk.np.models.authorization.Driver;

/**
 * Created by bobyk on 6/9/17.
 */

public interface IAddDriverPresenter {
    void registerDriver(Driver driver, String password);
}
