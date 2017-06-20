package com.example.bobyk.np.views.main.profile.profilePage;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventMainChangeFragment;
import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.models.authorization.BaseAuthModel;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.authorization.Admin;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.presenters.main.profile.profilePage.ProfilePagePresenter;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.profile.settings.SettingsFragment;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

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
    @Bind(R.id.crop_image_view)
    CropImageView cropImageView;
    @Bind(R.id.root_layout)
    RelativeLayout rootLayout;
    @Bind(R.id.rl_profile_content)
    RelativeLayout profileContentRelativeLayout;
    @Bind(R.id.rl_crop_content)
    RelativeLayout cropContent;
    @Bind(R.id.tv_email)
    TextView mEmailTextView;

    private ProfilePagePresenter mPresenter;
    private DisplayImageOptions mOptions;
    private BaseAuthModel mUser;
    private Driver mDriver;
    private Admin mAdmin;
    private Bitmap mUserPhotoBitmap;
    private Dialog dialog;

    public static ProfilePageFragment newInstance() {
        Bundle args = new Bundle();
        ProfilePageFragment fragment = new ProfilePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ProfilePagePresenter(getActivity(), this, this);
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
        showProgressDialog();
        mPresenter.initData();
    }

    @OnClick(R.id.btn_setting)
    public void onSettingsClick() {
        EventBus.getDefault().post(new EventMainChangeFragment(SettingsFragment.newInstance(mUser), true, 3));
    }

    @OnClick(R.id.iv_profile_image)
    public void onChooseImageClick() {
        if (checkReadPerm()) {
            mPresenter.chooseProfileImage();
        } else {
            checkPermission();
        }
    }

    @OnClick(R.id.btn_crop_image)
    public void onCropImageClick() {
        cropImageView.getCroppedImageAsync();
    }

    @Override
    public void setFullName(String name) {
        System.out.println("EEE " + name);
        mFullNameTextView.setText(name);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        System.out.println("EEE " + phoneNumber);
        mPhoneNumberTextView.setText(phoneNumber);
    }

    @Override
    public void setPhoto(String photoUrl) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(photoUrl, mProfileImageView, mOptions);
    }

    @Override
    public void setUserData(BaseAuthModel user) {
        hideProgressDialog();
        mUser = user;
    }

    @Override
    public void setEmail(String email) {
        mEmailTextView.setText(email);
    }


    @Override
    public void setProfileImage(File file) {
        if (file.exists()) {
            cropImageView(file);
        }
    }

    @Override
    public void onSuccessLoadPhotoToDatabase() {

    }

    @Override
    public void onFailedLoadPhotoToDatabase() {

    }

    @Override
    public void error() {
        hideProgressDialog();
        Utils.showToastMessage(getActivity(), "Error");
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

    private boolean checkReadPerm() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.getLoadFile(requestCode, resultCode, data);
    }



    private void cropImageView(final File file) {
        rootLayout.setBackgroundColor(getResources().getColor(R.color.bb_darkBackgroundColor));
        cropContent.setVisibility(View.VISIBLE);
        profileContentRelativeLayout.setVisibility(View.INVISIBLE);
        cropImageView.setImageUriAsync(Uri.fromFile(file));
        cropImageView.setCropShape(CropImageView.CropShape.OVAL);
        cropImageView.setCropRect(new Rect(100, 100, 300, 300));
        cropImageView.isFixAspectRatio();
        cropImageView.setAspectRatio(1, 1);
        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                rootLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                cropContent.setVisibility(View.INVISIBLE);
                mProfileImageView.setImageBitmap(result.getBitmap());
                mUserPhotoBitmap = result.getBitmap();
                mPresenter.uploadPhoto(mUserPhotoBitmap);
                profileContentRelativeLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.READ_EXTERNAL_STORAGE_REQUEST);

            return;
        }
    }


    private void showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress_bar, null);
        builder.setView(dialogView);

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        Display display = getActivity().getWindowManager().getDefaultDisplay();

        Window window = dialog.getWindow();
        window.setLayout(display.getWidth() - 100, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void hideProgressDialog() {
        dialog.cancel();
    }


}

