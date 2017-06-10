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
import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.utils.SPManager;
import com.example.bobyk.np.views.main.driverDeliveries.DriverDeliveriesFragment;
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
    private DriverDeliveriesFragment mDriverDeliveriesFragment;
    private String mRole;

    private int currentPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mRole = getIntent().getStringExtra("role");
        System.out.println("EEE " + mRole);
        if (mRole != null && !mRole.equals("")) {
            SPManager.storeUserLoginData(getApplicationContext(), Constants.ROLE, mRole);
        } else {
            mRole = SPManager.loadUserLoginData(getApplicationContext(), Constants.ROLE);
        }
        initFragment();
        if (mBottomBar != null) {
            System.out.println("EEE " + mRole);
            switch (mRole) {
                case "Administrator":
                    initAdminsitratorBottomBar();
                    break;
                case "Driver":
                    initDriverBottomBar();
                    break;
                case "User":
                    initUserBottomBar();
                    break;
            }
        }
    }

    private void initAdminsitratorBottomBar() {
        currentPage = 2;
        mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_notification), false);
        mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_info), true);
        mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_profile), false);
        mBottomBar.addOnTabSelectedListener(this);
        setActiveCurrentFragment();
    }

    private void initDriverBottomBar() {
        currentPage = 2;
        mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_delivery), true);
        mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_profile), false);
        mBottomBar.addOnTabSelectedListener(this);
        setActiveCurrentFragment();
    }

    private void initUserBottomBar() {
        currentPage = 2;
        mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_notification), false);
        mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_info), true);
        mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_profile), false);
        mBottomBar.addOnTabSelectedListener(this);
        setActiveCurrentFragment();
    }

    private void initFragment() {
        mNotificationFragment = NotificationFragment.newInstance();
        mInfoHostFragment = InfoHostFragment.newInstance();
        mProfileHostFragment = ProfileHostFragment.newInstance();
        mDriverDeliveriesFragment = DriverDeliveriesFragment.newInstance();
    }

    private void setActiveCurrentFragment() {
        if (mRole.equals("User")) {
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
        }
        if (mRole.equals("Administrator")) {
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
        }
        if (mRole.equals("Driver")) {
            switch (currentPage) {
                case 1:
                    setActiveFragment(mDriverDeliveriesFragment, 1);
                    break;
                case 2:
                    setActiveFragment(mProfileHostFragment, 2);
                    break;
            }
        }
        mBottomBar.getTabAt(currentPage - 1).select();
    }

    private void setActiveFragment(Fragment fragment, int num) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mRole.equals("User")) {
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
        }

        if (mRole.equals("Administrator")) {
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
        }

        if (mRole.equals("Driver")) {
            switch (num) {
                case 1:
                    ft.replace(R.id.fl_main_container, fragment, "driverDeliveries");
                    break;
                case 2:
                    ft.replace(R.id.fl_main_container, fragment, "profilePage");
                    break;

            }
        }
        ft.commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        currentPage = tab.getPosition() + 1;
        if (mRole.equals("User")) {
            switch (tab.getPosition()) {
                case 0:
                    if (mNotificationFragment == null) {
                        mNotificationFragment = NotificationFragment.newInstance();
                    }
                    setActiveFragment(mNotificationFragment, 1);
                    break;
                case 1:
                    if (mInfoHostFragment == null) {
                        mInfoHostFragment = InfoHostFragment.newInstance();
                    }
                    setActiveFragment(mInfoHostFragment, 2);
                    break;
                case 2:
                    if (mProfileHostFragment == null) {
                        mProfileHostFragment = ProfileHostFragment.newInstance();
                    }
                    setActiveFragment(mProfileHostFragment, 3);
                    break;
            }
        }

        if (mRole.equals("Administrator")) {
            switch (tab.getPosition()) {
                case 0:
                    if (mNotificationFragment == null) {
                        mNotificationFragment = NotificationFragment.newInstance();
                    }
                    setActiveFragment(mNotificationFragment, 1);
                    break;
                case 1:
                    if (mInfoHostFragment == null) {
                        mInfoHostFragment = InfoHostFragment.newInstance();
                    }
                    setActiveFragment(mInfoHostFragment, 2);
                    break;
                case 2:
                    if (mProfileHostFragment == null) {
                        mProfileHostFragment = ProfileHostFragment.newInstance();
                    }
                    setActiveFragment(mProfileHostFragment, 3);
                    break;
            }
        }
        if (mRole.equals("Driver")) {
            switch (tab.getPosition()) {
                case 0:
                    if (mDriverDeliveriesFragment == null) {
                        mDriverDeliveriesFragment = DriverDeliveriesFragment.newInstance();
                    }
                    setActiveFragment(mDriverDeliveriesFragment, 1);
                    break;
                case 1:
                    if (mProfileHostFragment == null) {
                        mProfileHostFragment = ProfileHostFragment.newInstance();
                    }
                    setActiveFragment(mProfileHostFragment, 2);
                    break;
            }
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
        if (mRole.equals("User")) {
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
        if (mRole.equals("Administrator")) {
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
        if (mRole.equals("Driver")) {
            switch (mBottomBar.getSelectedTabPosition()) {
                case 0:
                    if (!mDriverDeliveriesFragment.onBackPressed()) {
                        this.finish();
                    }
                    break;
                case 1:
                    if (!mProfileHostFragment.onBackPressed()) {
                        this.finish();
                    }
                    break;
            }
        }
    }
}
