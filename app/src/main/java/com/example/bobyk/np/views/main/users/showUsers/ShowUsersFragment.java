package com.example.bobyk.np.views.main.users.showUsers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bobyk.np.R;
import com.example.bobyk.np.adapters.UsersAdapter;
import com.example.bobyk.np.listeners.OnItemClickListener;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.presenters.main.users.showUsers.ShowUsersPresenter;
import com.example.bobyk.np.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/10/17.
 */

public class ShowUsersFragment extends Fragment implements ShowUsersView {

    @Bind(R.id.rv_users)
    RecyclerView mUsersRecyclerView;

    private ShowUsersPresenter mPresenter;
    private List<User> mUserList = new ArrayList<>();
    private UsersAdapter mAdapter;

    public static ShowUsersFragment newInstance() {
        Bundle args = new Bundle();
        ShowUsersFragment fragment = new ShowUsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ShowUsersPresenter(getActivity(), this);
        mAdapter = new UsersAdapter(getContext(), mUserList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_users, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        mPresenter.loadUsers();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mUsersRecyclerView.setLayoutManager(manager);
        mUsersRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    @Override
    public void onSuccessLoadUsers(List<User> users) {
        mUserList.clear();
        mUserList.addAll(users);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Utils.showToastMessage(getActivity(), "Error load users");
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }
}
