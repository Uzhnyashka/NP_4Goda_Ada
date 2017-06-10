package com.example.bobyk.np.presenters.main.mainInfo.addDelivery.chooseDriver;

import com.example.bobyk.np.models.main.Delivery;

/**
 * Created by bobyk on 6/10/17.
 */

public interface IChooseDriverPresenter {
    void loadAllDrivers();
    void addDelivery(Delivery delivery, String email);
}
