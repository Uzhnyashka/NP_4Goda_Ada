package com.example.bobyk.np.presenters.main.mainInfo.addDelivery.chooseDriver;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.views.main.mainInfo.addDelivery.chooseDriver.ChooseDriverView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bobyk on 6/10/17.
 */

public class ChooseDriverPresenter implements IChooseDriverPresenter {

    private Activity mActivity;
    private ChooseDriverView mView;
    private DatabaseReference mDatabase;
    private List<Driver> driverList = new ArrayList<>();
    private int deliveryCount;
    private Delivery mDelivery;

    public ChooseDriverPresenter(Activity activity, ChooseDriverView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void loadAllDrivers() {
        driverList.clear();
        mDatabase.child("drivers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Driver driver = d.getValue(Driver.class);
                    driverList.add(driver);
                }
                mView.successLoadDriver(driverList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addDelivery(final Delivery delivery, final String email) {

        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        mDelivery = delivery;
        mDelivery.setSendDate(dayOfMonth + "." + month + "." + year);
        mDatabase.child("drivers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Driver driver = d.getValue(Driver.class);
                    if (driver.getEmail().equals(email)) {
                        mDelivery.setDriverId(d.getKey());
                        createDelivery();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void createDelivery() {
        mDatabase.child("deliveryCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long val = dataSnapshot.getValue(Long.class);
                deliveryCount = val.intValue();

                mDatabase.child("deliveries").child(String.valueOf(deliveryCount)).setValue(mDelivery)
                        .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mView.onSuccessAddDelivery();
                                } else {
                                    mView.onFailedAddDelivery(task.getException().getMessage());
                                }
                            }
                        });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
