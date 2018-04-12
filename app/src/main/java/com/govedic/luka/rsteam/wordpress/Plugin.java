package com.govedic.luka.rsteam.wordpress;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plugin {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("download_link")
    @Expose
    private String downloadLink;
    @SerializedName("screenshots")
    @Expose
    private List<Screenshot> screenshots;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

}