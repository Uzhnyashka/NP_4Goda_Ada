package com.example.bobyk.np.views.main.mainInfo.addDelivery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.presenters.main.mainInfo.addDelivery.AddDeliveryPresenter;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.mainInfo.addDelivery.chooseDriver.ChooseDriverFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/10/17.
 */

public class AddDeliveryFragment extends Fragment implements AddDeliveryView{

    @Bind(R.id.et_sender_data)
    EditText mSenderDataEditText;
    @Bind(R.id.et_sender_location)
    EditText mSenderLocationEditText;
    @Bind(R.id.et_recipient_data)
    EditText mRecipientDataEditText;
    @Bind(R.id.et_recipient_location)
    EditText mRecipientLocationEditText;
    @Bind(R.id.et_weight)
    EditText mWeightEditText;
    @Bind(R.id.et_price)
    EditText mPriceEditText;

    private AddDeliveryPresenter mPresenter;

    public static AddDeliveryFragment newInstance() {
        Bundle args = new Bundle();
        AddDeliveryFragment fragment = new AddDeliveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddDeliveryPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_delivery, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onSuccessAddUserData(Delivery delivery) {
        Utils.showToastMessage(getActivity(), "Success");
        EventBus.getDefault().post(new EventMainChangeFragment(ChooseDriverFragment.newInstance(delivery), true, 2));
    }

    @Override
    public void onFailedAddUserData(String message) {
        Utils.showToastMessage(getActivity(), message);
    }

    @OnClick(R.id.btn_next)
    public void onNextClick() {
        Delivery delivery = new Delivery();
        delivery.setWeight(Double.valueOf(mWeightEditText.getText().toString()));
        delivery.setPrice(Double.valueOf(mPriceEditText.getText().toString()));
        delivery.setSenderLocation(mSenderLocationEditText.getText().toString());
        delivery.setRecipientLocation(mRecipientLocationEditText.getText().toString());

        mPresenter.onAddDelivery(delivery, mSenderDataEditText.getText().toString(),
                mRecipientDataEditText.getText().toString());
    }

}
