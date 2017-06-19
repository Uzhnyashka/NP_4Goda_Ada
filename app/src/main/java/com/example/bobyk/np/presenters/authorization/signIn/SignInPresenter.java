package com.example.bobyk.np.presenters.authorization.signIn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.models.authorization.Admin;
import com.example.bobyk.np.models.authorization.BaseAuthModel;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.facebook.FacebookModel;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.authorization.signIn.SignInView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobyk on 6/1/17.
 */

public class SignInPresenter implements ISignInPresenter, FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {

    private SignInView mView;
    private FirebaseAuth mAuth;
    private Activity mActivity;
    private DatabaseReference mDatabase;
    private Fragment mFragment;
    private CallbackManager callbackManager;
    private FacebookModel facebookModel = new FacebookModel();

    public SignInPresenter(Activity activity, SignInView view, Fragment fragment) {
        mView = view;
        mActivity = activity;
        mFragment = fragment;
        init();
    }

    private void init() {
        callbackManager = CallbackManager.Factory.create();
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
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                mView.onSuccessSingIn();
                            } else {
                                Utils.showToastMessage(mActivity, "Please verify your email");
                            }
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

    private List<String> getFacebookPermission() {
        List<String> permissions = new ArrayList<>();
        permissions.add(Constants.FACEBOOK_PERMISSION_EMAIL);
        return permissions;
    }

    public void loginUserWithFacebook() {
        System.out.println("WWW loginUserWithFacebook");
        LoginManager.getInstance().logInWithReadPermissions(mFragment, getFacebookPermission());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("WWW onActivityResult");
        facebookModel.getFacebookUser(AccessToken.getCurrentAccessToken(), this);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    @Override
    public void isRegistred(final String email) {
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean ok = false;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    User user = d.getValue(User.class);
                    if (user.getEmail().equals(email)) {
                        ok= true;
                        mView.userExist();
                    }
                }
                if (!ok) {
                    mView.userIsNotExist();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        String email = "";
        try {
            email = object.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mView.setFacebookEmail(email);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        System.out.println("WWW onSuccess");
        mView.onSuccessLoginFB(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
        System.out.println("WWW onCancel");
    }

    @Override
    public void onError(FacebookException error) {
        System.out.println("WWW onError");
    }
}
