package com.phatle.smartrestaurant;

public class Player {

    String name;
    int imgRes;
    int matchePlayed;
    int point;
    int gd;

    public Player(String name, int imgRes, int matchePlayed, int point, int gd) {
        this.name = name;
        this.imgRes = imgRes;
        this.matchePlayed = matchePlayed;
        this.point = point;
        this.gd = gd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public int getMatchePlayed() {
        return matchePlayed;
    }

    public void setMatchePlayed(int matchePlayed) {
        this.matchePlayed = matchePlayed;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getGd() {
        return gd;
    }

    public void setGd(int gd) {
        this.gd = gd;
    }
}
