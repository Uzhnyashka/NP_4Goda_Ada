package com.example.bobyk.np.views.main.driverDeliveries;

import com.example.bobyk.np.models.main.Delivery;

import java.util.List;

/**
 * Created by bobyk on 6/10/17.
 */

public interface DriverDeliveriesView {
    void onSuccessLoadDeliveries(List<Delivery> deliveries);
    void onError();
}
