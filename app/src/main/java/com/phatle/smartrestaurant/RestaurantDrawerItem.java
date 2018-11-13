package com.phatle.smartrestaurant;

public class RestaurantDrawerItem {
    int imgRes;
    float overallRating;
    String name;
    String type;
    boolean status;
    String distance;
    String location;
    int priceRating;

    public RestaurantDrawerItem(int imgRes, float overallRating, String name, String type, boolean status, String distance, String location, int priceRating) {
        this.imgRes = imgRes;
        this.overallRating = overallRating;
        this.name = name;
        this.type = type;
        this.status = status;
        this.distance = distance;
        this.location = location;
        this.priceRating = priceRating;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
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
}