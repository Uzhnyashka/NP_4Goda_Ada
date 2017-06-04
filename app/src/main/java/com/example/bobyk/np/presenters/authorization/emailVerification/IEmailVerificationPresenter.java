package com.example.bobyk.np.presenters.authorization.emailVerification;


import com.google.firebase.auth.FirebaseUser;

/**
 * Created by bobyk on 6/4/17.
 */

public interface IEmailVerificationPresenter {
    void checkEmailVerification();
    void resendEmailVerification();
}
