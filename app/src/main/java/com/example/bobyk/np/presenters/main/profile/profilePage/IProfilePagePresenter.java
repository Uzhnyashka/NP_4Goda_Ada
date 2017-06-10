package com.example.bobyk.np.presenters.main.profile.profilePage;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by bobyk on 6/7/17.
 */

public interface IProfilePagePresenter {
    void initData();
    void getLoadFile(int requestCode, int resultCode, Intent data);
    void uploadPhoto(Bitmap bitmap);
    void chooseProfileImage();
}
