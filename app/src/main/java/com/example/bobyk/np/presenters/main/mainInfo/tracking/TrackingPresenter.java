package com.example.bobyk.np.presenters.main.mainInfo.tracking;

import android.app.Activity;

import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.mainInfo.tracking.TrackingView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by bobyk on 6/9/17.
 */

public class TrackingPresenter implements ITrackingPresenter {

    private Activity mActivity;
    private TrackingView mView;
    private DatabaseReference mDatabase;

    public TrackingPresenter(Activity activity, TrackingView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void findDelivery(String id) {
        mDatabase.child("deliveries").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Delivery delivery = dataSnapshot.getValue(Delivery.class);
                if (delivery != null) {
                    findDriver(delivery.getDriverId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Utils.showToastMessage(mActivity, "Can`t find delivery");
            }
        });
    }

    private void findDriver(String id) {
        mDatabase.child("drivers").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Driver driver = dataSnapshot.getValue(Driver.class);
                if (driver != null) {
                    mView.setDeliveryPoint(driver.getLatitude(), driver.getLongitude());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}