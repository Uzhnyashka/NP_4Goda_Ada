package com.example.bobyk.np.views.main.mainInfo.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.example.bobyk.np.presenters.main.mainInfo.info.InfoScreenPresenter;

import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/6/17.
 */

public class InfoScreenFragment extends Fragment implements InfoScreenView {

    private InfoScreenPresenter mPresenter;

    public static InfoScreenFragment newInstance() {
        Bundle args = new Bundle();
        InfoScreenFragment fragment = new InfoScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new InfoScreenPresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_screen, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {

    }
}
