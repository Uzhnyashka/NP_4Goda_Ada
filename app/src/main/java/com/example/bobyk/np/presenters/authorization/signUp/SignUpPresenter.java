package com.example.bobyk.np.presenters.authorization.signUp;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.bobyk.np.views.authorization.signUp.SignUpView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by bobyk on 6/1/17.
 */

public class SignUpPresenter implements ISignUpPresenter {

    private SignUpView mView;
    private Activity mActivity;
    private FirebaseAuth mAuth;

    public SignUpPresenter(Activity activity, SignUpView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mView.onSuccessSignUp(task.getResult().getUser());
                        } else {
                            mView.onFailSignUp(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mView.onSuccessSendEmailVerification("Success send verification to email");
                        } else {
                            mView.onFailSendEmailVerification(task.getException().getMessage());
                        }
                    }
                });
    }
}
