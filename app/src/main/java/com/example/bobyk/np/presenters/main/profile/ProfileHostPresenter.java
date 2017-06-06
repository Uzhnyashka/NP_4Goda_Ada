package com.example.bobyk.np.presenters.main.profile;

import android.app.Activity;

import com.example.bobyk.np.views.main.profile.ProfileHostView;

/**
 * Created by bobyk on 6/6/17.
 */

public class ProfileHostPresenter implements IProfileHostPresenter {

    private Activity mActivity;
    private ProfileHostView mView;

    public ProfileHostPresenter(Activity activity, ProfileHostView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {

    }
}
