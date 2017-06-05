package com.example.bobyk.np.presenters.authorization.emailVerification;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.bobyk.np.views.authorization.emailVerification.EmailVerificationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by bobyk on 6/4/17.
 */

public class EmailVerificationPresenter implements IEmailVerificationPresenter {

    private Activity mActivity;
    private EmailVerificationView mView;
    private FirebaseUser mUser;
    private String e;
    private String p;

    public EmailVerificationPresenter(Activity activity, EmailVerificationView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        e = mUser.getEmail();
    }

    @Override
    public void checkEmailVerification() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mUser.isEmailVerified()) {
            mView.onSuccessVerified(mUser);
        } else {
            mView.onFailVerified("Email is not verified");
        }

        FirebaseAuth.getInstance().signOut();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(e, "123456")
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUser = FirebaseAuth.getInstance().getCurrentUser();

                            if (mUser.isEmailVerified()) {
                                mView.onSuccessVerified(mUser);
                            } else {
                                mView.onFailVerified("Email is not verified");
                            }
                        }
                    }
                });

    }

    @Override
    public void resendEmailVerification() {
        mUser.sendEmailVerification()
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mView.onSuccessSendVerification("Success send verification to email");
                        } else {
                            mView.onFailSendVerification(task.getException().getMessage());
                        }
                    }
                });
    }
}
