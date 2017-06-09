package com.example.bobyk.np.views.main.profile.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.views.authorization.AuthActivity;
import com.example.bobyk.np.views.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/6/17.
 */

public class SettingsFragment extends Fragment implements SettingsView {

    @Bind(R.id.tv_first_name)
    TextView mFirstNameTextView;
    @Bind(R.id.tv_surname)
    TextView mSurnameTextView;
    @Bind(R.id.tv_middle_name)
    TextView mMiddleNameTextView;
    @Bind(R.id.tv_email)
    TextView mEmailTextView;
    @Bind(R.id.tv_phone_number)
    TextView mPhoneNumberTextView;

    private User mUser;

    public static SettingsFragment newInstance(User user) {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        fragment.setUser(user);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        mFirstNameTextView.setText(mUser.getFirstName());
        mSurnameTextView.setText(mUser.getSurname());
        mMiddleNameTextView.setText(mUser.getMiddleName());
        mEmailTextView.setText(mUser.getEmail());
        mPhoneNumberTextView.setText(mUser.getPhoneNumber());
    }

    private void initUserData() {

    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }

    private void setUser(User user) {
        mUser = user;
        initUserData();
    }

    @OnClick(R.id.btn_logout)
    public void onLogoutClick() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext().getApplicationContext(), AuthActivity.class));
        getActivity().finish();
    }
}
