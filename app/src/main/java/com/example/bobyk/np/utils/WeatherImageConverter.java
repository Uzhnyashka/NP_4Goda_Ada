package com.example.bobyk.np.utils;

import com.example.bobyk.np.global.MyApplication;

import java.util.ArrayList;

/**
 * Created by bobyk on 3/25/17.
 */

public class WeatherImageConverter {

    private static ArrayList<String> getFullResNameList(ArrayList<String> idNamesList) {
        ArrayList<String> result = new ArrayList<>();
        for (String idName : idNamesList) {
            result.add("drawable://" +
                    MyApplication.getAppContext().getResources()
                            .getIdentifier(idName, "drawable", MyApplication.getAppContext().getPackageName()));
        }
        return result;
    }

    public static ArrayList<String> imagesFromRes(ArrayList<String> images) {
        return getFullResNameList(images);
    }
}

