package com.example.bobyk.np.views.main.users.deliveryInfo;

import com.example.bobyk.np.models.authorization.User;

/**
 * Created by bobyk on 6/11/17.
 */

public interface DeliveryInfoView {
    void successLoadSenderUser(User user);
    void successLoadRecipientUser(User user);
    void onError();
    void successSetSentStatus();
    void successSetDeliveredStatus();
    void successSetObtainedStatus();
    void successFindDeliveryLocation(Double latitude, Double longitude);
}
