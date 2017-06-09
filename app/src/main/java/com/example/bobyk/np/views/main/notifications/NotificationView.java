package com.example.bobyk.np.views.main.notifications;

import com.example.bobyk.np.models.main.Notification;

import java.util.List;

/**
 * Created by bobyk on 6/6/17.
 */

public interface NotificationView {
    void setNotificationList(List<Notification> notifications);
}
