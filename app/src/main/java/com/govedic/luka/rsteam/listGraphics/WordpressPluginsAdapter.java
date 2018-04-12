package com.govedic.luka.rsteam.listGraphics;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.govedic.luka.rsteam.wordpress.WordpressPlugin;

public class WordpressPluginsAdapter extends RecyclerView.Adapter {
    private WordpressPlugin[] mDataset;

    public WordpressPluginsAdapter(WordpressPlugin[] mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.length : 0;
    }
}
