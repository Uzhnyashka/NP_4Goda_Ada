package com.example.bobyk.np.presenters.main.mainInfo.addDriver;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.models.main.Point;
import com.example.bobyk.np.views.main.mainInfo.addDriver.AddDriverView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by bobyk on 6/9/17.
 */

public class AddDriverPresenter implements IAddDriverPresenter {

    private Activity mActivity;
    private AddDriverView mView;
    private FirebaseAuth mAuthAdditional;
    private DatabaseReference mDatabase;

    public AddDriverPresenter(Activity activity, AddDriverView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://tracking-e7f58.firebaseio.com/")
                .setApiKey("AIzaSyAReKuIx_QlI9glkNuS5-2qZDhqjK4Cing")
                .setApplicationId("tracking-e7f58").build();


        FirebaseApp myApp = null;
        try {
            myApp = FirebaseApp.initializeApp(getApplicationContext(),firebaseOptions,
                    "NP");
        } catch (Exception e) {
            myApp = FirebaseApp.getInstance("NP");
            e.printStackTrace();
        }

        mAuthAdditional = FirebaseAuth.getInstance(myApp);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void registerDriver(final Driver driver, String password) {
        mAuthAdditional.createUserWithEmailAndPassword(driver.getEmail(), password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            addDriverAditionalData(driver);
                            mAuthAdditional.signOut();
                        } else {
                            mView.onFailedRegisterDriver(task.getException().getMessage());
                            mAuthAdditional.signOut();
                        }
                    }
                });
    }

    private void addDriverAditionalData(Driver driver) {
//        driver.setLatitude(0d);
//        driver.setLongitude(0d);
        if (driver.getPoints() == null) {
            List<Point> points = new ArrayList<>();
            points.add(new Point(0d, 0d));
            driver.setPoints(points);
        } else {
            driver.getPoints().add(new Point(0d, 0d));
        }
        mDatabase.child("drivers").child(mAuthAdditional.getCurrentUser().getUid()).setValue(driver)
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mView.onSuccessRegisterDriver();
                        } else {
                            mView.onFailedRegisterDriver(task.getException().getMessage());
                        }
                    }
                });
    }
}
