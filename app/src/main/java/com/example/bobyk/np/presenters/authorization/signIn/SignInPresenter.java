package com.example.bobyk.np.presenters.authorization.signIn;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.bobyk.np.models.authorization.Admin;
import com.example.bobyk.np.models.authorization.BaseAuthModel;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.views.authorization.signIn.SignInView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by bobyk on 6/1/17.
 */

public class SignInPresenter implements ISignInPresenter {

    private SignInView mView;
    private FirebaseAuth mAuth;
    private Activity mActivity;
    private DatabaseReference mDatabase;

    public SignInPresenter(Activity activity, SignInView view) {
        mView = view;
        mActivity = activity;
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void checkRoleWithEmail(final String email, String role) {
        mDatabase.child(getChildName(role)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean ok = false;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    BaseAuthModel baseAuthModel = d.getValue(BaseAuthModel.class);
                    if (baseAuthModel.getEmail().equals(email)) {
                        ok = true;
                    }

//                    if (mRole.equals("admins")) {
//                        Admin admin = d.getValue(Admin.class);
//                        if (admin.getEmail().equals(email)) {
//                            ok = true;
//                        }
//                    }
//                    if (mRole.equals("drivers")) {
//                        Driver driver = d.getValue(Driver.class);
//                        String email1 = driver.getEmail();
//                        System.out.println("WWW " + email1);
//                        if (driver.getEmail().equals(email)) {
//                            ok = true;
//                        }
//                    }
//                    if (mRole.equals("users")) {
//                        User user = d.getValue(User.class);
//                        if (user.getEmail().equals(email)) {
//                            ok = true;
//                        }
//                    }
                }
                if (ok) {
                    mView.roleConfirmed();
                } else {
                    mView.roleFailed();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mView.roleFailed();
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mView.onSuccessSingIn();
                        } else {
                            mView.onFailSignIn(task.getException().getMessage());
                        }
                    }
                });
    }

    private String getChildName(String role) {
        switch (role) {
            case "User":
                return "users";
            case "Administrator":
                return "admins";
            case "Driver":
                return "drivers";
        }
        return "";
    }
}
