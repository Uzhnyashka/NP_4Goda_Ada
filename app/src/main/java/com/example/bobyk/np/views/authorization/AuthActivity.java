package com.example.bobyk.np.views.authorization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.bobyk.np.R;
import com.example.bobyk.np.customViews.SeasonsBackgroundData;
import com.example.bobyk.np.customViews.SlideView;
import com.example.bobyk.np.event.EventAuthChangeFragment;
import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.models.background.Background;
import com.example.bobyk.np.models.background.Season;
import com.example.bobyk.np.utils.SPManager;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.utils.WeatherImageConverter;
import com.example.bobyk.np.views.authorization.signIn.SignInFragment;
import com.example.bobyk.np.views.authorization.startScreen.StartScreenFragment;
import com.example.bobyk.np.views.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bobyk on 3/25/17.
 */

public class AuthActivity extends AppCompatActivity {

    @Bind(R.id.container_fl)
    FrameLayout mContainerFrameLayout;
//    @Bind(R.id.background_slides)
    SlideView backgroundSlidesView;

    private BroadcastReceiver mBroadcastReceiver;
    private boolean registred;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        ButterKnife.bind(this);
        Utils.isNetworkConnected(this);
        checkAuthUser();
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void checkAuthUser() {
        if (!SPManager.loadUserLoginData(getApplicationContext(), Constants.PARAM_USER_ID).equals("")) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private void init() {
        changeFragment(StartScreenFragment.newInstance(), false);
        configBroadcastReceiver();
    }

    private void configBroadcastReceiver() {
        registred = false;
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int task = intent.getIntExtra(Constants.PARAM_TASK, 0);
                if (task == Constants.REGISTRED) {
                    registred = true;
                }
            }
        };
        IntentFilter filter = new IntentFilter(Constants.BROADCAST_ACTION);
        registerReceiver(mBroadcastReceiver, filter);
    }

    private void initBG() {
        loadDefaultBackground();
    }

    private void loadDefaultBackground() {
        Gson gson = new Gson();
        String json = Utils.loadJSONFromAsset(AuthActivity.this, "default_background.json");
        Season season = gson.fromJson(json, Season.class);
        Background background = season.getBackground();
        SeasonsBackgroundData seasonsBackground = new SeasonsBackgroundData(
                background.getColors(), WeatherImageConverter.imagesFromRes(season.getBackground().getImagesIos()));

        backgroundSlidesView.setAnimatedImages(seasonsBackground);
        backgroundSlidesView.startAnimation(true);
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack) {
        if (!addToBackStack) {
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStack();
            }
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_fl, fragment);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    @Subscribe
    public void onEvent(EventAuthChangeFragment eventFragment) {
        changeFragment(eventFragment.getFragment(), eventFragment.isAddToBackStack());
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("EEEE " + registred);
        if (!registred) {
            FirebaseAuth.getInstance().getCurrentUser().delete();
        }
        SPManager.storeUserLoginData(this, Constants.PARAM_USERNAME, "");
        super.onDestroy();
    }
}
