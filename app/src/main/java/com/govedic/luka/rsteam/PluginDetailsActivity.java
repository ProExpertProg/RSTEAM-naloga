package com.govedic.luka.rsteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.govedic.luka.rsteam.wordpress.WordpressPlugin;

public class PluginDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_PLUGIN = "PluginDetailsActivity.EXTRA_PLUGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_details);

        WordpressPlugin plugin = getIntent().getExtras().getParcelable(EXTRA_PLUGIN);
    }
}
