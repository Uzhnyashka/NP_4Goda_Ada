package com.example.bobyk.np.views.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.bobyk.np.R;
import com.example.bobyk.np.views.main.mainInfo.InfoHostFragment;
import com.example.bobyk.np.views.main.notifications.NotificationFragment;
import com.example.bobyk.np.views.main.profile.ProfileHostFragment;

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

    private NotificationFragment mNotificationFragment;
    private InfoHostFragment mInfoHostFragment;
    private ProfileHostFragment mProfileHostFragment;

    private int currentPage = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initFragment();
        if (mBottomBar != null) {
            mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_notification), false);
            mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_info), true);
            mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_profile), false);
            mBottomBar.addOnTabSelectedListener(this);
            setActiveCurrentFragment();
        }
    }

    private void initFragment() {
        mNotificationFragment = NotificationFragment.newInstance();
        mInfoHostFragment = InfoHostFragment.newInstance();
        mProfileHostFragment = ProfileHostFragment.newInstance();
    }

    private void setActiveCurrentFragment() {
        switch (currentPage) {
            case 1:
                setActiveFragment(mNotificationFragment, 1);
                break;
            case 2:
                setActiveFragment(mInfoHostFragment, 2);
                break;
            case 3:
                setActiveFragment(mProfileHostFragment, 3);
                break;
        }
        mBottomBar.getTabAt(currentPage - 1).select();
    }

    private void setActiveFragment(Fragment fragment, int num) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (num) {
            case 1:
                ft.replace(R.id.fl_main_container, fragment, "notifications");
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
        currentPage = tab.getPosition() + 1;
        switch (tab.getPosition()) {
            case 0:
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                if (mNotificationFragment == null) {
                    mNotificationFragment = NotificationFragment.newInstance();
                }
                setActiveFragment(mNotificationFragment, 1);
                break;
            case 1:
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                if (mInfoHostFragment == null) {
                    mInfoHostFragment = InfoHostFragment.newInstance();
                }
                setActiveFragment(mInfoHostFragment, 2);
                break;
            case 2:
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                if (mProfileHostFragment == null) {
                    mProfileHostFragment = ProfileHostFragment.newInstance();
                }
                setActiveFragment(mProfileHostFragment, 3);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {
        switch (mBottomBar.getSelectedTabPosition()) {
            case 0:
                if (!mNotificationFragment.onBackPressed()) {
                    this.finish();
                }
                break;
            case 1:
                if (!mInfoHostFragment.onBackPressed()) {
                    this.finish();
                }
                break;
            case 2:
                if (!mProfileHostFragment.onBackPressed()) {
                    this.finish();
                }
                break;
        }
    }
}
