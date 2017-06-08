package com.example.bobyk.np.views.main.profile.profilePage;

import com.example.bobyk.np.models.authorization.User;

/**
 * Created by bobyk on 6/6/17.
 */

public interface ProfilePageView {
    void setFullName(String name);
    void setPhoneNumber(String phoneNumber);
    void setPhoto(String photoUrl);
    void setUser(User user);
}
