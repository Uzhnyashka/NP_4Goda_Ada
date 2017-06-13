package com.example.bobyk.np.presenters.main.mainInfo.addDelivery.chooseCity;

import android.app.Activity;

import com.example.bobyk.np.models.main.City;
import com.example.bobyk.np.views.main.mainInfo.addDelivery.chooseCity.ChooseCityView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 6/13/17.
 */

public class ChooseCityPresenter implements IChooseCityPresenter {

    private Activity mActivity;
    private ChooseCityView mView;
    private DatabaseReference mDatabase;

    public ChooseCityPresenter(Activity activity, ChooseCityView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void loadAllCities() {
        mDatabase.child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<City> cityList = new ArrayList<City>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    City city = d.getValue(City.class);
                    city.setName(d.getKey());
                    cityList.add(city);
                }
                mView.successLoadCities(cityList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }
}
