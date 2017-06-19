package com.example.bobyk.np.presenters.authorization.signIn;

import android.content.Intent;

import com.facebook.CallbackManager;

/**
 * Created by bobyk on 6/1/17.
 */

public interface ISignInPresenter {

    void checkRoleWithEmail(String email, String role);
    void signIn(String email, String password);
    void loginUserWithFacebook();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    CallbackManager getCallbackManager();
    void isRegistred(String email);
}
