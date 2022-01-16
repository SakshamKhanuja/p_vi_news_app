package com.project.news_app.data;

import com.project.news_app.constants.JsonUtilsConstants;
import com.project.news_app.adapters.EpisodeAdapter;

/**
 * Defines an Episode belonging to a {@link Podcast}.
 */
public class Episode {
    /**
     * Stores info. of the podcast under which this Episode belongs.
     */
    private Podcast podcast;

    // Stores the episode title.
    private String headline;

    // Stores brief info. about this episode.
    private String standFirst;

    // Stores info. who are present during the episode.
    private String byLine;

    /**
     * Stores then date when this episode was recorded.
     * <p>
     * Date Format - {@link JsonUtilsConstants#PATTERN_PODCAST}
     */
    private String date;

    // Stores a URL in String format that points to thumbnail for this episode.
    private String thumbnailUrl;

    /**
     * Stores a URL in String format that locates the episode in "The Guardian" website.
     */
    private String episodeUrl;

    // Stores the layout in which this feed gets displayed.
    private int viewType = EpisodeAdapter.EPISODE_DEFAULT;

    /**
     * Stores status whether a CardView item in {@link EpisodeAdapter} is EXPANDED or COLLAPSED.
     * <p>
     * By default every item in Adapter is set to COLLAPSED.
     */
    private boolean expanded = false;

    /**
     * Sets the podcast info. under which the episode belongs.
     */
    public void setPodcast(Podcast podcast) {
        this.podcast = podcast;
    }

    /**
     * Sets the headline for this episode.
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * Sets brief details for this episode.
     */
    public void setStandFirst(String standFirst) {
        this.standFirst = standFirst;
    }

    /**
     * Sets info. about the people present in the episode.
     */
    public void setByLine(String byLine) {
        this.byLine = byLine;
    }

    /**
     * Sets the date when the episode was recorded.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the thumbnail URL for this episode.
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * Sets a URL in string format to access the episode in "The Guardian" website.
     */
    public void setEpisodeUrl(String episodeUrl) {
        this.episodeUrl = episodeUrl;
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
     * Sets the view type of the episode/podcast intro.
     */
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    /**
     * @return Info. about the Podcast under which the episode belongs.
     */
    public Podcast getPodcast() {
        return podcast;
    }

    /**
     * @return The episodes' headline.
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * @return Brief details about the episode.
     */
    public String getStandFirst() {
        return standFirst;
    }

    /**
     * @return Info. about the people present in the episode.
     */
    public String getByLine() {
        return byLine;
    }

    /**
     * @return Date when the episode was recorded.
     */
    public String getDate() {
        return date;
    }

    /**
     * @return Episodes' thumbnail url.
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * @return URL in String format that locates the episode in "The Guardian" website.
     */
    public String getEpisodeUrl() {
        return episodeUrl;
    }

    /**
     * @return Status whether CardView item is EXPANDED.
     */
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * @return Layout in which the episode/podcast about detail gets displayed.
     */
    public int getViewType() {
        return viewType;
    }
}