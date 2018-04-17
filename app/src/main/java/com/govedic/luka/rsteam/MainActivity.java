package com.govedic.luka.rsteam;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity implements NumberOfPluginsDialogFragment.ValueChosenListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public final static String NUM_PLUGINS_SHARED_PREF_KEY = "NUM_WORDPRESS_PLUGINS_TO_LOAD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        if (sharedPref.contains(NUM_PLUGINS_SHARED_PREF_KEY)) {
            //if we already know how many plugins to show, then we get the number and load them
            int numPlugins = sharedPref.getInt(NUM_PLUGINS_SHARED_PREF_KEY, 100);
            loadPlugins(numPlugins);
        } else {
            //otherwise, the user has to enter the number of plugins in our dialog
            DialogFragment df = new NumberOfPluginsDialogFragment();
            df.show(getFragmentManager(), "numberOfPlugins");
        }

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
        //save the value of numberOfPlugins
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(NUM_PLUGINS_SHARED_PREF_KEY, value);
        editor.apply();

        loadPlugins(value);
    }

    public void loadPlugins(final int numPlugins) {

        WordpressWebService service = buildWebService();
        final WordpressPlugin[] plugins = new WordpressPlugin[numPlugins];

        //fill the recycler view
        mAdapter = new WordpressPluginsAdapter(plugins);
        mRecyclerView.setAdapter(mAdapter);

        //get the json data
        service.pluginInfo(numPlugins).enqueue(new Callback<WordpressPluginInfo>() {
            @Override
            public void onResponse(@NonNull Call<WordpressPluginInfo> call, @NonNull Response<WordpressPluginInfo> response) {
                //get plugins in pojo form
                List<Plugin> pojoPlugins = response.body().getPlugins();

                //convert them to the desirable form (WordpressPlugin)
                int count = 0;

                //just in case more plugins were loaded than necessary, cut the redundant part
                if (pojoPlugins.size() > numPlugins)
                    pojoPlugins = pojoPlugins.subList(0, numPlugins);

                for (Plugin p : pojoPlugins) {
                    plugins[count++] = new WordpressPlugin(p);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<WordpressPluginInfo> call, @NonNull Throwable t) {
                System.err.print(t.getMessage());
                t.printStackTrace(System.err);
            }
        });
    }
}
