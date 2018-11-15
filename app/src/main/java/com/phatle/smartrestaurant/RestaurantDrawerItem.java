package com.phatle.smartrestaurant;

import java.io.Serializable;
import java.util.List;

public class RestaurantDrawerItem implements Serializable{
    int imgRes;
    float overallRating;
    String name;
    String type;
    boolean status;
    int distance;
    String location;
    int priceRating;
    List<MenuItem> menu;
    List<CommentItem> comment;

    public RestaurantDrawerItem(int imgRes, float overallRating, String name, String type, boolean status, int distance, String location, int priceRating, List<MenuItem> menu, List<CommentItem> comment) {
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

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
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
}
