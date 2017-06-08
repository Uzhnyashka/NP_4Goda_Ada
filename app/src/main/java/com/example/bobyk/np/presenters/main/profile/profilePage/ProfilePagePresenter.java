package com.example.bobyk.np.presenters.main.profile.profilePage;

import android.app.Activity;

import com.example.bobyk.np.R;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.views.main.profile.profilePage.ProfilePageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Created by bobyk on 6/7/17.
 */

public class ProfilePagePresenter implements IProfilePagePresenter {

    private Activity mActivity;
    private ProfilePageView mView;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private User user;

    public ProfilePagePresenter(Activity activity, ProfilePageView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void initUserData() {
        if (mUser != null) {
            mDatabase.child("users").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        mView.setFullName(user.getSurname() + " " + user.getFirstName() + " " + user.getMiddleName());
                        mView.setPhoneNumber(user.getPhoneNumber());
                        mView.setPhoto(user.getPhotoUrl());
                        mView.setUser(user);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
