package com.phatle.smartrestaurant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NationalTeamResponse implements Serializable{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("fw")
    @Expose
    private Integer fw;
    @SerializedName("mf")
    @Expose
    private Integer mf;
    @SerializedName("df")
    @Expose
    private Integer df;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getFw() {
        return fw;
    }

    public void setFw(Integer fw) {
        this.fw = fw;
    }

    public Integer getMf() {
        return mf;
    }

    public void setMf(Integer mf) {
        this.mf = mf;
    }

    public Integer getDf() {
        return df;
    }

    public void setDf(Integer df) {
        this.df = df;
    }
}
