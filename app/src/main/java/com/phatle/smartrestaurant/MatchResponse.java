package com.phatle.smartrestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class MatchResponse {


    @SerializedName("HomeImg")
    @Expose
    private String homeImg;
    @SerializedName("AwayImg")
    @Expose
    private String awayImg;
    @SerializedName("HomeName")
    @Expose
    private String homeName;
    @SerializedName("AwayName")
    @Expose
    private String awayName;
    @SerializedName("HomeScore")
    @Expose
    private String homeScore;
    @SerializedName("AwayScore")
    @Expose
    private String awayScore;

    public String getHomeImg() {
        return homeImg;
    }

    public void setHomeImg(String homeImg) {
        this.homeImg = homeImg;
    }

    public String getAwayImg() {
        return awayImg;
    }

    public void setAwayImg(String awayImg) {
        this.awayImg = awayImg;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }
}
