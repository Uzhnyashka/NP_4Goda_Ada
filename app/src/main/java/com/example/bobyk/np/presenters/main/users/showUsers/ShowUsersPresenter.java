package com.example.bobyk.np.presenters.main.users.showUsers;

import android.app.Activity;

import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.views.main.users.showUsers.ShowUsersView;
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

public class ShowUsersPresenter implements IShowUsersPresenter {

    private Activity mActivity;
    private ShowUsersView mView;
    private DatabaseReference mDatabase;

    public ShowUsersPresenter(Activity activity, ShowUsersView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void loadUsers() {
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    User user = d.getValue(User.class);
                    users.add(user);
                }
                mView.onSuccessLoadUsers(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.onError();
            }
        });
    }
}
