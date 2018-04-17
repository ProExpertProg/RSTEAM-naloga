package com.govedic.luka.rsteam;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.govedic.luka.rsteam.wordpress.WordpressPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

public class PluginDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_PLUGIN = "PluginDetailsActivity.EXTRA_PLUGIN";
    private WordpressPlugin plugin;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_details);

        plugin = getIntent().getExtras().getParcelable(EXTRA_PLUGIN);
        Button btn = findViewById(R.id.homepage_link);
        if (plugin.homepageURL == null || plugin.homepageURL.isEmpty()) {
            //invalid link, hide the button
            ViewGroup parent = (ViewGroup) btn.getParent();
            parent.removeView(btn);
            //parent.
        } else {
            btn.setOnClickListener(this);
        }

        System.out.println("Preping the list");
        ListView lv = findViewById(R.id.file_types_list);
        mAdapter = new ArrayAdapter<>(this, R.layout.zip_list_item, R.id.zip_text_view, new ArrayList<String>());
        lv.setAdapter(mAdapter);

        System.out.println("Invoking download");
        downloadAndTraverseZip(plugin.downloadURL);
    }

    //when the homepage button is clicked
    @Override
    public void onClick(View v) {
        String url = plugin.homepageURL;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void downloadAndTraverseZip(String url) {
        try {
            File outputDir = getCacheDir(); // context being the Activity pointer
            File outputFile = File.createTempFile("plugin", "zip", outputDir);

            DownloadFileAsync download = new DownloadFileAsync(outputFile, new DownloadFileAsync.PostDownload() {
                @Override
                public void downloadDone(File file) {
                    System.out.println("file download completed");
                    try {
                        FileInputStream fin = new FileInputStream(file);
                        ZipInputStream zin = new ZipInputStream(fin);
                        Map<String, Integer> numFiles = ZipTraverse.numberOfFiles(zin);

                        displayFileCountResults(numFiles);
                        System.out.println("file traverse completed");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            download.execute(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayFileCountResults(Map<String, Integer> numFiles) {
        //initialize the list
        List<Map.Entry<String, Integer>> list = new ArrayList<>(numFiles.entrySet());

        //sort it ascending
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                //sort by number of files and then extension
                int val = o1.getValue().compareTo(o2.getValue());
                return val == 0 ? o1.getKey().compareTo(o2.getKey()) : val;
            }
        });

        //display in the list view
        for (Map.Entry<String, Integer> pair : list) {
            String str = pair.getKey() + ":" + pair.getValue();
            mAdapter.add(str);
        }
    }
}
