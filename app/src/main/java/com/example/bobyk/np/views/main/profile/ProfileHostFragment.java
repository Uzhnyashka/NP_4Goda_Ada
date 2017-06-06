package com.example.bobyk.np.views.main.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.presenters.main.profile.ProfileHostPresenter;
import com.example.bobyk.np.views.main.profile.profilePage.ProfilePageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/6/17.
 */

public class ProfileHostFragment extends Fragment implements ProfileHostView{

    private String TAG = getClass().getSimpleName();

    private ProfileHostPresenter mPresenter;
    private ProfilePageFragment mProfilePageFragment;

    public static ProfileHostFragment newInstance() {
        Bundle args = new Bundle();
        ProfileHostFragment fragment = new ProfileHostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mPresenter = new ProfileHostPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_host, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mProfilePageFragment == null) {
            Log.d(TAG, "init: profile");
            mProfilePageFragment = ProfilePageFragment.newInstance();
        }
        init();
    }

    private void init() {
        changeFragment(mProfilePageFragment, false);
    }

    @Subscribe
    public void onEvent(EventMainChangeFragment event) {
        if (event.getNumberFragment() == 3) changeFragment(event.getFragment(), event.isAddToBackStack());
    }

    private void changeFragment(Fragment fragment, boolean fragmentToBackStack) {
        if (!fragmentToBackStack) {
            for (int i = 0; i < getChildFragmentManager().getBackStackEntryCount(); i++) {
                getChildFragmentManager().popBackStack();
            }
        }
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.fl_profile_container, fragment);
        if (fragmentToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public boolean onBackPressed() {
        return false;
    }
}
