package com.phatle.smartrestaurant;

import java.io.Serializable;

public class MenuItem implements Serializable{
    int imgRes;
    String name;
    float price;
    String description;

    public MenuItem(int imgRes, String name, float price, String description) {
        this.imgRes = imgRes;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
