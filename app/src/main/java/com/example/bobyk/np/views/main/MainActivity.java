package com.example.bobyk.np.views.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.bobyk.np.R;
import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.services.LoadLocationService;
import com.example.bobyk.np.utils.SPManager;
import com.example.bobyk.np.views.main.driverDeliveries.DriverDeliveriesFragment;
import com.example.bobyk.np.views.main.mainInfo.InfoHostFragment;
import com.example.bobyk.np.views.main.messages.MessagesFragment;
import com.example.bobyk.np.views.main.profile.ProfileHostFragment;
import com.example.bobyk.np.views.main.users.UsersHostFragment;

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

    private MessagesFragment mNotificationFragment;
    private InfoHostFragment mInfoHostFragment;
    private ProfileHostFragment mProfileHostFragment;
    private DriverDeliveriesFragment mDriverDeliveriesFragment;
    private UsersHostFragment mUsersHostFragment;
    private String mRole;

//    LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            System.out.println("WWW " + location.getLatitude() + " " + location.getLongitude());
//            updateDriverLocation(location.getLatitude(), location.getLongitude());
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            System.out.println("WWW onStatusChanged");
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//            System.out.println("WWW onProviderEnabled");
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//            System.out.println("WWW onProviderDisabled");
//        }
//    };

    private int currentPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("WWW onCreate");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mRole = getIntent().getStringExtra("role");
        if (mRole != null && !mRole.equals("")) {
            SPManager.storeUserLoginData(getApplicationContext(), Constants.ROLE, mRole);
        } else {
            mRole = SPManager.loadUserLoginData(getApplicationContext(), Constants.ROLE);
        }


        if (mRole.equals("Driver")) {
            System.out.println("checkLockPerm");
            if (checkLocationPermission()) {
                System.out.println("WWW startService");
                startService(new Intent(this, LoadLocationService.class));
                initFragment();
                if (mBottomBar != null) {
                    initDriverBottomBar();
                }
            } else {
                System.out.println("checkPerm");
                checkPermission();
            }
        } else {
            initFragment();
            if (mBottomBar != null) {
                switch (mRole) {
                    case "Administrator":
                        initAdministratorBottomBar();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
//        trackDriver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        mLocationManager.removeUpdates(mLocationListener);
    }

    private boolean checkLocationPermission() {
        int permissionCoarseCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionFineCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionCoarseCheck == PackageManager.PERMISSION_GRANTED && permissionFineCheck == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            }
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION },
                    Constants.ACCESS_COARSE_LOCATION_REQUEST);
            return;
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.ACCESS_COARSE_LOCATION_REQUEST);
            return;
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.ACCESS_COARSE_LOCATION_REQUEST);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean ok = true;
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ok = false;
                    break;
                }
            }
            if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ok = false;
                    break;
                }
            }
        }
        if (mRole.equals("Driver")) {
            if (ok) init();
            else checkPermission();
        }
    }

    private void initAdministratorBottomBar() {
        currentPage = 2;
        mBottomBar.addTab(mBottomBar.newTab().setIcon(R.drawable.selector_users), false);
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
        mNotificationFragment = MessagesFragment.newInstance();
        mInfoHostFragment = InfoHostFragment.newInstance();
        mProfileHostFragment = ProfileHostFragment.newInstance();
        mDriverDeliveriesFragment = DriverDeliveriesFragment.newInstance();
        mUsersHostFragment = UsersHostFragment.newInstance();
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
                    setActiveFragment(mUsersHostFragment, 1);
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
                    ft.replace(R.id.fl_main_container, fragment, "users");
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
                        mNotificationFragment = MessagesFragment.newInstance();
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
                    if (mUsersHostFragment == null) {
                        mUsersHostFragment = UsersHostFragment.newInstance();
                    }
                    setActiveFragment(mUsersHostFragment, 1);
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
                    if (!mUsersHostFragment.onBackPressed()) {
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
//
//    private void trackDriver() {
//        System.out.println("WWW track driver");
//        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
//                1000, mLocationListener);
//        mLocationManager.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER, 1000, 1000,
//                mLocationListener);
//    }

    private void updateDriverLocation(final Double latitude, final Double longitude) {
//        database.child("drivers").child(user.getUid()).runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                Driver driver = mutableData.getValue(Driver.class);
//                if (driver != null) {
//                    System.out.println("WWW driver not null");
//                    if (driver.getPoints() == null) {
//                        List<Point> points = new ArrayList<>();
//                        points.add(new Point(latitude, longitude));
//                        driver.setPoints(points);
//                    } else {
//                        driver.getPoints().add(new Point(latitude, longitude));
//                        database.child("drivers").child(user.getUid()).setValue(driver);
//                    }
//                } else {
//                    return Transaction.success(mutableData);
//                }
//                mutableData.setValue(driver);
//                return Transaction.success(mutableData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
//
//            }
//        });
//        database.child("drivers").child(user.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Driver driver = dataSnapshot.getValue(Driver.class);
//                if (driver != null) {
//                    System.out.println("WWW driver not null");
//                    if (driver.getPoints() == null) {
//                        List<Point> points = new ArrayList<>();
//                        points.add(new Point(latitude, longitude));
//                        driver.setPoints(points);
//                    } else {
//                        driver.getPoints().add(new Point(latitude, longitude));
//
//                        database.child("drivers").child(user.getUid()).setValue(driver);
//                    }
//                } else {
//                    System.out.println("WWW driver null");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
