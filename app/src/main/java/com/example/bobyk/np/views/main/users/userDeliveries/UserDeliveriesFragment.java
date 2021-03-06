package com.example.bobyk.np.views.main.users.userDeliveries;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.bobyk.np.R;
import com.example.bobyk.np.adapters.DeliveriesAdapter;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.listeners.OnItemClickListener;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.main.Delivery;
import com.example.bobyk.np.presenters.main.users.userDeliveries.UserDeliveriesPresenter;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.users.deliveryInfo.DeliveryInfoFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/11/17.
 */

public class UserDeliveriesFragment extends Fragment implements UserDeliveriesView {

    @Bind(R.id.rv_deliveries)
    RecyclerView mDeliveriesRecyclerView;

    private User mUser;
    private UserDeliveriesPresenter mPresenter;
    private DeliveriesAdapter mAdapter;
    private List<Delivery> mDeliveryList = new ArrayList<>();
    private Dialog dialog;

    public static UserDeliveriesFragment newInstance(User user) {
        Bundle args = new Bundle();
        UserDeliveriesFragment fragment = new UserDeliveriesFragment();
        fragment.setArguments(args);
        fragment.setUser(user);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new UserDeliveriesPresenter(getActivity(), this);
        mAdapter = new DeliveriesAdapter(getContext(), mDeliveryList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_deliveries, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        showProgressDialog();
        mPresenter.loadDeliveriesForUser(mUser.getEmail());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mDeliveriesRecyclerView.setLayoutManager(manager);
        mDeliveriesRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().post(new EventMainChangeFragment(DeliveryInfoFragment.newInstance(mDeliveryList.get(position), 4), true, 4));
            }
        });
    }

    @Override
    public void successLoadUserDeliveries(List<Delivery> deliveries) {
        hideProgressDialog();
        mDeliveryList.clear();
        mDeliveryList.addAll(deliveries);
        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onError() {
        hideProgressDialog();
        Utils.showToastMessage(getActivity(), "Error load user deliveries");
    }

    private void setUser(User user) {
        mUser = user;
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }
}
