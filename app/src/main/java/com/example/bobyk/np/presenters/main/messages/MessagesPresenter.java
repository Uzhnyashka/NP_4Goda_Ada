package com.example.bobyk.np.presenters.main.messages;

import android.app.Activity;

import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.models.main.Message;
import com.example.bobyk.np.models.main.Notification;
import com.example.bobyk.np.views.main.messages.MessagesView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 6/7/17.
 */

public class MessagesPresenter implements IMessagesPresenter {

    private Activity mActivity;
    private MessagesView mView;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    private List<Message> messages;
    private int pos;

    public MessagesPresenter(Activity activity, MessagesView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void loadNotifications() {
        mDatabase.child("messages").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                messages = new ArrayList<Message>();
                for (MutableData childSnapshot : mutableData.getChildren()) {
                    Message message = childSnapshot.getValue(Message.class);
                    if (message.getRecipientId().equals(mUser.getUid())) {
                        messages.add(message);
                    }
                }
                findDeliveryData();
//                mView.setNotificationList(messages);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    private void findDeliveryData() {
        pos = -1;
        next();
    }

    private void next() {
        pos++;
        final Message message = messages.get(pos);

        mDatabase.child("deliveries").child(message.getDeliveryId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Delivery delivery = dataSnapshot.getValue(Delivery.class);
                if (delivery != null) {
                    messages.get(pos).setSenderFullName(delivery.getSenderName());
                    messages.get(pos).setRecipientFullName(delivery.getRecipientName());
                    messages.get(pos).setSenderLocation(delivery.getSenderLocation());
                    messages.get(pos).setRecipientFullName(delivery.getRecipientLocation());
                }
                if (pos == messages.size() - 1) {
                    mView.setNotificationList(messages);
                } else {
                    next();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }
}
