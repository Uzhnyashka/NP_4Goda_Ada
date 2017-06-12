package com.example.bobyk.np.presenters.main.mainInfo.deliveries;

import android.app.Activity;

import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.utils.SPManager;
import com.example.bobyk.np.views.main.mainInfo.deliveries.DeliveriesView;
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

public class DeliveriesPresenter implements IDeliveriesPresenter {

    private Activity mActivity;
    private DeliveriesView mView;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    private String mRole;

    public DeliveriesPresenter(Activity activity, DeliveriesView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRole = SPManager.loadUserLoginData(mActivity.getApplicationContext(), Constants.ROLE);
    }


    @Override
    public void loadDeliveries() {
        mDatabase.child("deliveries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Delivery> deliveryList = new ArrayList<Delivery>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Delivery delivery = d.getValue(Delivery.class);
                    if (mRole.equals("User")) {
                        if (delivery.getSenderEmail().equals(mUser.getEmail()) ||
                                delivery.getRecipientEmail().equals(mUser.getEmail())) {
                            deliveryList.add(delivery);
                        }
                    }
                    if (mRole.equals("Administrator")) {
                        deliveryList.add(delivery);
                    }
                }
                mView.onSuccessLoadDeliveries(deliveryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }
}
