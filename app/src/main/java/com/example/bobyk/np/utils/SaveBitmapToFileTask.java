package com.example.bobyk.np.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.bobyk.np.listeners.FileSavedListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bobyk on 6/5/17.
 */

public class SaveBitmapToFileTask extends AsyncTask<Bitmap, Void, Boolean> {

    private final FileSavedListener mFileSavedListener;
    private final String mResultFileName;
    private final boolean mRecycle;
    private String mErrorMessage;

    public SaveBitmapToFileTask(boolean recycle, String fileName, FileSavedListener listener) {
        mFileSavedListener = listener;
        mResultFileName = fileName;
        mRecycle = recycle;
    }

    @Override
    protected Boolean doInBackground(Bitmap... params) {
        return saveImageToFile(params[0]);
    }

    private Boolean saveImageToFile(Bitmap bm) {
        // write the bytes in file
        if (bm == null || bm.isRecycled()) {
            mErrorMessage = "Bitmap null or recycled";
            return false;
        }

        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(mResultFileName);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

            outStream.flush();
            outStream.close();
            if (mRecycle)
                bm.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mErrorMessage = e.getMessage();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            mErrorMessage = e.getMessage();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (isSuccess) {
            mFileSavedListener.onSuccess(mResultFileName);
        } else {
            mFileSavedListener.onFail(mErrorMessage);
        }
    }

}
