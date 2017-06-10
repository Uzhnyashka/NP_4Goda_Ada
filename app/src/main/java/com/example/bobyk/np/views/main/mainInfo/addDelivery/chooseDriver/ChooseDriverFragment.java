package com.example.bobyk.np.views.main.mainInfo.addDelivery.chooseDriver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.example.bobyk.np.adapters.DriversAdapter;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.presenters.main.mainInfo.addDelivery.chooseDriver.ChooseDriverPresenter;
import com.example.bobyk.np.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/10/17.
 */

public class ChooseDriverFragment extends Fragment implements ChooseDriverView {

    @Bind(R.id.rv_drivers)
    RecyclerView mDriversRecyclerView;

    private Delivery mDelivery;
    private DriversAdapter mAdapter;
    private List<Driver> mDrivers = new ArrayList<>();
    private ChooseDriverPresenter mPresenter;

    public static ChooseDriverFragment newInstance(Delivery delivery) {
        Bundle args = new Bundle();
        ChooseDriverFragment fragment = new ChooseDriverFragment();
        fragment.setArguments(args);
        fragment.setDelivery(delivery);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DriversAdapter(getContext(), mDrivers);
        mPresenter = new ChooseDriverPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_driver, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        mPresenter.loadAllDrivers();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mDriversRecyclerView.setLayoutManager(manager);
        mDriversRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }

    private void setDelivery(Delivery delivery) {
        mDelivery = delivery;
    }

    @Override
    public void successLoadDriver(List<Driver> drivers) {
        mDrivers.clear();
        mDrivers.addAll(drivers);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void error() {
        Utils.showToastMessage(getActivity(), "Error load drivers");
    }

    @OnClick(R.id.btn_add_delivery)
    public void onAddDeliveryClick() {

    }
}
