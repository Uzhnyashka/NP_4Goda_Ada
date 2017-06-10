package com.example.bobyk.np.presenters.main.mainInfo.addDelivery;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.views.main.mainInfo.addDelivery.AddDeliveryView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by bobyk on 6/10/17.
 */

public class AddDeliveryPresenter implements IAddDeliveryPresenter {

    private Activity mActivity;
    private AddDeliveryView mView;
    private DatabaseReference mDatabase;
    private Long deliveryCount;
    private String senderName;
    private String recipientName;
    private Delivery mDelivery;

    public AddDeliveryPresenter(Activity activity, AddDeliveryView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onAddDelivery(final Delivery delivery, String senderEmail, String recipientEmail) {
        mDelivery = delivery;
        mDelivery.setSenderEmail(senderEmail);
        mDelivery.setRecipientEmail(recipientEmail);
        findNames(senderEmail, recipientEmail);
//        mDatabase.child("deliveryCount").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                deliveryCount = dataSnapshot.getValue(Long.class);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        mDatabase.child("deliveries").child(deliveryCount.toString()).setValue(delivery)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            mView.onSuccessAddDelivery();
//                        } else {
//                            mView.onFailedAddDeliver(task.getException().getMessage());
//                        }
//                    }
//                });
    }

    private void findNames(final String senderEmail, final String recipientEmail) {
        System.out.println("EEE findNames " + senderEmail + " " + recipientEmail);
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean ok = false;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    User user = d.getValue(User.class);
                    System.out.println("EEE " + user.getEmail());
                    if (user.getEmail().equals(senderEmail)) {
                        ok = true;
                        senderName = user.getSurname() + " " + user.getFirstName() + " " +
                                user.getMiddleName();
                        break;
                    }
                }
                if (ok) {
                    findRecipientName(recipientEmail);
                } else {
                    mView.onFailedAddUserData("Can`t find user with " + senderEmail + " email");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("EEE cancelled");
            }
        });
    }

    private void findRecipientName(final String email) {
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean ok = false;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    User user = d.getValue(User.class);
                    if (user.getEmail().equals(email)) {
                        ok = true;
                        recipientName = user.getSurname() + " " + user.getFirstName() + " " +
                                user.getMiddleName();
                        break;
                    }
                }
                if (ok) {
                    sendUserData();
                } else {
                    mView.onFailedAddUserData("Can`t find user with " + email + " email");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("EEE cancelled");
            }
        });
    }

    private void sendUserData() {
        mDelivery.setRecipientName(recipientName);
        mDelivery.setSenderName(senderName);
        mView.onSuccessAddUserData(mDelivery);
    }
}
