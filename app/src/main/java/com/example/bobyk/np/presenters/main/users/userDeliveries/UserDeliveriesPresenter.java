package com.example.bobyk.np.presenters.main.users.userDeliveries;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.views.main.users.userDeliveries.UserDeliveriesView;
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

public class UserDeliveriesPresenter implements IUserDeliveriesPresenter {

    private Activity mActivity;
    private UserDeliveriesView mView;
    private DatabaseReference mDatabase;

    public UserDeliveriesPresenter(Activity activity, UserDeliveriesView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void loadDeliveriesForUser(final String email) {
        mDatabase.child("deliveries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Delivery> deliveryList = new ArrayList<Delivery>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Delivery delivery = d.getValue(Delivery.class);
                    if (delivery.getRecipientEmail().equals(email) ||
                            delivery.getSenderEmail().equals(email)) {
                        deliveryList.add(delivery);
                    }
                }
                mView.successLoadUserDeliveries(deliveryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }
}
