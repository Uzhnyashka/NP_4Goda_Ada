package com.example.bobyk.np.views.main.users;

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
import com.example.bobyk.np.views.main.profile.profilePage.ProfilePageFragment;
import com.example.bobyk.np.views.main.users.chooseUserType.ChooseUserTypeFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/10/17.
 */

public class UsersHostFragment extends Fragment {

    private ChooseUserTypeFragment mChooseUserTypeFragment;

    public static UsersHostFragment newInstance() {
        Bundle args = new Bundle();
        UsersHostFragment fragment = new UsersHostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_host, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mChooseUserTypeFragment == null) {
            mChooseUserTypeFragment = ChooseUserTypeFragment.newInstance();
        }
        init();
    }

    private void init() {
        changeFragment(mChooseUserTypeFragment, false);
    }

    @Subscribe
    public void onEvent(EventMainChangeFragment event) {
        if (event.getNumberFragment() == 4) changeFragment(event.getFragment(), event.isAddToBackStack());
    }

    private void changeFragment(Fragment fragment, boolean fragmentToBackStack) {
        if (!fragmentToBackStack) {
            for (int i = 0; i < getChildFragmentManager().getBackStackEntryCount(); i++) {
                getChildFragmentManager().popBackStack();
            }
        }
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.fl_users_container, fragment);
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
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStack();
            return true;
        } else {
            return false;
        }
    }
}
