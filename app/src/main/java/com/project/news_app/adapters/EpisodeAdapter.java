package com.project.news_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.R;
import com.project.news_app.data.Episode;
import com.project.news_app.data.Podcast;
import com.project.news_app.activities.EpisodeActivity;
import com.project.news_app.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Adapter provides {@link Episode} to the RecyclerView in {@link EpisodeActivity}.
 */
public class EpisodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * Used to load images via Glide library.
     */
    private final Context context;

    /**
     * Stores downloaded episodes info.
     */
    private ArrayList<Episode> episodes;

    /**
     * View type inflated from {@link R.layout#episode_item} layout.
     */
    public static final int EPISODE_DEFAULT = 1;

    /**
     * View type inflated from {@link R.layout#podcast_about} layout.
     */
    public static final int PODCAST_ABOUT = 2;

    public EpisodeAdapter(Context context, ArrayList<Episode> episodes) {
        this.context = context;
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates views from layout.
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (viewType) {
            case PODCAST_ABOUT:
                /*
                 * Shows info. about the clicked Podcast - title, about, links to access the
                 * podcast in - Apple Podcast, Google Podcast and Spotify, and indicates whether
                 * the episodes under the Podcast contains topics which are explicit.
                 */
                return new AboutPodcastViewHolder(layoutInflater.inflate(R.layout.podcast_about,
                        parent, false));

            case EPISODE_DEFAULT:
            default:
                /*
                 * Shows info. of an episode belonging to the clicked Podcast - title, date,
                 * by-line, about, and link to access the Episode in "The Guardian" website.
                 */
                return new EpisodeViewHolder(layoutInflater.inflate(R.layout.episode_item, parent,
                        false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Get the episode at specified position.
        Episode episode = episodes.get(position);

        // Binding current episode data based on view type.
        switch (holder.getItemViewType()) {
            case EPISODE_DEFAULT:
                ((EpisodeViewHolder) holder).setEpisodeData(episode);
                break;

            case PODCAST_ABOUT:
                ((AboutPodcastViewHolder) holder).setPodcastData(episode);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return episodes.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        // Returns 0 if list is empty.
        if (episodes == null) {
            return 0;
        }

        // Returns total number of episode items.
        return episodes.size();
    }

    /**
     * Sets new episode data to Adapter.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setEpisodeData(ArrayList<Episode> episodes) {
        this.episodes = episodes;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder shows episode's title, date, by-line, about, and link to access the Episode in
     * "The Guardian" website.
     * <br/>
     * Layout resource - {@link R.layout#episode_item}.
     */
    protected class EpisodeViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        // Shows the episode's title in the collapsed state.
        private final TextView titleCollapsed;

        // Shows when the episode was recorded in the collapsed state.
        private final TextView dateCollapsed;

        // Shows the episode's title in the expanded state.
        private final TextView titleExpanded;

        // Shows when the episode was recorded in the expanded state.
        private final TextView dateExpanded;

        // Shows the thumbnail of the episode in the expanded state.
        private final ImageView thumbnailExpanded;

        // Shows the host's info of the episode in the expanded state.
        private final TextView byLineExpanded;

        // Shows info. about the episode in the expanded state.
        private final TextView aboutExpanded;

        /*
         * Shows the episode's title, and date.
         */
        private final ConstraintLayout basicLayout;

        /*
         * Shows the episode's details when user clicks the collapsed layout in order to show more
         * details.
         */
        private final ConstraintLayout expandableLayout;

        // Indicates that the entire layout (in collapsed state) is expanded.
        private final ImageView arrow;

        public EpisodeViewHolder(View itemView) {
            super(itemView);

            // Initializes Collapsed Views.
            titleCollapsed = itemView.findViewById(R.id.collapsed_episode_name);
            dateCollapsed = itemView.findViewById(R.id.collapse_episode_date);
            arrow = itemView.findViewById(R.id.image_arrow);

            // Initializes Expanded Views.
            titleExpanded = itemView.findViewById(R.id.expanded_episode_title);
            dateExpanded = itemView.findViewById(R.id.expanded_episode_date);
            thumbnailExpanded = itemView.findViewById(R.id.expanded_episode_thumbnail);
            byLineExpanded = itemView.findViewById(R.id.expanded_episode_byline);
            aboutExpanded = itemView.findViewById(R.id.expanded_episode_about);
            basicLayout = itemView.findViewById(R.id.basic_layout);

            // Opens up the episode in "The Guardian" website.
            Button buttonListen = itemView.findViewById(R.id.expanded_episode_play);

            // Attaching OnClickListener to play the selected episode in "The Guardian" website.
            buttonListen.setOnClickListener(this);

            // Initializes layout that gets displayed.
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            // Attaching OnClickListener to either expand/collapse the item view.
            basicLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the clicked Episode in adapter.
            int currentPosition = getAdapterPosition();

            // Getting the current episode.
            Episode episode = episodes.get(currentPosition);

            // Get the clicked view ID.
            int clickedViewID = view.getId();

            // Checks if user tried to expand/collapse episode.
            if (clickedViewID == R.id.basic_layout) {
                // Invert the expand status of the clicked podcast.
                episode.setExpanded(!episode.isExpanded());

                // Refreshes the clicked item to reload its contents with new expanded status.
                notifyItemChanged(currentPosition);
            }

            // Checks if user tried to play the expanded podcast.
            else if (clickedViewID == R.id.expanded_episode_play) {
                CommonUtils.openBrowserOrApp(context, episode.getEpisodeUrl(),
                        R.string.toast_browser_unavailable);
            }
        }

        /**
         * Set's info. about the episode.
         */
        public void setEpisodeData(Episode episode) {
            // Sets collapsed title.
            titleCollapsed.setText(episode.getHeadline());

            // Sets collapsed date.
            dateCollapsed.setText(episode.getDate());

            // Checking whether episode is expanded or collapsed.
            if (episode.isExpanded()) {
                // Update drawable of "basicLayout".
                basicLayout.setBackground(AppCompatResources.getDrawable(context,
                        R.drawable.ripple_red));

                // Update date collapsed color.
                dateCollapsed.setTextColor(ContextCompat.getColor(context,
                        R.color.colorRedLight));

                // Update arrow.
                arrow.setImageDrawable(AppCompatResources.getDrawable(context,
                        R.drawable.ic_arrow_up));

                // Checks if thumbnail url for the episode is available.
                String thumbnail = episode.getThumbnailUrl();
                if (!TextUtils.isEmpty(thumbnail)) {
                    // Downloading podcast thumbnail.
                    CommonUtils.setThumbnail(context, thumbnailExpanded, thumbnail);
                } else {
                    // Shows the podcast's thumbnail instead.
                    thumbnailExpanded.setImageResource(episodes.get(0).getPodcast().getThumbnail());
                }

                // Sets expanded title.
                titleExpanded.setText(episode.getHeadline());

                // Sets expanded date.
                dateExpanded.setText(episode.getDate());

                // Sets expanded by line.
                CommonUtils.setText(byLineExpanded, episode.getByLine());

                // Sets expanded about.
                CommonUtils.setText(aboutExpanded, episode.getStandFirst());

                // Shows the expanded layout.
                expandableLayout.setVisibility(View.VISIBLE);
            } else {
                // Update drawable of "basicLayout".
                basicLayout.setBackground(AppCompatResources.getDrawable(context,
                        R.drawable.ripple_dark));

                // Update date collapsed color.
                dateCollapsed.setTextColor(ContextCompat.getColor(context,
                        R.color.colorLight));

                // Hides the expanded layout.
                expandableLayout.setVisibility(View.GONE);

                // Update arrow.
                arrow.setImageDrawable(AppCompatResources.getDrawable(context,
                        R.drawable.ic_arrow_down));
            }
        }
    }

    /**
     * ViewHolder shows basic info. about the clicked Podcast title, about, links to access the
     * podcast in - Apple Podcast, Google Podcast and Spotify, and indicates whether the episodes
     * under the Podcast contains topics which are explicit.
     * <br/>
     * Layout resource - {@link R.layout#podcast_about}
     */
    protected class AboutPodcastViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        // Shows the podcast's thumbnail.
        private final ImageView thumbnail;

        // Shows the podcast's title.
        private final TextView title;

        // Shows brief info. about the podcast.
        private final TextView about;

        // Indicates whether the podcast contains explicit content.
        private final TextView warning;

        // Stores podcast URL on different platforms.
        private String applePodcastUrl, googlePodcastUrl, spotifyPodcastUrl;

        // Shows apple podcast logo.
        private final ImageView imageApple;

        // Shows google podcast logo.
        private final ImageView imageGoogle;

        // Shows spotify podcast logo.
        private final ImageView imageSpotify;

        public AboutPodcastViewHolder(View itemView) {
            super(itemView);

            // Initializing views.
            thumbnail = itemView.findViewById(R.id.podcast_about_thumbnail);
            title = itemView.findViewById(R.id.podcast_about_title);
            about = itemView.findViewById(R.id.podcast_about_about);
            warning = itemView.findViewById(R.id.text_warning);

            // Shows all available platforms streaming this podcast.
            imageApple = itemView.findViewById(R.id.image_podcast_apple);
            imageGoogle = itemView.findViewById(R.id.image_podcast_google);
            imageSpotify = itemView.findViewById(R.id.image_podcast_spotify);

            // Attaching OnClickListener to open the podcast in "Apple Podcast".
            imageApple.setOnClickListener(this);

            // Attaching OnClickListener to open the podcast in "Google Podcast".
            imageGoogle.setOnClickListener(this);

            // Attaching OnClickListener to open the podcast in "Spotify".
            imageSpotify.setOnClickListener(this);
        }

        /**
         * Sets info. about the podcast of this episode.
         */
        public void setPodcastData(Episode episode) {
            // Get podcast info.
            Podcast podcast = episode.getPodcast();

            // Sets podcast thumbnail.
            thumbnail.setImageResource(podcast.getThumbnail());

            // Sets podcast title.
            title.setText(podcast.getTitle());

            // Checks whether the podcast contains explicit content in order to show "warning".
            if (!podcast.isExplicit()) {
                warning.setVisibility(View.GONE);
            } else {
                warning.setVisibility(View.VISIBLE);
            }

            // Sets podcast info.
            about.setText(podcast.getDescription());

            // Get's apple podcast url.
            applePodcastUrl = podcast.getApplePodcastUrl();

            // Sets background and src. based on apple podcast url.
            setPodcastBackground(applePodcastUrl, imageApple, R.drawable.ic_podcast_apple,
                    R.drawable.ic_podcast_apple_disabled);

            // Get's google podcast url.
            googlePodcastUrl = podcast.getGooglePodcastUrl();

            // Sets background and src. based on google podcast url.
            setPodcastBackground(googlePodcastUrl, imageGoogle, R.drawable.ic_podcast_google,
                    R.drawable.ic_podcast_google_disabled);

            // Get's spotify podcast url.
            spotifyPodcastUrl = podcast.getSpotifyUrl();

            // Sets background and src. based on spotify podcast url.
            setPodcastBackground(spotifyPodcastUrl, imageSpotify, R.drawable.ic_podcast_spotify,
                    R.drawable.ic_podcast_spotify_disabled);
        }

        /**
         * Sets the podcast logo background and source.
         *
         * @param podcastUrl      Locates podcast in platforms - "Google Podcasts", "Apple
         *                        Podcasts" and "Spotify".
         * @param podcast         Shows podcast logo.
         * @param enableDrawable  Drawable resource is set if the podcast is present in the
         *                        platform.
         * @param disableDrawable Drawable resource is set if the podcast is not present in the
         *                        platform.
         */
        private void setPodcastBackground(String podcastUrl, ImageView podcast, int enableDrawable,
                                          int disableDrawable) {
            if (!TextUtils.isEmpty(podcastUrl)) {
                podcast.setBackground(AppCompatResources.getDrawable(context,
                        R.drawable.ripple_podcast_platform));
                podcast.setImageResource(enableDrawable);
                podcast.setEnabled(true);

            } else {
                podcast.setBackground(AppCompatResources.getDrawable(context,
                        R.drawable.shape_podcast_disabled));
                podcast.setImageResource(disableDrawable);
                podcast.setEnabled(false);
            }
        }

        @Override
        public void onClick(View fab) {
            // Gets the clicked platform ID.
            int platformID = fab.getId();

            // Stores the url of the clicked platform.
            String url = "";

            // Checks if user clicked "Apple" FAB.
            if (platformID == R.id.image_podcast_apple) {
                url = applePodcastUrl;
            }

            // Checks if user clicked "Google" FAB.
            else if (platformID == R.id.image_podcast_google) {
                url = googlePodcastUrl;
            }

            // Checks if user clicked "Spotify" FAB.
            else if (platformID == R.id.image_podcast_spotify) {
                url = spotifyPodcastUrl;
            }

            /*
             * Opens the clicked platform in its individual app or load its page in device's
             * browser.
             */
            CommonUtils.openBrowserOrApp(context, url, R.string.toast_browser_unavailable);
        }
    }
}