package com.example.bobyk.np.views.authorization.signUp;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by bobyk on 4/17/17.
 */

public interface SignUpView {
    void onSuccessSignUp(FirebaseUser user);
    void onFailSignUp(String message);
    void onSuccessSendEmailVerification(String message);
    void onFailSendEmailVerification(String message);
}
