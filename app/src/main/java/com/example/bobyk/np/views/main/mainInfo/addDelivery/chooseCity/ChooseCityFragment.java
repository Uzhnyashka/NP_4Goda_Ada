package com.example.bobyk.np.views.main.mainInfo.addDelivery.chooseCity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.example.bobyk.np.adapters.CitiesAdapter;
import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.listeners.OnItemClickListener;
import com.example.bobyk.np.models.main.City;
import com.example.bobyk.np.presenters.main.mainInfo.addDelivery.chooseCity.ChooseCityPresenter;
import com.example.bobyk.np.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bobyk on 6/13/17.
 */

public class ChooseCityFragment extends Fragment implements ChooseCityView {

    @Bind(R.id.rv_cities)
    RecyclerView mCitiesRecyclerView;

    private ChooseCityPresenter mPresenter;
    private CitiesAdapter mAdapter;
    private List<City> mCityList = new ArrayList<>();
    private String mType;

    public static ChooseCityFragment newInstance(String type) {
        Bundle args = new Bundle();
        ChooseCityFragment fragment = new ChooseCityFragment();
        fragment.setArguments(args);
        fragment.setType(type);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ChooseCityPresenter(getActivity(), this);
        mAdapter = new CitiesAdapter(getContext(), mCityList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_city, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        mPresenter.loadAllCities();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (mType.equals("Sender")) {
                    Intent intent = new Intent("ChooseCity");
                    intent.putExtra(Constants.PARAM_CHOOSE_CITY_TASK, 1);
                    intent.putExtra(Constants.PARAM_NAME_CITY, mCityList.get(position).getName());
                    getActivity().sendBroadcast(intent);
                } else if (mType.equals("Recipient")) {
                    Intent intent = new Intent("ChooseCity");
                    intent.putExtra(Constants.PARAM_CHOOSE_CITY_TASK, 2);
                    intent.putExtra(Constants.PARAM_NAME_CITY, mCityList.get(position).getName());
                    getActivity().sendBroadcast(intent);
                }
                getActivity().onBackPressed();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mCitiesRecyclerView.setLayoutManager(manager);
        mCitiesRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void successLoadCities(List<City> cities) {
        mCityList.clear();;
        mCityList.addAll(cities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Utils.showToastMessage(getActivity(), "Error load cities");
    }

    private void setType(String type) {
        mType = type;
    }
}
