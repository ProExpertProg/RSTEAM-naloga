package com.govedic.luka.rsteam.wordpress;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordpressPluginInfo {

    @SerializedName("plugins")
    @Expose
    private List<Plugin> plugins = null;

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

}