package com.phatle.smartrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuItem implements Serializable{

    @SerializedName("menuImg")
    @Expose
    String imgRes;

    @SerializedName("menuName")
    @Expose
    String name;

    @SerializedName("menuPrice")
    @Expose
    float price;

    @SerializedName("menuDescription")
    @Expose
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
