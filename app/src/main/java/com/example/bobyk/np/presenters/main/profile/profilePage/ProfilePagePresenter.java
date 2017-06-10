package com.example.bobyk.np.presenters.main.profile.profilePage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.models.authorization.Admin;
import com.example.bobyk.np.models.authorization.Driver;
import com.example.bobyk.np.utils.SPManager;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.main.profile.profilePage.ProfilePageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by bobyk on 6/7/17.
 */

public class ProfilePagePresenter implements IProfilePagePresenter {

    private Activity mActivity;
    private ProfilePageView mView;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private StorageReference mStorageReference;
    private User user;
    private Driver driver;
    private Admin admin;
    private Fragment mFragment;
    private String downloadUrl;
    private String mRole;

    public ProfilePagePresenter(Activity activity, ProfilePageView view, Fragment fragment) {
        mActivity = activity;
        mFragment = fragment;
        mView = view;
        init();
    }

    private void init() {
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference().child(mUser.getUid() + ".jpg");
        mRole = SPManager.loadUserLoginData(mActivity.getApplicationContext(), Constants.ROLE);
    }


    @Override
    public void initData() {
        if (mUser != null) {
            System.out.println("EEE not null " + mRole);
            switch (mRole) {
                case "Administrator":
                    initAdminData();
                    break;
                case "Driver":
                    initDriverData();
                    break;
                case "User":
                    initUserData();
                    break;

            }

        }
    }

    private void initUserData() {
        mDatabase.child("users").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    mView.setFullName(user.getSurname() + " " + user.getFirstName() + " " + user.getMiddleName());
                    mView.setPhoneNumber(user.getPhoneNumber());
                    mView.setPhoto(user.getPhotoUrl());
                    mView.setUserData(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initDriverData() {
        mDatabase.child("drivers").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                driver = dataSnapshot.getValue(Driver.class);
                if (driver != null) {
                    mView.setFullName(driver.getSurname() + " " + driver.getFirstName() + " " + driver.getMiddleName());
                    mView.setPhoneNumber(driver.getPhoneNumber());
                    mView.setPhoto(driver.getPhotoUrl());
                    mView.setUserData(driver);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initAdminData() {
        mDatabase.child("admins").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                admin = dataSnapshot.getValue(Admin.class);
                if (admin != null) {
                    mView.setFullName(admin.getSurname() + " " + admin.getFirstName() + " " + admin.getMiddleName());
                    mView.setPhoneNumber(admin.getPhoneNumber());
                    mView.setPhoto(admin.getPhotoUrl());
                    mView.setUserData(admin);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void uploadPhoto(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorageReference.putBytes(data);
        uploadTask
                .addOnSuccessListener(mActivity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        downloadUrl = taskSnapshot.getDownloadUrl().toString();
                        updateProfilePhoto();
                        mView.onSuccessLoadPhotoToDatabase();
                    }
                })
                .addOnFailureListener(mActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        downloadUrl = "";
                        mView.onFailedLoadPhotoToDatabase();
                    }
                });
    }

    private void updateProfilePhoto() {
        switch (mRole) {
            case "Administrator":
                updateAdminPhoto();
                break;
            case "Driver":
                updateDriverPhoto();
                break;
            case "User":
                updateUserPhoto();
                break;
        }
    }

    private void updateAdminPhoto() {
        mDatabase.child("admins").child(mUser.getUid()).child("photoUrl").setValue(downloadUrl);
    }

    private void updateDriverPhoto() {
        mDatabase.child("drivers").child(mUser.getUid()).child("photoUrl").setValue(downloadUrl);
    }

    private void updateUserPhoto() {
        mDatabase.child("users").child(mUser.getUid()).child("photoUrl").setValue(downloadUrl);
    }

    @Override
    public void chooseProfileImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        mFragment.startActivityForResult(intent, Constants.UPLOAD_IMAGE_PROFILE);
    }

    @Override
    public void getLoadFile(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.UPLOAD_IMAGE_PROFILE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                try {
                    uri = data.getData();
                    File mFile = new File(Utils.getPath(mActivity, uri));
//                    final InputStream imageStream = mContext.getContentResolver().openInputStream(uri);
//                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                File file = new File(getRealPathFromURI(uri));
//                    File file = new File(PathExtractor.getPath(mContext, uri));
                    mView.setProfileImage(mFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            mView.error();
        }
    }
}
