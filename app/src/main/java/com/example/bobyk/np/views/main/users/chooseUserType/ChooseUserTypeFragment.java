package com.example.bobyk.np.views.main.users.chooseUserType;

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
import com.example.bobyk.np.views.main.users.showDrivers.ShowDriversFragment;
import com.example.bobyk.np.views.main.users.showUsers.ShowUsersFragment;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by bobyk on 6/10/17.
 */

public class ChooseUserTypeFragment extends Fragment implements ChooseUserTypeView {

    @Bind(R.id.iv_users)
    ImageView mUsersImageView;
    @Bind(R.id.iv_drivers)
    ImageView mDriversImageView;

    private DisplayImageOptions mOptions;
    private ImageLoader imageLoader;

    public static ChooseUserTypeFragment newInstance() {
        Bundle args = new Bundle();
        ChooseUserTypeFragment fragment = new ChooseUserTypeFragment();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_choose_user_type, null);

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


    private void init() {
        imageLoader.displayImage("drawable://" + R.drawable.user_icon, mUsersImageView, mOptions);
        imageLoader.displayImage("drawable://" + R.drawable.driver_icon, mDriversImageView, mOptions);
    }

    @OnClick(R.id.iv_users)
    public void onUsersImageClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(ShowUsersFragment.newInstance(), false, 4));
    }

    @OnClick(R.id.tv_users)
    public void onUsersTextClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(ShowUsersFragment.newInstance(), false, 4));
    }

    @OnClick(R.id.tv_drivers)
    public void onDriversImageClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(ShowDriversFragment.newInstance(), false, 4));
    }

    @OnClick(R.id.tv_drivers)
    public void onDriversTextClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(ShowDriversFragment.newInstance(), false, 4));
    }
}
