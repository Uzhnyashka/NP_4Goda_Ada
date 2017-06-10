package com.example.bobyk.np.presenters.main.mainInfo.addDelivery;

import com.example.bobyk.np.models.main.Delivery;

/**
 * Created by bobyk on 6/10/17.
 */

public interface IAddDeliveryPresenter {
    void onAddDelivery(Delivery delivery, String senderEmail, String recipientEmail);
}
