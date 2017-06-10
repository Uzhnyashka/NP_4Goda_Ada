package com.example.bobyk.np.presenters.main.driverDeliveries;

import android.app.Activity;

import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.views.main.driverDeliveries.DriverDeliveriesView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class DriverDeliveriesPresenter implements IDriverDeliveriesPresenter {

    private Activity mActivity;
    private DriverDeliveriesView mView;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    public DriverDeliveriesPresenter(Activity activity, DriverDeliveriesView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void loadDriverDeliveries() {
        mDatabase.child("deliveries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Delivery> deliveries = new ArrayList<Delivery>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Delivery delivery = d.getValue(Delivery.class);
                    if (delivery.getDriverId().equals(mUser.getUid())) {
                        deliveries.add(delivery);
                    }
                }
                mView.onSuccessLoadDeliveries(deliveries);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }
}
