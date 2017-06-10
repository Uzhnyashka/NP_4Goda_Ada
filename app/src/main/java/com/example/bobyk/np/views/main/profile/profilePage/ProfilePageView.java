package com.example.bobyk.np.views.main.profile.profilePage;

import com.example.bobyk.np.models.authorization.BaseAuthModel;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.authorization.Admin;
import com.example.bobyk.np.models.authorization.Driver;

import java.io.File;

/**
 * Created by bobyk on 6/6/17.
 */

public interface ProfilePageView {
    void setFullName(String name);
    void setPhoneNumber(String phoneNumber);
    void setPhoto(String photoUrl);
    void setUserData(BaseAuthModel user);
    void setProfileImage(File file);
    void onSuccessLoadPhotoToDatabase();
    void onFailedLoadPhotoToDatabase();
    void error();
}
