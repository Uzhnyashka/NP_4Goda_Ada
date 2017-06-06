package com.example.bobyk.np.event;

import android.support.v4.app.Fragment;

/**
 * Created by bobyk on 6/7/17.
 */

public class EventMainChangeFragment {

    private Fragment mFragment;
    private boolean mAddToBackStack;
    private int numberFragment;

    public EventMainChangeFragment(Fragment fragment, boolean addToBackStack, int num) {
        mFragment = fragment;
        mAddToBackStack = addToBackStack;
        numberFragment = num;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public boolean isAddToBackStack() {
        return mAddToBackStack;
    }

    public int getNumberFragment() {
        return numberFragment;
    }
}
