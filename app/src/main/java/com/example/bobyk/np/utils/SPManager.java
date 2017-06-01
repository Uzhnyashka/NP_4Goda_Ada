package com.example.bobyk.np.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.bobyk.np.views.authorization.AuthActivity;

/**
 * Created by bobyk on 3/25/17.
 */

public abstract class SPManager {

    private static final String TAG = "SPManager";

    public static void storeUserLoginData(final Context context, final String key, final String value) {
        if (context == null) return;
        final SharedPreferences prefs = context.getSharedPreferences(
                AuthActivity.class.getSimpleName(), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String loadUserLoginData(final Context context, final String key) {
        if (context == null) return "";
        final SharedPreferences prefs = context.getSharedPreferences(
                AuthActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String value = prefs.getString(key, "");

        if (value.isEmpty()) {
            Log.d(TAG, "loadUserLoginData: Param not found.");
            return "";
        }

        return value;
    }

}
