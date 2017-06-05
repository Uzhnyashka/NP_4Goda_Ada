package com.example.bobyk.np.views.authorization.additionalData;

import java.io.File;

/**
 * Created by bobyk on 6/4/17.
 */

public interface AdditionalDataView {
    void onSuccessSignUp();
    void onFailedSignUp(String message);
    void onSuccessLoadPhotoToDatabase();
    void onFailedLoadPhotoToDatabase();
    void onSuccessLoadPhotoFromDevice();
    void onFailedLoadPhotoFromDevice();
    void setProfileImage(File file);
    void error();
}
