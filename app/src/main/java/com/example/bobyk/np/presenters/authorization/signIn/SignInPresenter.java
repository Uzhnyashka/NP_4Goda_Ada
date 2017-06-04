package com.example.bobyk.np.presenters.authorization.signIn;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.bobyk.np.views.authorization.signIn.SignInView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by bobyk on 6/1/17.
 */

public class SignInPresenter implements ISignInPresenter {

    private SignInView mView;
    private FirebaseAuth mAuth;
    private Activity mActivity;

    public SignInPresenter(Activity activity, SignInView view) {
        mView = view;
        mActivity = activity;
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
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
}
