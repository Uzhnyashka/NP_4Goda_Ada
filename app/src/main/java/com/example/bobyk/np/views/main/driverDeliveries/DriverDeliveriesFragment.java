package com.example.bobyk.np.views.main.driverDeliveries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.example.bobyk.np.adapters.DeliveriesAdapter;
import com.example.bobyk.np.listeners.OnItemClickListener;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.presenters.main.driverDeliveries.DriverDeliveriesPresenter;
import com.example.bobyk.np.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/10/17.
 */

public class DriverDeliveriesFragment extends Fragment implements DriverDeliveriesView {


    @Bind(R.id.rv_driver_deliveries)
    RecyclerView mDriverDeliveriesRecyclerView;

    private DriverDeliveriesPresenter mPresenter;
    private List<Delivery> deliveryList = new ArrayList<>();
    private DeliveriesAdapter mAdapter;

    public static DriverDeliveriesFragment newInstance() {
        Bundle args = new Bundle();
        DriverDeliveriesFragment fragment = new DriverDeliveriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DriverDeliveriesPresenter(getActivity(), this);
        mAdapter = new DeliveriesAdapter(getContext(), deliveryList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_deliveries, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        mPresenter.loadDriverDeliveries();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mDriverDeliveriesRecyclerView.setLayoutManager(manager);
        mDriverDeliveriesRecyclerView.setAdapter(mAdapter);
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onSuccessLoadDeliveries(List<Delivery> deliveries) {
        deliveryList.clear();
        deliveryList.addAll(deliveries);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Utils.showToastMessage(getActivity(), "Error");
    }
}
