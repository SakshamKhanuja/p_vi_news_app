package com.project.news_app.data;

import com.project.news_app.constants.JsonUtilsConstants;
import com.project.news_app.adapters.PodcastAdapter;

/**
 * Defines a single podcast item.
 */
public class Podcast {
    // Stores the podcast title.
    private String headline;

    // Stores brief info. about this podcast.
    private String trailText;

    /**
     * Stores then date when this podcast was recorded.
     * <p>
     * Date Format - {@link JsonUtilsConstants#PATTERN_PODCAST}
     */
    private String date;

    // Stores a URL in String format that locates this podcast in Spotify.
    private String spotifyUrl;

    // Stores a URL in String format that locates this podcast in Google Podcast.
    private String googlePodcastUrl;

    // Stores a URL in String format that points to thumbnail for this podcast.
    private String thumbnailUrl;

    // Stores the layout in which this feed gets displayed.
    private int viewType = PodcastAdapter.PODCAST_DEFAULT;

    /**
     * Stores status whether a CardView item in {@link PodcastAdapter} is EXPANDED or COLLAPSED.
     * <p>
     * By default every item in Adapter is set to COLLAPSED.
     */
    private boolean expanded = false;

    /**
     * Sets the headline for this podcast.
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * Sets brief details for this podcast.
     */
    public void setTrailText(String trailText) {
        this.trailText = trailText;
    }

    /**
     * Sets the date when the podcast was recorded.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the string URL that locates this podcast in Spotify.
     */
    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }

    /**
     * Sets the string URL that locates this podcast in Google Podcasts.
     */
    public void setGooglePodcastUrl(String googlePodcastUrl) {
        this.googlePodcastUrl = googlePodcastUrl;
    }

    /**
     * Sets the thumbnail URL for this Podcast.
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * Sets the status for the CardView item to be either EXPANDED or COLLAPSED.
     *
     * @param expanded Set 'true' to EXPAND and 'false' to COLLAPSE.
     */
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * @param viewType Sets the view type in which this News feed gets displayed.
     */
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    /**
     * @return The podcast headline.
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * @return Brief details about the podcast.
     */
    public String getTrailText() {
        return trailText;
    }

    /**
     * @return Date when the podcast was recorded.
     */
    public String getDate() {
        return date;
    }

    /**
     * @return URL in String format that locates the podcast in Spotify.
     */
    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    /**
     * @return URL in String format that locates the podcast in Google Podcast.
     */
    public String getGooglePodcastUrl() {
        return googlePodcastUrl;
    }

    /**
     * Podcast's thumbnail.
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * @return Status whether CardView item is EXPANDED.
     */
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * @return Layout in which the News feed gets displayed.
     */
    public int getViewType() {
        return viewType;
    }
}