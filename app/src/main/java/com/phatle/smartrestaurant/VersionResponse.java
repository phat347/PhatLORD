package com.phatle.smartrestaurant;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionResponse {

    @SerializedName("new_version")
    @Expose
    private String newVersion;
    @SerializedName("is_force_update")
    @Expose
    private Boolean isForceUpdate;

    @SerializedName("url")
    @Expose
    private String url;

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public Boolean getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(Boolean isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}