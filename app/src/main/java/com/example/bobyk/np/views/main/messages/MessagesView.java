package com.example.bobyk.np.views.main.messages;

import com.example.bobyk.np.models.main.Message;

import java.util.List;

/**
 * Created by bobyk on 6/6/17.
 */

public interface MessagesView {
    void setNotificationList(List<Message> notifications);
    void onError();
}
