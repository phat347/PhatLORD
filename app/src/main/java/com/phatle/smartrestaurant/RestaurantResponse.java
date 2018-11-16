package com.phatle.smartrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantResponse {
    @SerializedName("restaurantItem")
    @Expose
    private List<RestaurantDrawerItem> restaurantItem = null;

    public List<RestaurantDrawerItem> getRestaurantItem() {
        return restaurantItem;
    }

    public void setRestaurantItem(List<RestaurantDrawerItem> restaurantItem) {
        this.restaurantItem = restaurantItem;
    }
}
