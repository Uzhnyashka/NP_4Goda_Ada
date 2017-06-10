package com.example.bobyk.np.views.main.users.showUsers;

import com.example.bobyk.np.models.authorization.User;

import java.util.List;

/**
 * Created by bobyk on 6/10/17.
 */

public interface ShowUsersView {
    void onSuccessLoadUsers(List<User> users);
    void onError();
}
