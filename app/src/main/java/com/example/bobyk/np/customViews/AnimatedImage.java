package com.example.bobyk.np.customViews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by bobyk on 3/25/17.
 */

public class AnimatedImage {

    private  static final DisplayImageOptions DISPLAYED_OPTIONS = new DisplayImageOptions.Builder()
            .cacheInMemory(false)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .considerExifParams(true)
            .build();

    private static final String TAG = AnimatedImage.class.getSimpleName();
    private static final long ALPHA_ANIMATION_DURATION = 2000;

    private String mImagePath;
    private Bitmap mImageBitmap;
    private int mViewWidth, mViewHeight;
    private float mScale;
    private Matrix mMatrix = new Matrix();
    private Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private Paint mForegroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    private int mScaledImageWidth, mScaledImageHeight;

    private int mImageOffsetX = 0;
    private OnPixelsLeftOnScreenListener mOnPixelsLeftOnScreenListener;
    private boolean mIsAlphaAnimationStarted;
    private boolean mTransitionAnimationStarted;
    private OnImageLoadingListener mOnImageLoadingListener;
    private int mAnimationSpeed = 1;
    private AnimationEndListener mOnAlphaAnimationListener;


    public void initWithImagePath(String imagePath, String foregroundColor) {
        Log.d(TAG, "initWithImagePath() called with: imagePath = [" + imagePath + "], foregroundColor = [" + foregroundColor + "]");
        release();
        mImagePath = imagePath;
        updateColorFilter(foregroundColor);
        loadBitmap();
    }

    public void setOnPixelsLeftOnScreenListener(OnPixelsLeftOnScreenListener listener) {
        mOnPixelsLeftOnScreenListener = listener;
    }

    public void setOnImageLoadingListener(OnImageLoadingListener listener) {
        mOnImageLoadingListener = listener;
    }


    public void setViewSize(int width, int height) {
        mViewWidth = width;
        mViewHeight = height;
        calculateScale();
    }

    public void onDrawImage(Canvas canvas) {
        updateAnimatedValue();
        updateMatrix();

        if (canDraw()) {
            canvas.drawBitmap(mImageBitmap, mMatrix, mBitmapPaint);
        }
    }

    public void onDrawForeground(Canvas canvas, Bitmap foregroundBitmap, Matrix foregroundMatrix) {
        if (canDraw()) {
            mForegroundPaint.setAlpha((int) (mBitmapPaint.getAlpha()));
            canvas.drawBitmap(foregroundBitmap, foregroundMatrix, mForegroundPaint);
        }
    }


    public  void startTransitionAnimation(boolean restart) {
        mTransitionAnimationStarted = true;
        if (restart) mImageOffsetX = 0;
    }

    public void pauseTransitionAnimation() {
        mTransitionAnimationStarted = false;
    }

    public void startAlphaInAnimation () {
        startAlphaAnimation(0, 255);
    }

    public void startAlphaOutAnimation () {
        startAlphaAnimation(255, 0);
    }

    public void release() {
        pauseTransitionAnimation();
        if (mImageBitmap != null) {
            mImageBitmap.recycle();
            mImageBitmap = null;
        }
    }





    private void updateAnimatedValue() {
        if (mTransitionAnimationStarted) {
            mImageOffsetX += mAnimationSpeed;
            if (mOnPixelsLeftOnScreenListener != null) {
                mOnPixelsLeftOnScreenListener.onPixelsLeftOnScreen(this, mScaledImageWidth - mImageOffsetX);
            }
        }
    }

    private void calculateScale() {
        if (mImageBitmap == null) return;
        mScaledImageHeight = mViewHeight;
        mScaledImageWidth = mImageBitmap.getWidth() * mScaledImageHeight / mImageBitmap.getHeight();
        Log.d(TAG, "calculateScale: original w: " + mImageBitmap.getWidth() + " h: " + mImageBitmap.getHeight());
        Log.d(TAG, "calculateScale: scaled w: " + mScaledImageWidth + " h: " + mScaledImageHeight);
        mScale = mScaledImageWidth / (float) mImageBitmap.getWidth();
        Log.d(TAG, "calculateScale: scale value  " + mScale);
        updateMatrix();
    }

    private void updateMatrix() {
        mMatrix.reset();
        mMatrix.setScale(mScale, mScale);
        mMatrix.postTranslate(-mImageOffsetX, 0);
    }

    private void loadBitmap() {
        Log.d(TAG, "loadBitmap: mImagePath: " + mImagePath);
        ImageLoader.getInstance().loadImage(mImagePath, DISPLAYED_OPTIONS, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (mOnImageLoadingListener != null && TextUtils.equals(imageUri, mImagePath)) {
                    mOnImageLoadingListener.onLoadingStarted(AnimatedImage.this, imageUri);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (mOnImageLoadingListener != null && TextUtils.equals(imageUri, mImagePath)) {
                    mOnImageLoadingListener.onLoadingFailed(AnimatedImage.this, imageUri, failReason.toString());
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                handleLoadedBitmap(loadedImage);
                if (mOnImageLoadingListener != null && TextUtils.equals(imageUri, mImagePath)) {
                    mOnImageLoadingListener.onLoadingComplete(AnimatedImage.this, imageUri, loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if (mOnImageLoadingListener != null && TextUtils.equals(imageUri, mImagePath)) {
                    mOnImageLoadingListener.onLoadingCancelled(AnimatedImage.this, imageUri);
                }
            }
        });
    }

    private void handleLoadedBitmap(Bitmap loadedImage) {
        mImageBitmap = loadedImage;
        calculateScale();
    }

    private void startAlphaAnimation(final int startValue, final int endValue) {
        if (!mIsAlphaAnimationStarted) {
            mIsAlphaAnimationStarted = true;
            Log.d(TAG, "startAlphaAnimation: startValue: " + startValue + " endValue: " + endValue );
            ObjectAnimator alphaAnimation = ObjectAnimator.ofInt(mBitmapPaint, "alpha", startValue, endValue);
            alphaAnimation.setDuration(ALPHA_ANIMATION_DURATION);
            alphaAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mIsAlphaAnimationStarted = false;
                    Log.d(TAG, "startAlphaAnimation: startValue: " + startValue + " endValue: " + endValue  + " FINISHED");
                    if (mOnAlphaAnimationListener != null) {
                        mOnAlphaAnimationListener.onAnimationEnd();
                    }
                }
            });
            alphaAnimation.start();
        }
    }


    private boolean canDraw() {
        return mImageBitmap != null && !mImageBitmap.isRecycled();
    }

    public void setAnimationSpeed(int animationSpeed) {
        mAnimationSpeed = animationSpeed;
    }

    public void setOnAlphaAnimationListener(AnimationEndListener onAlphaAnimationListener) {
        mOnAlphaAnimationListener = onAlphaAnimationListener;
    }


    public interface OnPixelsLeftOnScreenListener {
        void onPixelsLeftOnScreen(AnimatedImage animatedImage, long pixelsLeft);
    }

    public interface OnImageLoadingListener {
        void onLoadingStarted(AnimatedImage animatedImage, String imageUri);

        void onLoadingFailed(AnimatedImage animatedImage, String imageUri, String failReason);

        void onLoadingComplete(AnimatedImage animatedImage, String imageUri, Bitmap loadedImage);

        void onLoadingCancelled(AnimatedImage animatedImage, String imageUri);
    }

    public static class SimpleImageLoadingListener implements OnImageLoadingListener {

        @Override
        public void onLoadingStarted(AnimatedImage animatedImage, String imageUri) {

        }

        @Override
        public void onLoadingFailed(AnimatedImage animatedImage, String imageUri, String failReason) {

        }

        @Override
        public void onLoadingComplete(AnimatedImage animatedImage, String imageUri, Bitmap loadedImage) {

        }

        @Override
        public void onLoadingCancelled(AnimatedImage animatedImage, String imageUri) {

        }
    }

    public interface AnimationEndListener {
        void onAnimationEnd();
    }

    private void updateColorFilter(String color) {
        updateColorFilter(Color.parseColor(color));
    }

    private void updateColorFilter(int intColor) {
        float r = Color.red(intColor) / 255f;
        float g = Color.green(intColor) / 255f;
        float b = Color.blue(intColor) / 255f;
        float a = Color.alpha(intColor) / 255f;
        Log.d(TAG, "updateColorFilter: r: " +r + " g: " + g +  " b: " + b + " a: " + a);
        ColorMatrix cm = new ColorMatrix(new float[] {
                r, 0, 0, 0, 0,
                0, g, 0, 0, 0,
                0, 0, b, 0, 0,
                0, 0, 0, a, 0
        });
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(cm);
        mForegroundPaint.setColorFilter(filter);
    }
}
