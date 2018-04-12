package com.govedic.luka.rsteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.govedic.luka.rsteam.listGraphics.WordpressPluginsAdapter;
import com.govedic.luka.rsteam.wordpress.WordpressPlugin;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new WordpressPluginsAdapter(getPlugins());
        mRecyclerView.setAdapter(mAdapter);
    }

    private WordpressPlugin[] getPlugins(){
        return null;
    }
}
