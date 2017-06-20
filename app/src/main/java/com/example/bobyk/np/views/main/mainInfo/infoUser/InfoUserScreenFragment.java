package com.example.bobyk.np.views.main.mainInfo.infoUser;

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
 * Created by bobyk on 6/11/17.
 */

public class InfoUserScreenFragment extends Fragment {

    @Bind(R.id.iv_deliveries)
    ImageView mDeliveriesImageView;
    @Bind(R.id.iv_track)
    ImageView mTrackImageView;

    private DisplayImageOptions mOptions;
    private ImageLoader mImageLoader;

    public static InfoUserScreenFragment newInstance() {
        Bundle args = new Bundle();
        InfoUserScreenFragment fragment = new InfoUserScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_user_screen, null);

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
        mImageLoader.displayImage("drawable://" + R.drawable.del, mDeliveriesImageView, mOptions);
        mImageLoader.displayImage("drawable://" + R.drawable.ic_track, mTrackImageView, mOptions);
    }

    @OnClick(R.id.rl_my_deliveries)
    public void onDeliveriesClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(DeliveriesFragment.newInstance("My deliveries"), true, 2));
    }


    @OnClick(R.id.rl_track)
    public void onTrackClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(TrackingFragment.newInstance(), true, 2));
    }

}
