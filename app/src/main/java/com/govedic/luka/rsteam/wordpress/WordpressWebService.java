package com.govedic.luka.rsteam.wordpress;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WordpressWebService {
    @GET("/plugins/info/1.1/?action=query_plugins&request[page]=1&request[browse]=new")
    Call<WordpressPluginInfo> pluginInfo(@Query("request[per_page]") int numberOfPlugins);
    //https://api.wordpress.org/plugins/info/1.1/?action=query_plugins&request[page]=1&request[per_page]=100&request[browse]=new

}
