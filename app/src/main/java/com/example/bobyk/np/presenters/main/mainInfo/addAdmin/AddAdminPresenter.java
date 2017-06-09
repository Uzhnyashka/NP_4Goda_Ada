package com.example.bobyk.np.presenters.main.mainInfo.addAdmin;

import android.app.Activity;

import com.example.bobyk.np.views.main.mainInfo.addAdmin.AddAdminView;

/**
 * Created by bobyk on 6/9/17.
 */

public class AddAdminPresenter implements IAddAdminPresenter {

    private Activity mActivity;
    private AddAdminView mView;

    public AddAdminPresenter(Activity activity, AddAdminView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {

    }
}
