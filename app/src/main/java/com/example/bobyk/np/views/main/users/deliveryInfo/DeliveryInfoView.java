package com.example.bobyk.np.views.main.users.deliveryInfo;

import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.main.Point;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

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
    void successFindDeliveryLocation(List<Point> points);
    void setRecipientLocation(LatLng point);
}
