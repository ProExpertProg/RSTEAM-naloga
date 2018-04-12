package com.govedic.luka.rsteam.wordpress;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordpressPluginInfo {

    @SerializedName("plugins")
    @Expose
    private List<WordpressPlugin> plugins = null;

    public List<WordpressPlugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<WordpressPlugin> plugins) {
        this.plugins = plugins;
    }

}