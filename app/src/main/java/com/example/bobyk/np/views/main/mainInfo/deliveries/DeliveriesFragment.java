package com.example.bobyk.np.views.main.mainInfo.deliveries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bobyk on 6/6/17.
 */

public class DeliveriesFragment extends Fragment implements DeliveriesView {

    public static DeliveriesFragment newInstance() {
        Bundle args = new Bundle();
        DeliveriesFragment fragment = new DeliveriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}