package com.example.bobyk.np.views.authorization.emailVerification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventAuthChangeFragment;
import com.example.bobyk.np.presenters.authorization.emailVerification.EmailVerificationPresenter;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.authorization.additionalData.AdditionalDataFragment;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/4/17.
 */

public class EmailVerificationFragment extends Fragment implements EmailVerificationView {

    private EmailVerificationPresenter mPresenter;

    public static EmailVerificationFragment newInstance() {
        Bundle args = new Bundle();
        EmailVerificationFragment fragment = new EmailVerificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new EmailVerificationPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_verification, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onSuccessVerified(FirebaseUser user) {
        EventBus.getDefault().post(new EventAuthChangeFragment(AdditionalDataFragment.newInstance(), true));
    }

    @Override
    public void onFailVerified(String message) {
        Utils.showToastMessage(getActivity(), message);
    }

    @Override
    public void onSuccessSendVerification(String message) {
        Utils.showToastMessage(getActivity(), message);
    }

    @Override
    public void onFailSendVerification(String message) {
        Utils.showToastMessage(getActivity(), message);
    }

    @OnClick(R.id.btn_confirmed_email)
    public void onConfirmedEmailClick() {
        mPresenter.checkEmailVerification();
    }

    @OnClick(R.id.btn_resend)
    public void onResendClick() {
        mPresenter.resendEmailVerification();
    }
}
