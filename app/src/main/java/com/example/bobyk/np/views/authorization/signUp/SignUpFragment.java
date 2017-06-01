package com.example.bobyk.np.views.authorization.signUp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 4/17/17.
 */

public class SignUpFragment extends Fragment implements SignUpView {

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, null);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {

    }

    @OnClick(R.id.btn_sign_in_member)
    public void onSignInMemberClick() {
        getActivity().onBackPressed();
    }
}
