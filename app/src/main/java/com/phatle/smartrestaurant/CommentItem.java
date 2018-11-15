package com.phatle.smartrestaurant;

import java.io.Serializable;

public class CommentItem implements Serializable{
    int imgRes;
    int time;
    String name;
    float score;
    String comment;

    public CommentItem(int imgRes, int time, String name, float score, String comment) {
        this.imgRes = imgRes;
        this.time = time;
        this.name = name;
        this.score = score;
        this.comment = comment;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
