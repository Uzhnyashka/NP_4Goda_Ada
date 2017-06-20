package com.example.bobyk.np.views.main.users.deliveryInfo;

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
import android.widget.Button;
import android.widget.TextView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.models.main.Point;
import com.example.bobyk.np.presenters.main.users.deliveryInfo.DeliveryInfoPresenter;
import com.example.bobyk.np.utils.SPManager;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.map.ShowRouteOnMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/11/17.
 */

public class DeliveryInfoFragment extends Fragment implements DeliveryInfoView {

    @Bind(R.id.tv_delivery_id)
    TextView mDeliveryIdTextView;
    @Bind(R.id.tv_send_date)
    TextView mSendDateTextView;
    @Bind(R.id.tv_sender_location)
    TextView mSenderLocationTextView;
    @Bind(R.id.tv_recipient_date)
    TextView mRecipientDateTextView;
    @Bind(R.id.tv_recipient_location)
    TextView mRecipientLocationTextView;
    @Bind(R.id.btn_sent)
    Button mSentButton;
    @Bind(R.id.btn_delivered)
    Button mDeliveredButton;
    @Bind(R.id.btn_obtained)
    Button mObtainedButton;
    @Bind(R.id.tv_sender_full_name)
    TextView mSenderFullNameTextView;
    @Bind(R.id.tv_sender_email)
    TextView mSenderEmailTextView;
    @Bind(R.id.tv_sender_phone_number)
    TextView mSenderPhoneNumberTextView;
    @Bind(R.id.tv_recipient_full_name)
    TextView mRecipientFullNameTextView;
    @Bind(R.id.tv_recipient_email)
    TextView mRecipientEmailTextView;
    @Bind(R.id.tv_recipient_phone_number)
    TextView mRecipientPhoneNumberTextView;
    @Bind(R.id.btn_find_delivery)
    TextView mFindDeliveryButton;

    private Delivery mDelivery;
    private DeliveryInfoPresenter mPresenter;
    private int mNum;
    private User mSenderUser;
    private User mRecipientUser;
    private LatLng mPoint = new LatLng(0, 0);
    private Dialog dialog;

    public static DeliveryInfoFragment newInstance(Delivery delivery, int num) {
        Bundle args = new Bundle();
        DeliveryInfoFragment fragment = new DeliveryInfoFragment();
        fragment.setArguments(args);
        fragment.setDelivery(delivery);
        fragment.setNum(num);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DeliveryInfoPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_info, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        mPresenter.loadRecipientUser(mDelivery.getRecipientId());
        mPresenter.loadSenderUser(mDelivery.getSenderId());
        mDeliveryIdTextView.setText("Number #" + mDelivery.getId());
        mSenderLocationTextView.setText(mDelivery.getSenderLocation());
        mRecipientLocationTextView.setText(mDelivery.getRecipientLocation());
        mSendDateTextView.setText(mDelivery.getSendDate());
        mRecipientDateTextView.setText(mDelivery.getSendDate());
        initStatus();
    }

    private void initStatus() {
//        if (SPManager.loadUserLoginData(getContext(), Constants.ROLE).equals("User")) {
//            mSentButton.setEnabled(false);
//            mDeliveredButton.setEnabled(false);
//            mObtainedButton.setEnabled(false);
//        }
//
//        if (SPManager.loadUserLoginData(getContext(), Constants.ROLE).equals("Administrator")) {
//            mSentButton.setEnabled(true);
//            mDeliveredButton.setEnabled(true);
//            mObtainedButton.setEnabled(true);
//        }

        switch (mDelivery.getStatus()) {
            case "Sent":
                setActiveSentButton();
                break;
            case "Delivered":
                setActiveDeliveredButton();
                break;
            case "Obtained":
                setActiveObtainedButton();
                break;
        }

        if (SPManager.loadUserLoginData(getContext(), Constants.ROLE).equals("Administrator")) {
            mSentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressDialog();
                    mPresenter.setSendStatus(mDelivery.getId(), mDelivery.getRecipientId());
                }
            });
            mDeliveredButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressDialog();
                    mPresenter.setDeliveredStatus(mDelivery.getId(), mDelivery.getRecipientId());
                }
            });
            mObtainedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressDialog();
                    mPresenter.setObtainedStatus(mDelivery.getId(), mDelivery.getRecipientId());
                }
            });
        }
    }

    private void setActiveSentButton() {
        mSentButton.setBackground(getActivity().getResources().getDrawable(R.drawable.status_selected));
        mDeliveredButton.setBackground(getActivity().getResources().getDrawable(R.drawable.status_unselected));
        mObtainedButton.setBackground(getActivity().getResources().getDrawable(R.drawable.status_unselected));
    }

    private void setActiveDeliveredButton() {
        mSentButton.setBackground(getActivity().getResources().getDrawable(R.drawable.status_unselected));
        mDeliveredButton.setBackground(getActivity().getResources().getDrawable(R.drawable.status_selected));
        mObtainedButton.setBackground(getActivity().getResources().getDrawable(R.drawable.status_unselected));
    }

    private void setActiveObtainedButton() {
        mSentButton.setBackground(getActivity().getResources().getDrawable(R.drawable.status_unselected));
        mDeliveredButton.setBackground(getActivity().getResources().getDrawable(R.drawable.status_unselected));
        mObtainedButton.setBackground(getActivity().getResources().getDrawable(R.drawable.status_selected));
    }

    private void setDelivery(Delivery delivery) {
        mDelivery = delivery;
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void successLoadSenderUser(User user) {
        mSenderUser = user;
        initSenderData();
    }

    @Override
    public void successLoadRecipientUser(User user) {
        mRecipientUser = user;
        initRecipientData();
    }

    private void initSenderData() {
        mSenderFullNameTextView.setText(mSenderUser.getSurname() + " " + mSenderUser.getFirstName()
                + " " + mSenderUser.getMiddleName());
        mSenderEmailTextView.setText(mSenderUser.getEmail());
        mSenderPhoneNumberTextView.setText(mSenderUser.getPhoneNumber());
    }

    private void initRecipientData() {
        mRecipientFullNameTextView.setText(mRecipientUser.getSurname() + " " + mRecipientUser.getFirstName()
                + " " + mRecipientUser.getMiddleName());
        mRecipientEmailTextView.setText(mRecipientUser.getEmail());
        mRecipientPhoneNumberTextView.setText(mRecipientUser.getPhoneNumber());
    }

    @Override
    public void onError() {
        hideProgressDialog();
        Utils.showToastMessage(getActivity(), "Error");
    }

    @Override
    public void successSetSentStatus() {
        hideProgressDialog();
        setActiveSentButton();
    }

    @Override
    public void successSetDeliveredStatus() {
        hideProgressDialog();
        setActiveDeliveredButton();
    }

    @Override
    public void successSetObtainedStatus() {
        hideProgressDialog();
        setActiveObtainedButton();
    }

    @Override
    public void successFindDeliveryLocation(List<Point> pointList) {
        EventBus.getDefault().post(new EventMainChangeFragment(ShowRouteOnMapFragment.newInstance(pointList, mPoint, "Delivery"), true, mNum));
    }

    @Override
    public void setRecipientLocation(LatLng point) {
        mPoint = point;
    }

    private void setNum(int num) {
        mNum = num;
    }

    @OnClick(R.id.btn_find_delivery)
    public void onFindDeliveryClick() {
        mPresenter.findDelivery(mDelivery.getId());
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
