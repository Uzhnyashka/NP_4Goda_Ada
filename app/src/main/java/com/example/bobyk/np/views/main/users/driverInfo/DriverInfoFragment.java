package com.example.bobyk.np.views.main.users.driverInfo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.map.ShowLocationOnMapFragment;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bobyk on 6/10/17.
 */

public class DriverInfoFragment extends Fragment {

    @Bind(R.id.tv_first_name)
    TextView mFirstNameTextView;
    @Bind(R.id.tv_surname)
    TextView mSurnameTextView;
    @Bind(R.id.tv_middle_name)
    TextView mMiddleNameTextView;
    @Bind(R.id.tv_email)
    TextView mEmailTextView;
    @Bind(R.id.tv_phone_number)
    TextView mPhoneNumberTextView;
    @Bind(R.id.iv_driver)
    CircleImageView mDriverImageView;

    private Driver mDriver;
    private DisplayImageOptions mOptions;
    private ImageLoader imageLoader;

    public static DriverInfoFragment newInstance(Driver driver) {
        Bundle args = new Bundle();
        DriverInfoFragment fragment = new DriverInfoFragment();
        fragment.setArguments(args);
        fragment.setDriver(driver);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configImageLoader();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_info, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        if (mDriver != null) {
            if (mDriver.getPhotoUrl() != null && !mDriver.getPhotoUrl().equals("")) {
                imageLoader.displayImage(mDriver.getPhotoUrl(), mDriverImageView, mOptions);
            } else {
                imageLoader.displayImage("drawable://" + R.drawable.driver_icon, mDriverImageView, mOptions);
            }
            mFirstNameTextView.setText(mDriver.getFirstName());
            mSurnameTextView.setText(mDriver.getSurname());
            mMiddleNameTextView.setText(mDriver.getMiddleName());
            mEmailTextView.setText(mDriver.getEmail());
            mPhoneNumberTextView.setText(mDriver.getPhoneNumber());
        }
    }

    private void setDriver(Driver driver) {
        mDriver = driver;
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.btn_find_driver)
    public void onFindDriverClick() {
        if (mDriver.getLatitude().doubleValue() != 0 && mDriver.getLongitude().doubleValue() != 0) {
            EventBus.getDefault().post(new EventMainChangeFragment(
                    ShowLocationOnMapFragment.newInstance(mDriver.getLatitude(), mDriver.getLongitude()), true, 4));
        } else {
            Utils.showToastMessage(getActivity(), "Driver location data is empty");
        }
    }

    private void configImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getContext())
                .defaultDisplayImageOptions(mOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);

        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .showImageForEmptyUri(R.color.colorWhite)
                .showImageOnFail(R.color.colorWhite)
                .showImageOnLoading(R.color.colorWhite)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }
}
