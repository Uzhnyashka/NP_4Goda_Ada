package com.example.bobyk.np.views.main.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.presenters.main.profile.ProfileHostPresenter;

/**
 * Created by bobyk on 6/6/17.
 */

public class ProfileHostFragment extends Fragment implements ProfileHostView{

    private ProfileHostPresenter mPresenter;

    public static ProfileHostFragment newInstance() {
        Bundle args = new Bundle();
        ProfileHostFragment fragment = new ProfileHostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ProfileHostPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
