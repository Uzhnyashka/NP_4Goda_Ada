package com.example.bobyk.np.views.main.notifications;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.example.bobyk.np.adapters.NotificationsAdapter;
import com.example.bobyk.np.models.main.Notification;
import com.example.bobyk.np.presenters.main.notifications.NotificationPresenter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/6/17.
 */

public class NotificationFragment extends Fragment implements NotificationView {

    private NotificationPresenter mPresenter;
    private NotificationsAdapter mAdapter;
    private List<Notification> mNotifications;

    public static NotificationFragment newInstance() {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new NotificationPresenter(getActivity(), this);
        mAdapter = new NotificationsAdapter(getContext(), mNotifications);
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

    }

    public boolean onBackPressed() {
        return false;
    }
}
