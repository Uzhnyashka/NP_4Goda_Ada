package com.example.bobyk.np.listeners;

/**
 * Created by bobyk on 6/5/17.
 */

public interface FileSavedListener {
    void onSuccess(String fileName);
    void onFail(String error);
}
