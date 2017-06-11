package com.example.bobyk.np.views.main.users.deliveryInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.presenters.main.users.deliveryInfo.DeliveryInfoPresenter;
import com.example.bobyk.np.utils.SPManager;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.map.ShowLocationOnMapFragment;

import org.greenrobot.eventbus.EventBus;

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
        mDeliveryIdTextView.setText(mDelivery.getId());
        mSenderLocationTextView.setText(mDelivery.getSenderLocation());
        mRecipientLocationTextView.setText(mDelivery.getRecipientLocation());
        mSendDateTextView.setText(mDelivery.getSendDate());
        mRecipientDateTextView.setText(mDelivery.getRecipientDate());
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
                    mPresenter.setSendStatus(mDelivery.getId());
                }
            });
            mDeliveredButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.setDeliveredStatus(mDelivery.getId());
                }
            });
            mObtainedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.setObtainedStatus(mDelivery.getId());
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
        Utils.showToastMessage(getActivity(), "Error");
    }

    @Override
    public void successSetSentStatus() {
        setActiveSentButton();
    }

    @Override
    public void successSetDeliveredStatus() {
        setActiveDeliveredButton();
    }

    @Override
    public void successSetObtainedStatus() {
        setActiveObtainedButton();
    }

    @Override
    public void successFindDeliveryLocation(Double latitude, Double longitude) {
        EventBus.getDefault().post(new EventMainChangeFragment(ShowLocationOnMapFragment.newInstance(latitude, longitude, "Delivery"), true, mNum));
    }

    private void setNum(int num) {
        mNum = num;
    }

    @OnClick(R.id.btn_find_delivery)
    public void onFindDeliveryClick() {
        mPresenter.findDelivery(mDelivery.getId());
    }
}