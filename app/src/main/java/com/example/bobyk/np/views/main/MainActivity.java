package com.example.bobyk.np.views.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.bobyk.np.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bobyk on 3/25/17.
 */

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @Bind(R.id.bottomBar)
    TabLayout mBottomBar;
    @Bind(R.id.fl_main_container)
    FrameLayout mainContainerFrameLayout;
    @Bind(R.id.border_main)
    View borderMainView;

    private int currentPage = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (mBottomBar != null) {
            mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selecetor_notification), false);
            mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selecetor_notification), true);
            mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selecetor_notification), false);
            mBottomBar.addOnTabSelectedListener(this);
            setActiveCurrentFragment();
        }
        initFragment();
    }

    private void initFragment() {

    }

    private void setActiveCurrentFragment() {
        switch (currentPage) {
            case 1:
                setActiveFragment(homeFragment, 1);
                break;
            case 2:
                setActiveFragment(userActivityFragment, 2);
                break;
            case 3:
                setActiveFragment(profileFragment, 3);
                break;
        }
        mBottomBar.getTabAt(currentPage - 1).select();
    }

    private void setActiveFragment(Fragment fragment, int num) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (num) {
            case 1:
                ft.replace(R.id.fl_main_container, fragment, "Notifications");
                break;
            case 2:
                ft.replace(R.id.fl_main_container, fragment, "mainInfoPage");
                break;
            case 3:
                ft.replace(R.id.fl_main_container, fragment, "profilePage");
                break;

        }
        ft.commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
