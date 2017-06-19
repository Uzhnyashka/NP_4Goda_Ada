package com.example.bobyk.np.views.authorization.signIn;

import com.facebook.AccessToken;

/**
 * Created by bobyk on 3/25/17.
 */

public interface SignInView {
    void onSuccessSingIn();
    void onFailSignIn(String message);
    void roleConfirmed();
    void roleFailed();
    void onSuccessLoginFB(AccessToken token);
    void setFacebookEmail(String email);
    void userExist();
    void userIsNotExist();
}
