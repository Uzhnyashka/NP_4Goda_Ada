package com.example.bobyk.np.presenters.main.mainInfo.addAdmin;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.bobyk.np.models.authorization.Admin;
import com.example.bobyk.np.views.main.mainInfo.addAdmin.AddAdminView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by bobyk on 6/9/17.
 */

public class AddAdminPresenter implements IAddAdminPresenter {

    private Activity mActivity;
    private AddAdminView mView;
    private FirebaseAuth mAuthAdditional;
    private DatabaseReference mDatabase;

    public AddAdminPresenter(Activity activity, AddAdminView view) {
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
    public void registerAdmin(final Admin admin, String password) {
        mAuthAdditional.createUserWithEmailAndPassword(admin.getEmail(), password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            addDriverAditionalData(admin);
                            mAuthAdditional.signOut();
                        } else {
                            mView.onFailedRegisterAdmin(task.getException().getMessage());
                            mAuthAdditional.signOut();
                        }
                    }
                });
    }

    private void addDriverAditionalData(Admin admin) {
        mDatabase.child("admins").child(mAuthAdditional.getCurrentUser().getUid()).setValue(admin)
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mView.onSuccessRegisterAdmin();
                        } else {
                            mView.onFailedRegisterAdmin(task.getException().getMessage());
                        }
                    }
                });
    }
}
