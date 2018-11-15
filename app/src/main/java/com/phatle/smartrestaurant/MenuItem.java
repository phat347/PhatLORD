package com.phatle.smartrestaurant;

import java.io.Serializable;

public class MenuItem implements Serializable{
    String imgRes;
    String name;
    float price;
    String description;

    public MenuItem(String imgRes, String name, float price, String description) {
        this.imgRes = imgRes;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
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
