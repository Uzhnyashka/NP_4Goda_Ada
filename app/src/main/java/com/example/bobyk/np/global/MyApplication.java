package com.example.bobyk.np.global;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by bobyk on 3/25/17.
 */

public class MyApplication extends Application {

    private static MyApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;
        initImageLoader();

    }

    private void initImageLoader() {
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(100 * 1024 * 1024)
                .threadPoolSize(10)
                .build();

        ImageLoader.getInstance().init(imageLoaderConfig);
    }

    public static Context getAppContext() {
        return mApp;
    }
}
