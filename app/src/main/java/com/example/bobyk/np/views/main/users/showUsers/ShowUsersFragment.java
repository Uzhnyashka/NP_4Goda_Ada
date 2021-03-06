package com.example.bobyk.np.views.main.users.showUsers;

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
import com.example.bobyk.np.adapters.UsersAdapter;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.listeners.OnItemClickListener;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.presenters.main.users.showUsers.ShowUsersPresenter;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.users.userInfo.UserInfoFragment;

import org.greenrobot.eventbus.EventBus;

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
    private Dialog dialog;

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
        showProgressDialog();
        mPresenter.loadUsers();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mUsersRecyclerView.setLayoutManager(manager);
        mUsersRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().post(new EventMainChangeFragment(UserInfoFragment.newInstance(mUserList.get(position)), true, 4));
            }
        });
    }

    @Override
    public void onSuccessLoadUsers(List<User> users) {
        hideProgressDialog();
        mUserList.clear();
        mUserList.addAll(users);
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
        Utils.showToastMessage(getActivity(), "Error load users");
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }
}
