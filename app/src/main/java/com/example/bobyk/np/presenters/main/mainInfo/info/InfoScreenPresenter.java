package com.example.bobyk.np.presenters.main.mainInfo.info;

import android.app.Activity;

import com.example.bobyk.np.views.main.mainInfo.info.InfoScreenView;

/**
 * Created by bobyk on 6/7/17.
 */

public class InfoScreenPresenter implements IInfoScreenPresenter {

    private Activity mActivity;
    private InfoScreenView mView;

    public InfoScreenPresenter(Activity activity, InfoScreenView view) {
        mActivity = activity;
        mView = view;
        init();
    }

    private void init() {

    }
}
