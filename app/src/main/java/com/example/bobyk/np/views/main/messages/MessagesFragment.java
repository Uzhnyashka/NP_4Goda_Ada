package com.example.bobyk.np.views.main.messages;

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
import com.example.bobyk.np.adapters.MessagesAdapter;
import com.example.bobyk.np.models.main.Message;
import com.example.bobyk.np.presenters.main.messages.MessagesPresenter;
import com.example.bobyk.np.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/6/17.
 */

public class MessagesFragment extends Fragment implements MessagesView {

    @Bind(R.id.rv_notifications)
    RecyclerView mNotificationsRecyclerView;

    private MessagesPresenter mPresenter;
    private MessagesAdapter mAdapter;
    private List<Message> mNotifications = new ArrayList<>();
    private Dialog dialog;

    public static MessagesFragment newInstance() {
        Bundle args = new Bundle();
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MessagesPresenter(getActivity(), this);
        mAdapter = new MessagesAdapter(getContext(), mNotifications);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        showProgressDialog();
        mPresenter.loadNotifications();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mNotificationsRecyclerView.setLayoutManager(manager);
        mNotificationsRecyclerView.setAdapter(mAdapter);
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void setNotificationList(List<Message> notifications) {
        hideProgressDialog();
        mNotifications.clear();
        mNotifications.addAll(notifications);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        hideProgressDialog();
        Utils.showToastMessage(getActivity(), "Error load messages");
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
