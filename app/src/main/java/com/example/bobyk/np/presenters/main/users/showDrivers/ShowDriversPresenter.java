package com.example.bobyk.np.presenters.main.users.showDrivers;

import android.app.Activity;

import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.views.main.users.showDrivers.ShowDriversView;
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

public class ShowDriversPresenter implements IShowDriversPresenter {

    private Activity mActivity;
    private ShowDriversView mView;
    private DatabaseReference mDatabase;

    public ShowDriversPresenter(Activity activity, ShowDriversView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void loadDrivers() {
        mDatabase.child("drivers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Driver> drivers = new ArrayList<Driver>();
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Driver driver = d.getValue(Driver.class);
                    drivers.add(driver);
                }
                mView.onSuccessLoadDrivers(drivers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }
}
