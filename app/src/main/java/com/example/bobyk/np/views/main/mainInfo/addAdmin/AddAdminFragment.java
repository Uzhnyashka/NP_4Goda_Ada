package com.example.bobyk.np.views.main.mainInfo.addAdmin;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.example.bobyk.np.R;
import com.example.bobyk.np.models.authorization.Admin;
import com.example.bobyk.np.presenters.main.mainInfo.addAdmin.AddAdminPresenter;
import com.example.bobyk.np.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/9/17.
 */

public class AddAdminFragment extends Fragment implements AddAdminView {

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

    private AddAdminPresenter mPresenter;
    private Admin mAdmin;
    private Dialog dialog;

    public static AddAdminFragment newInstance() {
        Bundle args = new Bundle();
        AddAdminFragment fragment = new AddAdminFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddAdminPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_admin, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        if (mPasswordEditText.getText().toString().equals(mConfirmPasswordEditText.getText().toString())) {
            initDriverData();
            showProgressDialog();
            mPresenter.registerAdmin(mAdmin, mPasswordEditText.getText().toString());
        } else {
            Utils.showToastMessage(getActivity(), "Confirm password is wrong");
        }
    }

    @Override
    public void onSuccessRegisterAdmin() {
        hideProgressDialog();
        Utils.showToastMessage(getActivity(), "Success register ");
        getActivity().onBackPressed();
    }

    @Override
    public void onFailedRegisterAdmin(String message) {
        hideProgressDialog();
        Utils.showToastMessage(getActivity(), message);
    }

    private void initDriverData() {
        mAdmin = new Admin(
                mFirstNameEditText.getText().toString(),
                mSurnameEditText.getText().toString(),
                mMiddleNameEditText.getText().toString(),
                mEmailEditText.getText().toString(),
                "admin", "+228", ""
        );
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }

    private void showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress_bar, null);
        builder.setView(dialogView);

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        Display display = getActivity().getWindowManager().getDefaultDisplay();

        Window window = dialog.getWindow();
        window.setLayout(display.getWidth() - 100, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void hideProgressDialog() {
        dialog.cancel();
    }
}
