package com.example.bobyk.np.views.main.mainInfo;

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
import com.example.bobyk.np.views.main.mainInfo.info.InfoScreenFragment;
import com.example.bobyk.np.views.main.profile.profilePage.ProfilePageFragment;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/6/17.
 */

public class InfoHostFragment extends Fragment implements InfoHostView {

    private InfoScreenFragment mInfoScreenFragment;

    public static InfoHostFragment newInstance() {
        Bundle args = new Bundle();
        InfoHostFragment fragment = new InfoHostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_host, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mInfoScreenFragment == null) {
            mInfoScreenFragment = InfoScreenFragment.newInstance();
        }
        init();
    }

    private void init() {
        changeFragment(mInfoScreenFragment, false);
    }

    @Subscribe
    public void onEvent(EventMainChangeFragment event) {
        if (event.getNumberFragment() == 2) changeFragment(event.getFragment(), event.isAddToBackStack());
    }

    private void changeFragment(Fragment fragment, boolean fragmentToBackStack) {
        if (!fragmentToBackStack) {
            for (int i = 0; i < getChildFragmentManager().getBackStackEntryCount(); i++) {
                getChildFragmentManager().popBackStack();
            }
        }
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.fl_info_container, fragment);
        if (fragmentToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    public boolean onBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStack();
            return true;
        } else {
            return false;
        }
    }
}
