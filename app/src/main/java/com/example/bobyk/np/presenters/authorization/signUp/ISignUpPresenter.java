package com.example.bobyk.np.presenters.authorization.signUp;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by bobyk on 6/1/17.
 */

public interface ISignUpPresenter {
    void signUp(String email, String password);
    void sendEmailVerification(FirebaseUser user);
}
