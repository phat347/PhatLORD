package com.phatle.smartrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentItem implements Serializable{

    @SerializedName("commentImg")
    @Expose
    String imgRes;

    @SerializedName("commentTime")
    @Expose
    int time;

    @SerializedName("commentName")
    @Expose
    String name;

    @SerializedName("commentScore")
    @Expose
    float score;

    @SerializedName("commentContent")
    @Expose
    String comment;

    public CommentItem(String imgRes, int time, String name, float score, String comment) {
        this.imgRes = imgRes;
        this.time = time;
        this.name = name;
        this.score = score;
        this.comment = comment;
    }
    public CommentItem(String imgRes, String name, float score, String comment) {
        this.imgRes = imgRes;
        this.time = -1;
        this.name = name;
        this.score = score;
        this.comment = comment;
    }

    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
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
