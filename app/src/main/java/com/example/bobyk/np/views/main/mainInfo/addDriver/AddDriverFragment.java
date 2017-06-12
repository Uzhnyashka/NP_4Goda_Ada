package com.example.bobyk.np.views.main.mainInfo.addDriver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bobyk.np.R;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.presenters.main.mainInfo.addDriver.AddDriverPresenter;
import com.example.bobyk.np.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/9/17.
 */

public class AddDriverFragment extends Fragment implements AddDriverView {

    @Bind(R.id.et_firstname)
    EditText mFirstNameEditText;
    @Bind(R.id.et_surname)
    EditText mSurnameEditText;
    @Bind(R.id.et_middlename)
    EditText mMiddleNameEditText;
    @Bind(R.id.et_email)
    EditText mEmailEditText;
    @Bind(R.id.et_password)
    EditText mPasswordEditText;
    @Bind(R.id.et_confirm_password)
    EditText mConfirmPasswordEditText;

    private AddDriverPresenter mPresenter;
    private Driver mDriver;

    public static AddDriverFragment newInstance() {
        Bundle args = new Bundle();
        AddDriverFragment fragment = new AddDriverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddDriverPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_driver, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        if (mPasswordEditText.getText().toString().equals(mConfirmPasswordEditText.getText().toString())) {
            initDriverData();
            mPresenter.registerDriver(mDriver, mPasswordEditText.getText().toString());
        } else {
            Utils.showToastMessage(getActivity(), "Confirm password is wrong");
        }
    }

    @Override
    public void onSuccessRegisterDriver() {
        Utils.showToastMessage(getActivity(), "Success register driver");
        getActivity().onBackPressed();
    }

    @Override
    public void onFailedRegisterDriver(String message) {
        Utils.showToastMessage(getActivity(), message);
    }

    private void initDriverData() {
        mDriver = new Driver(
                mFirstNameEditText.getText().toString(),
                mSurnameEditText.getText().toString(),
                mMiddleNameEditText.getText().toString(),
                mEmailEditText.getText().toString(),
                 "driver", "+322" , ""
        );
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }
}
