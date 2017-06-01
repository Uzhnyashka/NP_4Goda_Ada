package com.example.bobyk.np.views.authorization.startScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventAuthChangeFragment;
import com.example.bobyk.np.utils.Role;
import com.example.bobyk.np.views.authorization.AuthActivity;
import com.example.bobyk.np.views.authorization.signIn.SignInFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 4/17/17.
 */

public class StartScreenFragment extends Fragment {

    @Bind(R.id.rl_user)
    RelativeLayout userRelativeLayout;
    @Bind(R.id.rl_admin)
    RelativeLayout adminRelativeLayout;
    @Bind(R.id.rl_driver)
    RelativeLayout driverRelativeLayout;

    public static StartScreenFragment newInstance() {
        Bundle args = new Bundle();
        StartScreenFragment fragment = new StartScreenFragment();
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
        View view = inflater.inflate(R.layout.fragment_start_screen, null);

        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.rl_user)
    public void onUserClick() {
        EventBus.getDefault().post(new EventAuthChangeFragment(SignInFragment.newInstance(Role.USER), true));
    }

    @OnClick(R.id.rl_admin)
    public void onAdminClick() {
        EventBus.getDefault().post(new EventAuthChangeFragment(SignInFragment.newInstance(Role.ADMIN), true));
    }

    @OnClick(R.id.rl_driver)
    public void onDriverClick() {
        EventBus.getDefault().post(new EventAuthChangeFragment(SignInFragment.newInstance(Role.DRIVER), true));
    }
}
