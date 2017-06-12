package com.example.bobyk.np.views.main.users.userInfo;

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
import com.example.bobyk.np.views.main.users.userDeliveries.UserDeliveriesFragment;
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
 * Created by bobyk on 6/11/17.
 */

public class UserInfoFragment extends Fragment {

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
    @Bind(R.id.iv_user)
    CircleImageView mUserImageView;

    private DisplayImageOptions mOptions;
    private ImageLoader imageLoader;
    private User mUser;

    public static UserInfoFragment newInstance(User user) {
        Bundle args = new Bundle();
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        fragment.setUser(user);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configImageLoader();
        imageLoader = ImageLoader.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, null);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        if (mUser != null) {
            if (mUser.getPhotoUrl() != null && !mUser.getPhotoUrl().equals("")) {
                imageLoader.displayImage(mUser.getPhotoUrl(), mUserImageView, mOptions);
            } else {
                imageLoader.displayImage("drawable://" + R.drawable.user_icon, mUserImageView, mOptions);
            }
            mFirstNameTextView.setText(mUser.getFirstName());
            mSurnameTextView.setText(mUser.getSurname());
            mMiddleNameTextView.setText(mUser.getMiddleName());
            mEmailTextView.setText(mUser.getEmail());
            mPhoneNumberTextView.setText(mUser.getPhoneNumber());
        }
    }

    private void setUser(User user) {
        mUser = user;
    }

    @OnClick(R.id.btn_back)
    public void onBackCLick() {
        getActivity().onBackPressed();
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

    @OnClick(R.id.btn_active_deliveries)
    public void onActiveDeliveriesClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(UserDeliveriesFragment.newInstance(mUser), true, 4));
    }
}
