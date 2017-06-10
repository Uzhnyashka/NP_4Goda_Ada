package com.example.bobyk.np.views.main.mainInfo.deliveries;

import com.example.bobyk.np.models.main.Delivery;

import java.util.List;

/**
 * Created by bobyk on 6/6/17.
 */

public interface DeliveriesView {
    void onSuccessLoadDeliveries(List<Delivery> deliveries);
    void onError();
}
