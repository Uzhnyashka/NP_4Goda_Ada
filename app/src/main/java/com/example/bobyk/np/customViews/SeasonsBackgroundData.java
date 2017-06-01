package com.example.bobyk.np.customViews;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by bobyk on 3/25/17.
 */

public class SeasonsBackgroundData {
    private static final String TAG = "SlideSeasonBackground";

    public static final String DEFAULT_OVERLAY_COLOR = "#00000000";

    private ArrayList<String> mColors = new ArrayList<>();
    private ArrayList<String> mImages  = new ArrayList<>();

    public SeasonsBackgroundData(ArrayList<String> colors, ArrayList<String> images) {
        this.mColors = colors;
        this.mImages = images;
    }

    @Override
    public String toString() {
        return "SeasonsBackgroundData images: " + mImages.size() + " colors: " + mColors;
    }

    public int picturesCount() {
        return mImages.size();
    }

    public String getImage(int index) {

        return mImages.get(index);
    }

    public String getColor(int index) {

        if (index < mColors.size()) {
            String color = mColors.get(index);

            return TextUtils.isEmpty(color) ? DEFAULT_OVERLAY_COLOR : color;
        } else {
            return DEFAULT_OVERLAY_COLOR;
        }
    }

    public boolean hasImage(int index) {

        if (index < mImages.size()) {
            String image = mImages.get(index);
            return !TextUtils.isEmpty(image);
        }
        return false;
    }
}

