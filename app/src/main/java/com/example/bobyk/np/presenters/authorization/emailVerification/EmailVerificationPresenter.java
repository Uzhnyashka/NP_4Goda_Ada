package com.example.bobyk.np.presenters.authorization.emailVerification;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.bobyk.np.views.authorization.emailVerification.EmailVerificationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by bobyk on 6/4/17.
 */

public class EmailVerificationPresenter implements IEmailVerificationPresenter {

    private Activity mActivity;
    private EmailVerificationView mView;
    private FirebaseUser mUser;

    public EmailVerificationPresenter(Activity activity, EmailVerificationView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void checkEmailVerification() {
        if (mUser.isEmailVerified()) {
            mView.onSuccessVerified(mUser);
        } else {
            mView.onFailVerified("Email is not verified");
        }
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
