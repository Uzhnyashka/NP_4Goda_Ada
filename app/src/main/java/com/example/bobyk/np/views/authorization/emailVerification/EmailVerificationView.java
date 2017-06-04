package com.example.bobyk.np.views.authorization.emailVerification;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by bobyk on 6/4/17.
 */

public interface EmailVerificationView {
    void onSuccessVerified(FirebaseUser user);
    void onFailVerified(String message);
    void onSuccessSendVerification(String message);
    void onFailSendVerification(String message);
}
