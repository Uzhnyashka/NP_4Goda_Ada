package com.example.bobyk.np.views.main.mainInfo.deliveries;

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
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.listeners.OnItemClickListener;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.presenters.main.mainInfo.deliveries.DeliveriesPresenter;
import com.example.bobyk.np.presenters.main.users.deliveryInfo.DeliveryInfoPresenter;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.users.deliveryInfo.DeliveryInfoFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/6/17.
 */

public class DeliveriesFragment extends Fragment implements DeliveriesView {

    @Bind(R.id.rv_deliveries)
    RecyclerView mDeliveriesRecyclerView;

    private DeliveriesAdapter mAdapter;
    private List<Delivery> mDeliveryList = new ArrayList<>();
    private DeliveriesPresenter mPresenter;

    public static DeliveriesFragment newInstance() {
        Bundle args = new Bundle();
        DeliveriesFragment fragment = new DeliveriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DeliveriesAdapter(getContext(), mDeliveryList);
        mPresenter = new DeliveriesPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_deliveries, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        mPresenter.loadDeliveries();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mDeliveriesRecyclerView.setLayoutManager(manager);
        mDeliveriesRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().post(new EventMainChangeFragment(DeliveryInfoFragment.newInstance(mDeliveryList.get(position), 2), true, 2));
            }
        });
    }

    @Override
    public void onSuccessLoadDeliveries(List<Delivery> deliveries) {
        mDeliveryList.clear();
        mDeliveryList.addAll(deliveries);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Utils.showToastMessage(getActivity(), "Error load deliveries");
    }
}
