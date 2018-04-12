package com.govedic.luka.rsteam.listGraphics;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.govedic.luka.rsteam.R;
import com.govedic.luka.rsteam.wordpress.WordpressPlugin;

public class WordpressPluginsAdapter extends RecyclerView.Adapter {
    private WordpressPlugin[] mDataset;

    public WordpressPluginsAdapter(WordpressPlugin[] mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plugin_item_view, parent, false);
        return new WordpressPluginViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.length : 0;
    }
}
