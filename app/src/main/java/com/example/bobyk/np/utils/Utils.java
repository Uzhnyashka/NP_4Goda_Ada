package com.example.bobyk.np.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bobyk on 3/25/17.
 */

public abstract class Utils {

    public static boolean isNetworkConnected(Activity context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            showToastMessage(context, "The internet connection appears to be offline");
        }
        return cm.getActiveNetworkInfo() != null;
    }

    public static void showToastMessage(Activity activity, String message) {
        if (activity != null) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
