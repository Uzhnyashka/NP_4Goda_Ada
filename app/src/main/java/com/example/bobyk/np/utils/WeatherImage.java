package com.example.bobyk.np.utils;

import java.util.ArrayList;

/**
 * Created by bobyk on 3/25/17.
 */

public class WeatherImage {
    private ArrayList<String> imgParts = new ArrayList<>();
    private int mStartGlobalIndex = 0;

    public int getSize(){
        return imgParts.size();
    }

    public void setStartGlobalIndex(int startGlobalIndex) {
        mStartGlobalIndex = startGlobalIndex;
    }

    public int getStartGlobalIndex() {
        return mStartGlobalIndex;
    }

    public int getEndGlobalIndex() {
        return mStartGlobalIndex + imgParts.size() - 1;
    }

    public int getGlobalIndex(int localIndex) {
        if (localIndex > imgParts.size()) {
            throw new IllegalArgumentException("Local index > than ImagePart count " + localIndex + " > " + imgParts.size());
        }
        return mStartGlobalIndex + localIndex;
    }

    public int getLocalIndex(int globalIndex) {
        return globalIndex - getStartGlobalIndex();
    }

    public boolean isCurrentGlobalIndex(int globalIndex) {
        return (getStartGlobalIndex() <= globalIndex && globalIndex <= getEndGlobalIndex());
    }

    public String getImage(int globalIndex) {
        return imgParts.get(getLocalIndex(globalIndex));
    }

    public ArrayList<String> getImgParts() {
        return imgParts;
    }

    public void setImgParts(ArrayList<String> imgParts) {
        this.imgParts = imgParts;
    }
    @Override
    public String toString() {
        return "WeatherImage " +
                "startIndex=" + mStartGlobalIndex +
                " parts count=" + imgParts.size() +
                " parts: " + imgParts;
    }
}
