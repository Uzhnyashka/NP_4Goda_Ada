package com.example.bobyk.np.customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.bobyk.np.R;

/**
 * Created by bobyk on 3/25/17.
 */

public class SlideView extends View {
    private static final int MSG_INVALIDATE = 0;
    private static final long DEFAULT_UPDATE_DELAY = 1000 / 200;
    private final int DEFAULT_ANIMATION_SPEED = 6; // pixels per frame

    private static final String TAG = "SlideView";

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INVALIDATE:
                    invalidate();
                    sendEmptyMessageDelayed(MSG_INVALIDATE, DEFAULT_UPDATE_DELAY);
                    break;
            }

        }
    };
    private boolean mAnimationStarted;

    private AnimatedImage mFirstAnimatedImage;
    private AnimatedImage mSecondAnimatedImage;

    private int mViewWidth = 1080;
    private int mViewHeight = 1920;
    private int mCurrentImageIndex = -1;

    private AnimatedImage.OnPixelsLeftOnScreenListener mPixelsOnScreenListener = new AnimatedImage.OnPixelsLeftOnScreenListener() {
        @Override
        public void onPixelsLeftOnScreen(AnimatedImage animatedImage, long pixelsLeft) {
            if (equalsWithEpsilon(pixelsLeft, (long) (1.25f * mViewWidth), DEFAULT_ANIMATION_SPEED)) {
                Log.d(TAG, "onPixelsLeftOnScreen: REQUEST NEXT IMAGE pixelsLeft: " + pixelsLeft + " animatedImage: " + animatedImage);
                requestNextImage();
            }
        }
    };

    private Bitmap mForegroundBitmap;
    private Matrix mForegroundMatrix;
    private SeasonsBackgroundData mBackgroundData;

    private void requestNextImage() {
        updateCurrentImage();
        mSecondAnimatedImage.initWithImagePath(getNextImageURL(), getNextImageForegroundColor());
    }

    private AnimatedImage.SimpleImageLoadingListener mImageLoadingListener = new AnimatedImage.SimpleImageLoadingListener() {
        @Override
        public void onLoadingComplete(AnimatedImage animatedImage, String imageUri, Bitmap loadedImage) {
            animatedImage.startTransitionAnimation(true);
            if (animatedImage != mFirstAnimatedImage) {
                animatedImage.startAlphaInAnimation();
                mFirstAnimatedImage.startAlphaOutAnimation();
            }
        }
    };

    public SlideView(Context context) {
        super(context);
        init();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mFirstAnimatedImage = new AnimatedImage();
        mFirstAnimatedImage.setAnimationSpeed(DEFAULT_ANIMATION_SPEED);
        mFirstAnimatedImage.setOnPixelsLeftOnScreenListener(mPixelsOnScreenListener);
        mFirstAnimatedImage.setOnImageLoadingListener(mImageLoadingListener);

        mSecondAnimatedImage = new AnimatedImage();
        mSecondAnimatedImage.setAnimationSpeed(DEFAULT_ANIMATION_SPEED);
        mSecondAnimatedImage.setOnPixelsLeftOnScreenListener(mPixelsOnScreenListener);
        mSecondAnimatedImage.setOnImageLoadingListener(mImageLoadingListener);
        mSecondAnimatedImage.setOnAlphaAnimationListener(new AnimatedImage.AnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                Log.d(TAG, "onAnimationEnd: ");
                AnimatedImage tmp = mFirstAnimatedImage;
                mFirstAnimatedImage = mSecondAnimatedImage;
                mSecondAnimatedImage = tmp;
                mSecondAnimatedImage.release();
            }
        });

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mViewWidth = getWidth();
        mViewHeight = getHeight();
        Log.d(TAG, "onLayout: w: " + mViewWidth + " h : " + mViewHeight);

        mFirstAnimatedImage.setViewSize(mViewWidth, mViewHeight);
        mSecondAnimatedImage.setViewSize(mViewWidth, mViewHeight);
        initForeground();
    }


    public void startAnimation(boolean restart) {
        Log.d(TAG, "startAnimation: ");
        mAnimationStarted = true;
        mFirstAnimatedImage.startTransitionAnimation(restart);
        mSecondAnimatedImage.startTransitionAnimation(restart);
        mHandler.removeMessages(MSG_INVALIDATE);
        mHandler.sendEmptyMessage(MSG_INVALIDATE);
    }


    public void pauseAnimation() {
        Log.d(TAG, "pauseAnimation: ");
        mAnimationStarted = false;

        mFirstAnimatedImage.pauseTransitionAnimation();
        mSecondAnimatedImage.pauseTransitionAnimation();

        mHandler.removeMessages(MSG_INVALIDATE);
    }


    public void setAnimatedImages(SeasonsBackgroundData background) {
        mBackgroundData = background;
        updateCurrentImage();
        mFirstAnimatedImage.initWithImagePath(getNextImageURL(), getNextImageForegroundColor());

    }

    private void updateCurrentImage() {
        mCurrentImageIndex  = (mCurrentImageIndex + 1) % mBackgroundData.picturesCount();
        if (!mBackgroundData.hasImage(mCurrentImageIndex)) {
            Log.d(TAG, "updateCurrentImage: NO IMAGE FOUND FOR INDEX: " + mCurrentImageIndex);
            // TODO: 14/01/17 can this be fixed?
            updateCurrentImage();
        }

    }

    private String getNextImageURL() {
        return mBackgroundData.getImage(mCurrentImageIndex);
    }

    private String getNextImageForegroundColor() {
        return mBackgroundData.getColor(mCurrentImageIndex);
    }

    private boolean equalsWithEpsilon(long value1, long value2, long epsilon) {
        return Math.abs(value1 - value2) <= epsilon / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Log.d(TAG, "onDrawImage: ");
        mFirstAnimatedImage.onDrawImage(canvas);
        mFirstAnimatedImage.onDrawForeground(canvas, mForegroundBitmap, mForegroundMatrix);
        mSecondAnimatedImage.onDrawImage(canvas);
        mSecondAnimatedImage.onDrawForeground(canvas, mForegroundBitmap, mForegroundMatrix);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pauseAnimation();
        mFirstAnimatedImage.release();
        mFirstAnimatedImage.release();
        if (mForegroundBitmap != null) {
            mForegroundBitmap.recycle();
            mForegroundBitmap = null;
        }
    }


    private void initForeground() {
        if (mForegroundBitmap == null) {
            mForegroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_foreground_mask);
        }

        mForegroundMatrix = new Matrix();
        mForegroundMatrix.reset();
        mForegroundMatrix.setScale(
                mViewWidth / (float) mForegroundBitmap.getWidth(),
                mViewHeight / (float) mForegroundBitmap.getHeight());
    }

}
