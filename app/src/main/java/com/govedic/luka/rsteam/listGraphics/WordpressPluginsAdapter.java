package com.govedic.luka.rsteam.listGraphics;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.govedic.luka.rsteam.PluginDetailsActivity;
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
        Context context = parent.getContext();
        View v = LayoutInflater.from(context)
                .inflate(R.layout.plugin_item_view, parent, false);
        return new WordpressPluginViewHolder(context, v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mDataset[position] != null) {
            WordpressPluginViewHolder h = (WordpressPluginViewHolder) holder;
            ImageView img = h.mItemView.findViewById(R.id.preview);
            String url = mDataset[position].screenshotURL;
            if (url != null && !url.isEmpty())
                Glide.with(h.mContext).load(url).error(R.drawable.ic_launcher_background).centerCrop().into(img);

            TextView name = h.mItemView.findViewById(R.id.pluginName);
            name.setText(mDataset[position].name);

            TextView desc = h.mItemView.findViewById(R.id.pluginDescription);
            desc.setText(mDataset[position].description);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.length : 0;
    }

    public class WordpressPluginViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View mItemView;
        Context mContext;

        WordpressPluginViewHolder(Context ctx, View itemView) {
            super(itemView);
            mItemView = itemView;
            mContext = ctx;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //open the details activity
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                WordpressPlugin plugin = mDataset[position];
                Intent intent = new Intent(mItemView.getContext(), PluginDetailsActivity.class);
                intent.putExtra(PluginDetailsActivity.EXTRA_PLUGIN, plugin);
                mItemView.getContext().startActivity(intent);
            }
        }
    }
}
