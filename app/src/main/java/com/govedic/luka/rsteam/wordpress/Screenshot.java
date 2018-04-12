package com.govedic.luka.rsteam.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Screenshot {
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("src")
    @Expose
    private String src;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
