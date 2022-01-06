package com.project.news_app.adapters;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.chip.Chip;
import com.project.news_app.R;
import com.project.news_app.data.Podcast;

import java.util.ArrayList;

/**
 * Provides {@link PodcastViewHolder} ViewHolder inflated from {@link R.layout#podcast_item}
 * item view layout to {@link R.id#recycler_view} RecyclerView.
 */
public class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder> {

    // Used to load images via Glide library.
    private Context mContext;

    // Stores downloaded podcasts info.
    private ArrayList<Podcast> podcasts;

    public PodcastAdapter(ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    @NonNull
    @Override
    public PodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Setting Context.
        mContext = parent.getContext();

        // Inflating "podcast_item" layout in order to initialize PodcastViewHolder instance.
        return new PodcastViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.podcast_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastViewHolder holder, int position) {
        // Binds podcast data with "podcast_item" layout.
        holder.setPodcastData(podcasts.get(position), position);
    }

    @Override
    public int getItemCount() {
        // Returns 0 if list is empty.
        if (podcasts == null) {
            return 0;
        }

        // Returns total number of podcast items.
        return podcasts.size();
    }

    /**
     * Sets new news data to Adapter.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setPodcastData(ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
        notifyDataSetChanged();
    }

    /**
     * Class describes {@link R.layout#podcast_item} item view and is responsible for caching
     * views.
     * <p>
     * It also provides click functionality to the RecyclerView holding news items.
     */
    protected class PodcastViewHolder extends RecyclerView.ViewHolder {

        // Shows the podcast thumbnail.
        private final ImageView thumbnail;

        // Shows the podcast headline.
        private final TextView headline;

        // Shows the date on when the podcast was recorded.
        private final TextView date;

        // Shows brief info. about the podcast.
        private final TextView about;

        // Indicates whether the card view is expanded or collapased.
        private final ImageView arrow;

        // Contains Guardian podcast info.
        private final ConstraintLayout headLayout;

        // Layout shows extra info. about the podcast.
        private final ConstraintLayout expandableLayout;

        public PodcastViewHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            thumbnail = itemView.findViewById(R.id.thumbnail);
            headline = itemView.findViewById(R.id.podcast_name);
            date = itemView.findViewById(R.id.podcast_date);
            about = itemView.findViewById(R.id.text_trail);
            arrow = itemView.findViewById(R.id.image_arrow);
            headLayout = itemView.findViewById(R.id.head_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            // Opens up the podcast in the device's browser.
            Chip podcastGoogle = itemView.findViewById(R.id.chip_google);

            // Opens up the podcast in the device's browser.
            Chip podcastSpotify = itemView.findViewById(R.id.chip_spotify);

            // Shows all podcast info.
            ConstraintLayout basicLayout = itemView.findViewById(R.id.basic_layout);

            // ClickListener is attached to all Chips and "expandableLayout" ConstraintLayout.
            View.OnClickListener clickListener = view -> {
                // Get the clicked view ID.
                int viewID = view.getId();

                // Gets the clicked Podcast.
                Podcast podcast = podcasts.get(getAdapterPosition());

                // Checks if user clicked the "basic_layout" in order to expand / collapse podcast item.
                if (viewID == R.id.basic_layout) {
                    expandCollapseItem(podcast);
                }

                // Checks if user clicked "Google Podcasts" Chip in order to open podcast.
                else if (viewID == R.id.chip_google) {
                    openPodcast(Uri.parse(podcast.getGooglePodcastUrl()));
                }

                // Checks if user clicked "Spotify" Chip in order to open podcast.
                else if (viewID == R.id.chip_spotify) {
                    openPodcast(Uri.parse(podcast.getSpotifyUrl()));
                }
            };

            /*
             * Attaches a click listener to the "basicLayout" which expands or collapse the
             * CardView to show or hide extra podcast info.
             */
            basicLayout.setOnClickListener(clickListener);

            // Attaches a click listener to both chips in order to open clicked Podcast.
            podcastSpotify.setOnClickListener(clickListener);
            podcastGoogle.setOnClickListener(clickListener);
        }

        /**
         * Sets the Podcast's thumbnail, headline, date and info.
         *
         * @param podcast Contains podcast info for item view.
         */
        public void setPodcastData(Podcast podcast, int position) {

            if (position == 0) {
                headLayout.setVisibility(View.VISIBLE);
            } else {
                headLayout.setVisibility(View.GONE);
            }

            // Setting podcast thumbnail.
            String thumbnailUrl = podcast.getThumbnailUrl();

            // Checks if thumbnail link is available.
            if (!TextUtils.isEmpty(thumbnailUrl)) {
                // Shows ImageView.
                thumbnail.setVisibility(View.VISIBLE);

                // Downloads and sets podcast's thumbnail.
                Glide.with(mContext)
                        .load(thumbnailUrl)
                        .placeholder(R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(thumbnail);
            } else {
                // Hides ImageView.
                thumbnail.setVisibility(View.INVISIBLE);
            }

            // Sets podcast headline.
            setText(headline, podcast.getHeadline());

            // Sets podcast date.
            setText(date, podcast.getDate());

            // Sets podcast info.
            setText(about, podcast.getTrailText());

            // Checks the expanded status of the podcast item view.
            boolean isItemExpanded = podcast.isExpanded();

            if (isItemExpanded) {
                // Show "expandable_layout".
                expandableLayout.setVisibility(View.VISIBLE);

                // Update arrow.
                arrow.setImageDrawable(AppCompatResources.getDrawable(mContext,
                        R.drawable.ic_arrow_up));
            } else {
                // Hide "expandable_layout".
                expandableLayout.setVisibility(View.GONE);

                // Update arrow.
                arrow.setImageDrawable(AppCompatResources.getDrawable(mContext,
                        R.drawable.ic_arrow_down));
            }
        }

        /**
         * Sets text to a TextView. If data is not available, visibility is set to
         * {@link View#GONE}.
         */
        private void setText(TextView textView, String text) {
            if (TextUtils.isEmpty(text)) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(text);
                textView.setVisibility(View.VISIBLE);
            }
        }

        /**
         * Opens the clicked podcast in either the device's "Browser" app OR opens up a
         * disambiguation dialog if user has installed either apps -
         * <ul>
         *     <li>Google Podcasts</li>
         *     <li>Spotify</li>
         * </ul>
         *
         * @param webPage Uri points to the clicked podcasts' "Google Podcasts" / "Spotify" link.
         */
        private void openPodcast(Uri webPage) {
            try {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, webPage));
            } catch (ActivityNotFoundException e) {
                Log.v("PodcastAdapter", "Unable to open - " + e.getMessage());
            }
        }

        /**
         * Expands or collapses the {@link R.id#expandable_layout} in {@link R.layout#podcast_item}
         * item view.
         *
         * @param podcast Contains clicked podcast info.
         */
        private void expandCollapseItem(Podcast podcast) {
            // Invert the expand status of the clicked podcast.
            podcast.setExpanded(!podcast.isExpanded());

            // Refreshes the clicked item to reload its contents with new expanded status.
            notifyItemChanged(getAdapterPosition());
        }
    }
}
