package com.govedic.luka.rsteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.govedic.luka.rsteam.listGraphics.WordpressPluginsAdapter;
import com.govedic.luka.rsteam.wordpress.Plugin;
import com.govedic.luka.rsteam.wordpress.WordpressPlugin;
import com.govedic.luka.rsteam.wordpress.WordpressPluginInfo;
import com.govedic.luka.rsteam.wordpress.WordpressWebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        //prepare the client

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.wordpress.org/")
                .build();

        WordpressWebService service = retrofit.create(WordpressWebService.class);

        //number of plugins
        int numPlugins = 100;

        //get the json data
        service.pluginInfo(numPlugins).enqueue(new Callback<WordpressPluginInfo>() {
            @Override
            public void onResponse(Call<WordpressPluginInfo> call, Response<WordpressPluginInfo> response) {
                //get plugins in pojo form
                List<Plugin> pojoPlugins = response.body().getPlugins();

                //convert them to the desirable form (WordpressPlugin)
                int count = 0;
                WordpressPlugin[] plugins = new WordpressPlugin[pojoPlugins.size()];
                for (Plugin p : pojoPlugins){
                    plugins[count++] = new WordpressPlugin(p);
                }

                //fill the recycler view
                mAdapter = new WordpressPluginsAdapter(plugins);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<WordpressPluginInfo> call, Throwable t) {
                System.err.print(t.getMessage());
                t.printStackTrace(System.err);
            }
        });
    }

}
