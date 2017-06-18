package com.example.bobyk.np.views.main.messages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = inflater.inflate(R.layout.fragment_notification, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
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
        mNotifications.clear();
        mNotifications.addAll(notifications);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Utils.showToastMessage(getActivity(), "Error load messages");
    }
}
