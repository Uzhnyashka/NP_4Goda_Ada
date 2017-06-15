package com.example.bobyk.np.presenters.main.users.deliveryInfo;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.main.Point;
import com.example.bobyk.np.views.main.users.deliveryInfo.DeliveryInfoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bobyk on 6/11/17.
 */

public class DeliveryInfoPresenter implements IDeliveryInfoPresenter {

    private Activity mActivity;
    private DeliveryInfoView mView;
    private DatabaseReference mDatabase;
    private String driverId;
    private Double latitude;
    private Double longitude;

    public DeliveryInfoPresenter(Activity activity, DeliveryInfoView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void loadSenderUser(String userId) {
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    mView.successLoadSenderUser(user);
                } else {
                    mView.onError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }

    @Override
    public void loadRecipientUser(String userId) {
        System.out.println("WWW userId " + userId);
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    mView.successLoadRecipientUser(user);
                } else {
                    mView.onError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }

    @Override
    public void setSendStatus(String deliveryId) {
        mDatabase.child("deliveries").child(deliveryId).child("status").setValue("Sent")
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mView.successSetSentStatus();
                        } else {
                            mView.onError();
                        }
                    }
                });
    }

    @Override
    public void setDeliveredStatus(String deliveryId) {
        mDatabase.child("deliveries").child(deliveryId).child("status").setValue("Delivered")
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mView.successSetDeliveredStatus();
                        } else {
                            mView.onError();
                        }
                    }
                });
    }

    @Override
    public void setObtainedStatus(String deliveryId) {
        mDatabase.child("deliveries").child(deliveryId).child("status").setValue("Obtained")
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mView.successSetObtainedStatus();
                        } else {
                            mView.onError();
                        }
                    }
                });
    }

    @Override
    public void findDelivery(String deliveryId) {
        mDatabase.child("deliveries").child(deliveryId).child("driverId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                driverId = dataSnapshot.getValue(String.class);
                findDriver();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }

    private void findDriver() {
        mDatabase.child("drivers").child(driverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Driver driver = dataSnapshot.getValue(Driver.class);
                if (driver != null) {
                    if (driver.getPoints() == null) {
                        List<Point> points = new ArrayList<>();
                        points.add(new Point(0d, 0d));
                        driver.setPoints(points);
                    }
                    List<Point> points = driver.getPoints();
                    mView.successFindDeliveryLocation(points);
                } else {
                    mView.onError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }
}
