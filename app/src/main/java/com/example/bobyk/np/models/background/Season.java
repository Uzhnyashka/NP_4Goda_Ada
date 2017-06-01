package com.example.bobyk.np.models.background;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bobyk on 3/25/17.
 */

public class Season {
    @SerializedName("success")
    @Expose
    boolean isSuccess;

    @SerializedName("background")
    @Expose
    Background background;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }
}
