package com.example.bobyk.np.presenters.authorization.additionalData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.models.authorization.User;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.authorization.additionalData.AdditionalDataView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by bobyk on 6/4/17.
 */

public class AdditionalDataPresenter implements IAdditionalDataPresenter {

    private AdditionalDataView mView;
    private Activity mActivity;
    private Fragment mFragment;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;
    private String downloadUrl;

    public AdditionalDataPresenter(Activity activity, AdditionalDataView view, Fragment fragment) {
        mActivity = activity;
        mView = view;
        mFragment = fragment;
        init();
    }

    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference().child(mUser.getUid() + ".jpg");
    }

    @Override
    public void signUp(String firstname, String surname, String middlename) {
        User user = new User(mUser.getEmail(), firstname, surname, middlename,
                "+380664171432", downloadUrl, "user");

        mDatabase.child("users").child(mUser.getUid()).setValue(user)
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mView.onSuccessSignUp();
                        } else {
                            mView.onFailedSignUp(task.getException().getMessage());
                        }
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
