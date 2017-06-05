package com.example.bobyk.np.views.authorization.additionalData;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.bobyk.np.R;
import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.listeners.FileSavedListener;
import com.example.bobyk.np.presenters.authorization.additionalData.AdditionalDataPresenter;
import com.example.bobyk.np.utils.SaveBitmapToFileTask;
import com.example.bobyk.np.utils.Utils;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bobyk on 6/4/17.
 */

public class AdditionalDataFragment extends Fragment implements AdditionalDataView {

    @Bind(R.id.et_firstname)
    EditText mFirstnameEditText;
    @Bind(R.id.et_surname)
    EditText mSurnameEditText;
    @Bind(R.id.et_middlename)
    EditText mMiddlenameEditText;
    @Bind(R.id.crop_image_view)
    CropImageView cropImageView;
    @Bind(R.id.rl_create_username_content)
    RelativeLayout createUsernameContent;
    @Bind(R.id.rl_crop_content)
    RelativeLayout cropContent;
    @Bind(R.id.rl_create_username)
    RelativeLayout rootLayout;
    @Bind(R.id.iv_choose_image)
    CircleImageView chooseImageView;

    private AdditionalDataPresenter mPresenter;
    private boolean mProfileImageChoosen = false;
    private String imageFilePath;
    private Bitmap mUserPhotoBitmap = null;

    public static AdditionalDataFragment newInstance() {
        Bundle args = new Bundle();
        AdditionalDataFragment fragment = new AdditionalDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AdditionalDataPresenter(getActivity(), this, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_additional_data, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_sign_up)
    public void onSignUpClick() {
        if (mUserPhotoBitmap != null) {
            mPresenter.uploadPhoto(mUserPhotoBitmap);
        }
        else {
            mPresenter.signUp(mFirstnameEditText.getText().toString(), mSurnameEditText.getText().toString(),
                    mMiddlenameEditText.getText().toString());
        }
    }

    @OnClick(R.id.iv_choose_image)
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
    public void onSuccessSignUp() {
        getActivity().finish();
    }

    @Override
    public void onFailedSignUp(String message) {
        Utils.showToastMessage(getActivity(), message);
    }

    @Override
    public void onSuccessLoadPhotoToDatabase() {
        mPresenter.signUp(mFirstnameEditText.getText().toString(), mSurnameEditText.getText().toString(),
                mMiddlenameEditText.getText().toString());
    }

    @Override
    public void onFailedLoadPhotoToDatabase() {
        mPresenter.signUp(mFirstnameEditText.getText().toString(), mSurnameEditText.getText().toString(),
                mMiddlenameEditText.getText().toString());
    }

    @Override
    public void onSuccessLoadPhotoFromDevice() {

    }

    @Override
    public void onFailedLoadPhotoFromDevice() {

    }

    @Override
    public void setProfileImage(File file) {
        if (file.exists()) {
            cropImageView(file);
            mProfileImageChoosen = true;
        }
    }

    @Override
    public void error() {
        Utils.showToastMessage(getActivity(), "Error upload photo");
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
        createUsernameContent.setVisibility(View.INVISIBLE);
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
                chooseImageView.setImageBitmap(result.getBitmap());
                imageFilePath = Utils.getFileFromTempDir(getActivity(), Utils.generateFileNameDate("IMG_", "jpg"));
//                new SaveBitmapToFileTask(false, imageFilePath, new FileSavedListener() {
//                    @Override
//                    public void onSuccess(String fileName) {
//                        mUserIconFile = new File(fileName);
//                    }
//
//                    @Override
//                    public void onFail(String error) {
//                        Log.d("TAG", "onFail: crop " + error);
//                    }
//                }).execute(result.getBitmap());
                mUserPhotoBitmap = result.getBitmap();

                createUsernameContent.setVisibility(View.VISIBLE);
            }
        });
    }
}
