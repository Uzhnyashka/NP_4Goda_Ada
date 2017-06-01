package com.example.bobyk.np.event;

import android.support.v4.app.Fragment;

/**
 * Created by bobyk on 3/25/17.
 */

public
class EventAuthChangeFragment {
    private Fragment mFragment;
    private boolean mAddToBackStack;

    public EventAuthChangeFragment(Fragment fragment, boolean addToBackStack) {
        mFragment = fragment;
        mAddToBackStack = addToBackStack;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public boolean isAddToBackStack() {
        return mAddToBackStack;
    }
}

