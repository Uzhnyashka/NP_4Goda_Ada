package com.example.bobyk.np.presenters.authorization.signIn;

/**
 * Created by bobyk on 6/1/17.
 */

public interface ISignInPresenter {

    void checkRoleWithEmail(String email, String role);
    void signIn(String email, String password);
}
