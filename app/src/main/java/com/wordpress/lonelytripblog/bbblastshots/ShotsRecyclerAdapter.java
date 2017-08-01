package com.wordpress.lonelytripblog.bbblastshots;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wordpress.lonelytripblog.bbblastshots.data.Shot;

import java.util.List;

/**
 * Adapter for recycler view.
 */

public class ShotsRecyclerAdapter extends RecyclerView.Adapter<ShotsRecyclerAdapter.MyViewHolder> {

    private List<Shot> shots;
    private int height;
    private int width;

    public ShotsRecyclerAdapter(List<Shot> shots) {
        this.shots = shots;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_shot_layout, parent, false);
        final float scale = parent.getContext().getResources().getDisplayMetrics().density;
        // 12 - padding for card in recycler view
        int pixels = (int) (12 * scale + 0.5f);
        height = parent.getHeight()/2-pixels;
        width = parent.getWidth()-pixels;
        itemView.setMinimumHeight(height);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Shot shot = shots.get(position);
        String url = shot.getUrl_hidpi() == null ? shot.getUrl_normal() == null ? shot.getUrl_teaser(): shot.getUrl_normal() : shot.getUrl_hidpi();
        holder.shotPicture.setMinimumHeight(height);
        Picasso.with(holder.shotPicture.getContext()).load(url).resize(width,height).into(holder.shotPicture);
        holder.shotTitle.setText(shot.getTitle());
        holder.shotDescription.setText(shot.getDescription());
    }

    @Override
    public int getItemCount() {
        return shots.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView shotPicture;
        public TextView shotTitle;
        public TextView shotDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            shotPicture = itemView.findViewById(R.id.shot_picture);
            shotTitle = itemView.findViewById(R.id.shot_title);
            shotDescription = itemView.findViewById(R.id.shot_description);
        }
    }
}
