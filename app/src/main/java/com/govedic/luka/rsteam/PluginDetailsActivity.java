package com.govedic.luka.rsteam;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.govedic.luka.rsteam.wordpress.WordpressPlugin;

public class PluginDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_PLUGIN = "PluginDetailsActivity.EXTRA_PLUGIN";
    private WordpressPlugin plugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_details);

        plugin = getIntent().getExtras().getParcelable(EXTRA_PLUGIN);
        Button btn =findViewById(R.id.homepage_link);
        if(plugin.homepageURL == null || plugin.homepageURL.isEmpty()){
            //invalid link, hide the button
            ViewGroup parent = (ViewGroup) btn.getParent();
            parent.removeView(btn);
            //parent.findViewById(R.id.file_types_list);
        } else {
            btn.setOnClickListener(this);
        }

    }

    //when the homepage button is clicked
    @Override
    public void onClick(View v) {
        String url = plugin.homepageURL;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
