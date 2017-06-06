package com.example.bobyk.np.views.main.profile.profilePage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;

import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/6/17.
 */

public class ProfilePageFragment extends Fragment implements ProfilePageView {

    public static ProfilePageFragment newInstance() {
        Bundle args = new Bundle();
        ProfilePageFragment fragment = new ProfilePageFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile_page, null);

        ButterKnife.bind(this, view);

        return view;
    }
}
