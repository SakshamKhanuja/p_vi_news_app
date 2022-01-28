package com.project.news_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.R;
import com.project.news_app.activities.EpisodeActivity;
import com.project.news_app.data.Podcast;
import com.project.news_app.fragments.PodcastFragment;

import java.util.ArrayList;

/**
 * Adapter provides {@link Podcast} to the RecyclerView in {@link PodcastFragment}.
 */
public class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder> {
    /**
     * Stores basic info. about all available podcasts in "The Guardian" API.
     */
    private final ArrayList<Podcast> podcasts;

    /**
     * Used to download thumbnails.
     */
    private final Context context;

    public PodcastAdapter(Context context, ArrayList<Podcast> podcasts) {
        this.context = context;
        this.podcasts = podcasts;
    }

    @NonNull
    @Override
    public PodcastAdapter.PodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
        // Initializing ViewHolder.
        return new PodcastViewHolder(LayoutInflater.from(context).inflate(R.layout.podcasts_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastAdapter.PodcastViewHolder holder,
                                 int position) {
        holder.setData(podcasts.get(position));
    }

    @Override
    public int getItemCount() {
        // Returns 0 if list is empty.
        if (podcasts == null) {
            return 0;
        }

        // Returns total number of podcast.
        return podcasts.size();
    }

    /**
     * ViewHolder shows thumbnail and title of podcasts.
     * <br/>
     * Layout resource - {@link R.layout#podcasts_item}.
     */
    protected class PodcastViewHolder extends RecyclerView.ViewHolder {
        // Shows the podcast thumbnail.
        private final ImageView thumbnail;

        // Shows the podcast web title.
        private final TextView title;

        public PodcastViewHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            thumbnail = itemView.findViewById(R.id.podcasts_thumbnail);
            title = itemView.findViewById(R.id.podcasts_title);

            // Attaching OnClickListener to show episodes of the clicked Podcast.
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, EpisodeActivity.class);

                // Adding the clicked podcast.
                intent.putExtra(EpisodeActivity.EXTRA_PODCAST, podcasts.get(getAdapterPosition()));
                context.startActivity(intent);
            });
        }

        /**
         * Sets podcasts' thumbnail and title.
         */
        public void setData(Podcast podcast) {
            // Sets podcasts' thumbnail.
            thumbnail.setImageResource(podcast.getThumbnail());

            // Sets podcasts' title.
            title.setText(podcast.getTitle());
        }
    }
}