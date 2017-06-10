package com.example.bobyk.np.views.main.mainInfo.addDelivery;

import com.example.bobyk.np.models.main.Delivery;

/**
 * Created by bobyk on 6/10/17.
 */

public interface AddDeliveryView {
    void onSuccessAddUserData(Delivery delivery);
    void onFailedAddUserData(String message);
}
