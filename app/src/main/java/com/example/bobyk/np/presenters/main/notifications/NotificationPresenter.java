package com.example.bobyk.np.presenters.main.notifications;

import android.app.Activity;

import com.example.bobyk.np.models.main.Notification;
import com.example.bobyk.np.views.main.notifications.NotificationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by bobyk on 6/7/17.
 */

public class NotificationPresenter implements INotificationPresenter {

    private Activity mActivity;
    private NotificationView mView;
    private DatabaseReference mDatabase;

    public NotificationPresenter(Activity activity, NotificationView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void loadNotifications() {
        mDatabase.child("notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<Notification> notifications = dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
