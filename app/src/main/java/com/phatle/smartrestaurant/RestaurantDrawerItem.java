package com.phatle.smartrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RestaurantDrawerItem implements Serializable{
    @SerializedName("resImg")
    @Expose
    String imgRes;

    @SerializedName("resPoint")
    @Expose
    float overallRating;

    @SerializedName("resName")
    @Expose
    String name;

    @SerializedName("resType")
    @Expose
    String type;

    @SerializedName("resStatus")
    @Expose
    boolean status;

    @SerializedName("resDistance")
    @Expose
    int distance;

    @SerializedName("resLocation")
    @Expose
    String location;

    @SerializedName("resPriceRating")
    @Expose
    int priceRating;

    @SerializedName("resLat")
    @Expose
    double lat;

    @SerializedName("resLng")
    @Expose
    double lng;

    @SerializedName("resMenu")
    @Expose
    List<MenuItem> menu;

    @SerializedName("resComment")
    @Expose
    List<CommentItem> comment;

    public RestaurantDrawerItem(String imgRes, float overallRating, String name, String type, boolean status, int distance, String location, int priceRating, List<MenuItem> menu, List<CommentItem> comment) {
        this.imgRes = imgRes;
        this.overallRating = overallRating;
        this.name = name;
        this.type = type;
        this.status = status;
        this.distance = distance;
        this.location = location;
        this.priceRating = priceRating;
        this.menu = menu;
        this.comment = comment;
    }

    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
        this.imgRes = imgRes;
    }

    public float getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(float overallRating) {
        this.overallRating = overallRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(int priceRating) {
        this.priceRating = priceRating;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public List<CommentItem> getComment() {
        return comment;
    }

    public void setComment(List<CommentItem> comment) {
        this.comment = comment;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
