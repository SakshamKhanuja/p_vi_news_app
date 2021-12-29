package com.project.news_app;

import androidx.annotation.NonNull;

/**
 * Defines a single News item.
 */
public class News {
    // Stores the news headline.
    private String headline;

    // Stores the section name under which this News is listed.
    private String sectionName;

    // Stores the news url that opens up the article in "The Guardian" website.
    private String articleURL;

    // Stores more info. this news.
    private String body;

    // Stores the name of the writer.
    private String byLine;

    // Stores the date when this news was published. Date format - (EEE, MMM dd at hh:mm a).
    private String date;

    // Stores the image url.
    private String thumbnail;

    /**
     * Sets the headline for this News.
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * Sets the section under which this News is listed.
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * Sets the article URL for this News.
     */
    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    /**
     * Sets the body text for this News.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Sets the author info. for this News.
     */
    public void setByLine(String byLine) {
        this.byLine = byLine;
    }

    /**
     * Sets the published for this News.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the thumbnail URL for this News.
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @NonNull
    @Override
    public String toString() {
        return "News{" +
                "headline='" + headline + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", articleURL='" + articleURL + '\'' +
                ", body='" + body + '\'' +
                ", byLine='" + byLine + '\'' +
                ", date='" + date + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}