package com.example.bobyk.np.presenters.main.mainInfo.addDelivery.chooseDriver;

import android.app.Activity;

import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.views.main.mainInfo.addDelivery.chooseDriver.ChooseDriverView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 6/10/17.
 */

public class ChooseDriverPresenter implements IChooseDriverPresenter {

    private Activity mActivity;
    private ChooseDriverView mView;
    private DatabaseReference mDatabase;
    private List<Driver> driverList = new ArrayList<>();

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
}
