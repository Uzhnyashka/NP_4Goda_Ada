package com.example.bobyk.np.models.background;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by bobyk on 3/25/17.
 */

public class Background {

    @SerializedName("_id")
    @Expose
    String id;

    @SerializedName("name")
    @Expose
    String season;

    @SerializedName("colors")
    @Expose
    ArrayList<String> colors;

    @SerializedName("images")
    @Expose
    ArrayList<ArrayList<String>> images = new ArrayList<>();

    @SerializedName("imagesIOS")
    @Expose
    ArrayList<String> imagesIos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public ArrayList<ArrayList<String>> getImages() {
        return images;
    }

    public void setImages(ArrayList<ArrayList<String>> seasonImages) {
        this.images = seasonImages;
    }

    public ArrayList<String> getImagesIos() {
        return imagesIos;
    }

    public void setImagesIos(ArrayList<String> imagesIos) {
        this.imagesIos = imagesIos;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "Background{" +
                "id='" + id + '\'' +
                ", season='" + season + '\'' +
                ", colors='" + colors + '\'' +
                ", images=" + images +
                ", imagesIos=" + imagesIos +
                '}';
    }
}

