package com.project.news_app.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Defines podcasts basic info.
 */
public class Podcast implements Parcelable {
    // Stores the String resource ID containing the title of the podcast.
    private final int title;

    // Stores the String resource ID containing the path to access the podcast from "The Guardian".
    private final int path;

    // Stores the Drawable resource ID containing the podcasts' thumbnail.
    private final int thumbnail;

    // Stores a URL in String format that locates the podcast in "The Guardian" website.
    private String webUrl;

    // Stores description about the Podcast.
    private String description;

    // Stores a URL in String format that locates the podcast in Spotify.
    private String spotifyUrl;

    // Stores a URL in String format that locates the podcast in Google Podcast.
    private String googlePodcastUrl;

    // Stores a URL in String format that locates the podcast in Apple Podcast.
    private String applePodcastUrl;

    // Stores whether this Podcast contains topics which are explicit in nature.
    private boolean explicit;

    public Podcast(int title, int path, int thumbnail) {
        this.title = title;
        this.path = path;
        this.thumbnail = thumbnail;
    }

    protected Podcast(Parcel in) {
        title = in.readInt();
        path = in.readInt();
        thumbnail = in.readInt();
        webUrl = in.readString();
        description = in.readString();
        spotifyUrl = in.readString();
        googlePodcastUrl = in.readString();
        applePodcastUrl = in.readString();
        explicit = in.readByte() != 0;
    }

    public static final Creator<Podcast> CREATOR = new Creator<Podcast>() {
        @Override
        public Podcast createFromParcel(Parcel in) {
            return new Podcast(in);
        }

        @Override
        public Podcast[] newArray(int size) {
            return new Podcast[size];
        }
    };

    /**
     * Sets the string URL that locates the podcast in "The Guardian" website.
     */
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    /**
     * Sets brief info. about the podcast.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the string URL that locates the podcast in Spotify.
     */
    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }

    /**
     * Sets the string URL that locates the podcast in Google Podcasts.
     */
    public void setGooglePodcastUrl(String googlePodcastUrl) {
        this.googlePodcastUrl = googlePodcastUrl;
    }

    /**
     * Sets the string URL that locates the podcast in Apple Podcasts.
     */
    public void setApplePodcastUrl(String applePodcastUrl) {
        this.applePodcastUrl = applePodcastUrl;
    }

    /**
     * Sets the status of the podcast on whether it contains explicit content or not.
     */
    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    /**
     * @return String resource ID containing the title of the podcast.
     */
    public int getTitle() {
        return title;
    }

    /**
     * @return String resource ID containing the path to access this podcast from "The Guardian"
     * API.
     */
    public int getPath() {
        return path;
    }

    /**
     * @return Podcast's thumbnail resource ID.
     */
    public int getThumbnail() {
        return thumbnail;
    }

    /**
     * @return URL in String format that locates this podcast in "The Guardian" website.
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * @return Brief info. of the podcast.
     */
    public String getDescription() {
        return description;
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
     * @return URL in String format that locates the podcast in Apple Podcast.
     */
    public String getApplePodcastUrl() {
        return applePodcastUrl;
    }

    /**
     * @return "true", if the podcast contains topics which are explicit in nature.
     */
    public boolean isExplicit() {
        return explicit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(title);
        dest.writeInt(path);
        dest.writeInt(thumbnail);
        dest.writeString(webUrl);
        dest.writeString(description);
        dest.writeString(spotifyUrl);
        dest.writeString(googlePodcastUrl);
        dest.writeString(applePodcastUrl);
        dest.writeByte((byte) (explicit ? 1 : 0));
    }
}