package com.phatle.smartrestaurant;

public class DragItem {

    int imgRes;
    String name;
    String price;
    int qty;

    public DragItem(int imgRes, String name, String price, int qty) {
        this.imgRes = imgRes;
        this.name = name;
        this.price = price;
        this.qty = qty;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
