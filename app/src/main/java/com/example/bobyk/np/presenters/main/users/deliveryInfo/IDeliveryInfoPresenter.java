package com.example.bobyk.np.presenters.main.users.deliveryInfo;

/**
 * Created by bobyk on 6/11/17.
 */

public interface IDeliveryInfoPresenter {
    void loadSenderUser(String userId);
    void loadRecipientUser(String userId);
    void setSendStatus(String deliveryId);
    void setDeliveredStatus(String deliveryId);
    void setObtainedStatus(String deliveryId);
    void findDelivery(String deliveryId);
}
