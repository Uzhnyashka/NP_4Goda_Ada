package com.example.bobyk.np.views.main.mainInfo.deliveries;

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
import android.widget.TextView;

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
import butterknife.OnClick;

/**
 * Created by bobyk on 6/6/17.
 */

public class DeliveriesFragment extends Fragment implements DeliveriesView {

    @Bind(R.id.rv_deliveries)
    RecyclerView mDeliveriesRecyclerView;
    @Bind(R.id.tv_toolbar_text)
    TextView mToolbarTextView;

    private DeliveriesAdapter mAdapter;
    private List<Delivery> mDeliveryList = new ArrayList<>();
    private DeliveriesPresenter mPresenter;
    private Dialog dialog;
    private String mLabel;

    public static DeliveriesFragment newInstance(String label) {
        Bundle args = new Bundle();
        DeliveriesFragment fragment = new DeliveriesFragment();
        fragment.setArguments(args);
        fragment.setLabel(label);
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
        mToolbarTextView.setText(mLabel);
        showProgressDialog();
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
        hideProgressDialog();
        mDeliveryList.clear();
        mDeliveryList.addAll(deliveries);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        hideProgressDialog();
        Utils.showToastMessage(getActivity(), "Error load deliveries");
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

    private void setLabel(String label) {
        mLabel = label;
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }
}
