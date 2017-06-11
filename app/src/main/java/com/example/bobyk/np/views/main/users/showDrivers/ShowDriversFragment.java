package com.example.bobyk.np.views.main.users.showDrivers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.example.bobyk.np.adapters.ShowDriversAdapter;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.listeners.OnItemClickListener;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.presenters.main.users.showDrivers.ShowDriversPresenter;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.users.driverInfo.DriverInfoFragment;
import com.google.gson.annotations.Expose;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/10/17.
 */

public class ShowDriversFragment extends Fragment implements ShowDriversView {

    @Bind(R.id.rv_drivers)
    RecyclerView mDriversRecyclerView;

    private ShowDriversPresenter mPresenter;
    private ShowDriversAdapter mAdapter;
    private List<Driver> mDriverList = new ArrayList<>();

    public static ShowDriversFragment newInstance() {
        Bundle args = new Bundle();
        ShowDriversFragment fragment = new ShowDriversFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ShowDriversPresenter(getActivity(), this);
        mAdapter = new ShowDriversAdapter(getContext(), mDriverList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_drivers, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        mPresenter.loadDrivers();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mDriversRecyclerView.setLayoutManager(manager);
        mDriversRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().post(new EventMainChangeFragment(DriverInfoFragment.newInstance(mDriverList.get(position)), true, 4));
            }
        });
    }

    @Override
    public void onSuccessLoadDrivers(List<Driver> drivers) {
        mDriverList.clear();
        mDriverList.addAll(drivers);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Utils.showToastMessage(getActivity(), "Error load drivers");
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }
}
