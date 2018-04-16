package com.govedic.luka.rsteam;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.govedic.luka.rsteam.listGraphics.WordpressPluginsAdapter;
import com.govedic.luka.rsteam.wordpress.JSONObjectLikeListDeserializer;
import com.govedic.luka.rsteam.wordpress.Plugin;
import com.govedic.luka.rsteam.wordpress.Screenshot;
import com.govedic.luka.rsteam.wordpress.WordpressPlugin;
import com.govedic.luka.rsteam.wordpress.WordpressPluginInfo;
import com.govedic.luka.rsteam.wordpress.WordpressWebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NumberOfPluginsDialogFragment.ValueChosenListener{

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

        loadPlugins(100);
    }

    public WordpressWebService buildWebService() {
        //prepare the client

        GsonBuilder gsonBuilder = new GsonBuilder();
        // Adding custom deserializers
        gsonBuilder.registerTypeAdapter(new TypeToken<List<Plugin>>() {
        }.getType(), new JSONObjectLikeListDeserializer<Plugin>());
        gsonBuilder.registerTypeAdapter(new TypeToken<List<Screenshot>>() {
        }.getType(), new JSONObjectLikeListDeserializer<Screenshot>());
        Gson gson = gsonBuilder.create();
        GsonConverterFactory factory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.wordpress.org/")
                .addConverterFactory(factory)
                .build();

        return retrofit.create(WordpressWebService.class);
    }

    @Override
    public void onValueChosen(int value) {
        loadPlugins(value);
    }

    public void loadPlugins(final int numPlugins){

        WordpressWebService service = buildWebService();


        final WordpressPlugin[] plugins = new WordpressPlugin[numPlugins];

        //fill the recycler view
        mAdapter = new WordpressPluginsAdapter(plugins);
        mRecyclerView.setAdapter(mAdapter);

        //get the json data
        service.pluginInfo(numPlugins).enqueue(new Callback<WordpressPluginInfo>() {
            @Override
            public void onResponse(Call<WordpressPluginInfo> call, Response<WordpressPluginInfo> response) {
                //get plugins in pojo form
                List<Plugin> pojoPlugins = response.body().getPlugins();

                //convert them to the desirable form (WordpressPlugin)
                int count = 0;
                if (pojoPlugins.size() > numPlugins)
                    pojoPlugins = pojoPlugins.subList(0, numPlugins);

                for (Object p : pojoPlugins) {
                    plugins[count++] = new WordpressPlugin((Plugin) p);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<WordpressPluginInfo> call, Throwable t) {
                System.err.print(t.getMessage());
                t.printStackTrace(System.err);
            }
        });
    }
}
