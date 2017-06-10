package com.example.bobyk.np.views.main.mainInfo.info;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.presenters.main.mainInfo.info.InfoScreenPresenter;
import com.example.bobyk.np.views.main.mainInfo.addAdmin.AddAdminFragment;
import com.example.bobyk.np.views.main.mainInfo.addDelivery.AddDeliveryFragment;
import com.example.bobyk.np.views.main.mainInfo.addDriver.AddDriverFragment;
import com.example.bobyk.np.views.main.mainInfo.deliveries.DeliveriesFragment;
import com.example.bobyk.np.views.main.mainInfo.tracking.TrackingFragment;
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

/**
 * Created by bobyk on 6/6/17.
 */

public class InfoScreenFragment extends Fragment implements InfoScreenView {

    @Bind(R.id.iv_admin)
    ImageView mAdminImageView;
    @Bind(R.id.iv_driver)
    ImageView mDriverImageView;
    @Bind(R.id.iv_delivery)
    ImageView mDeliveryImageView;
    @Bind(R.id.iv_deliveries)
    ImageView mDeliveriesImageView;
    @Bind(R.id.iv_track)
    ImageView mTrackImageView;
    @Bind(R.id.iv_calculate)
    ImageView mCalculateImageView;

    private InfoScreenPresenter mPresenter;
    private DisplayImageOptions mOptions;
    private ImageLoader mImageLoader;

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
        configImageLoader();
        init();

        return view;
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
                .showImageForEmptyUri(R.color.colorWhite)
                .showImageOnFail(R.color.colorWhite)
                .showImageOnLoading(R.color.colorWhite)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .resetViewBeforeLoading(true)
                .build();

        mImageLoader = ImageLoader.getInstance();
    }

    private void init() {
        mImageLoader.displayImage("drawable://" + R.drawable.admin_icon, mAdminImageView, mOptions);
        mImageLoader.displayImage("drawable://" + R.drawable.driver_icon, mDriverImageView, mOptions);
        mImageLoader.displayImage("drawable://" + R.drawable.delivery_ic, mDeliveryImageView, mOptions);
        mImageLoader.displayImage("drawable://" + R.drawable.del, mDeliveriesImageView, mOptions);
        mImageLoader.displayImage("drawable://" + R.drawable.ic_track, mTrackImageView, mOptions);
        mImageLoader.displayImage("drawable://" + R.drawable.calculator, mCalculateImageView, mOptions);
    }

    @OnClick(R.id.rl_track)
    public void onTrackClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(TrackingFragment.newInstance(), true, 2));
    }

    @OnClick(R.id.rl_driver)
    public void onDriverClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(AddDriverFragment.newInstance(), true, 2));
    }

    @OnClick(R.id.rl_admin)
    public void onAdminClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(AddAdminFragment.newInstance(), true, 2));
    }

    @OnClick(R.id.rl_add_delivery)
    public void onAddDeliveryClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(AddDeliveryFragment.newInstance(), true, 2));
    }

    @OnClick(R.id.rl_my_deliveries)
    public void onDeliveriesClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(DeliveriesFragment.newInstance(), true, 2));
    }
}
