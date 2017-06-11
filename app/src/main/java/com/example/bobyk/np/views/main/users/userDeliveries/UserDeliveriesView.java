package com.example.bobyk.np.views.main.users.userDeliveries;

import com.example.bobyk.np.models.main.Delivery;

import java.util.List;

/**
 * Created by bobyk on 6/11/17.
 */

public interface UserDeliveriesView {
    void successLoadUserDeliveries(List<Delivery> deliveries);
    void onError();
}
