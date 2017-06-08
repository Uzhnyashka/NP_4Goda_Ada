package com.example.bobyk.np.views.main.profile.profilePage;

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
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.presenters.main.profile.profilePage.ProfilePagePresenter;
import com.example.bobyk.np.views.main.profile.settings.SettingsFragment;
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
 * Created by bobyk on 6/6/17.
 */

public class ProfilePageFragment extends Fragment implements ProfilePageView {

    @Bind(R.id.iv_profile_image)
    CircleImageView mProfileImageView;
    @Bind(R.id.tv_full_name)
    TextView mFullNameTextView;
    @Bind(R.id.tv_phone_number)
    TextView mPhoneNumberTextView;

    private ProfilePagePresenter mPresenter;
    private DisplayImageOptions mOptions;
    private User mUser;

    public static ProfilePageFragment newInstance() {
        Bundle args = new Bundle();
        ProfilePageFragment fragment = new ProfilePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ProfilePagePresenter(getActivity(), this);
        configImageLoader();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        mPresenter.initUserData();
    }

    @OnClick(R.id.btn_setting)
    public void onSettingsClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(SettingsFragment.newInstance(mUser), true, 3));
    }

    @Override
    public void setFullName(String name) {
        mFullNameTextView.setText(name);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumberTextView.setText(phoneNumber);
    }

    @Override
    public void setPhoto(String photoUrl) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(photoUrl, mProfileImageView, mOptions);
    }

    @Override
    public void setUser(User user) {
        mUser = user;
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

