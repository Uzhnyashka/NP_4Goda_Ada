package com.example.bobyk.np.views.authorization.signUp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventAuthChangeFragment;
import com.example.bobyk.np.presenters.authorization.emailVerification.EmailVerificationPresenter;
import com.example.bobyk.np.presenters.authorization.signUp.SignUpPresenter;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.authorization.emailVerification.EmailVerificationFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 4/17/17.
 */

public class SignUpFragment extends Fragment implements SignUpView {

    private String TAG = getClass().getSimpleName();

    @Bind(R.id.et_email)
    EditText mEmailEditText;
    @Bind(R.id.et_password)
    EditText mPasswordEditText;
    @Bind(R.id.et_confirm_password)
    EditText mConfirmPasswordEditText;

    private SignUpPresenter mPresenter;

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SignUpPresenter(getActivity(), this);
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

    @OnClick(R.id.btn_sign_up)
    public void onNextClick() {
        if (mPasswordEditText.getText().toString().equals(mConfirmPasswordEditText.getText().toString())) {
            mPresenter.signUp(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        } else {
            Utils.showToastMessage(getActivity(), "Password and confirm password is different");
        }
    }

    @OnClick(R.id.btn_sign_in_member)
    public void onSignInMemberClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onSuccessSignUp(FirebaseUser user) {
        mPresenter.sendEmailVerification(user);
    }

    @Override
    public void onFailSignUp(String message) {
        Utils.showToastMessage(getActivity(), message);
    }

    @Override
    public void onSuccessSendEmailVerification(String message) {
        EventBus.getDefault().post(new EventAuthChangeFragment(EmailVerificationFragment.newInstance(), true));
    }

    @Override
    public void onFailSendEmailVerification(String message) {
        Utils.showToastMessage(getActivity(), message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        FirebaseAuth.getInstance().getCurrentUser().delete();
    }
}
