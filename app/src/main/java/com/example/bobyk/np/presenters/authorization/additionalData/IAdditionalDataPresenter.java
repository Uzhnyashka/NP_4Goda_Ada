package com.example.bobyk.np.presenters.authorization.additionalData;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by bobyk on 6/4/17.
 */

public interface IAdditionalDataPresenter {
    void signUp(String firstname, String surname, String middlename);
    void uploadPhoto(Bitmap bitmap);
    void chooseProfileImage();
    void getLoadFile(int requestCode, int resultCode, Intent data);
}
