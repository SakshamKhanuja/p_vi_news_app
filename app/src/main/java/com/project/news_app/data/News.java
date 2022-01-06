package com.project.news_app.data;

import com.project.news_app.constants.NewsAdapterConstants;
import com.project.news_app.constants.CategoryActivityConstants;
import com.project.news_app.constants.JsonUtilsConstants;

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

    // Stores the name of the writer.
    private String byLine;

    // Stores the publication of this article.
    private String publication;

    /**
     * Stores the date when this news was published.
     * <p>
     * Date Format - {@link JsonUtilsConstants#PATTERN_CATEGORY}
     */
    private String date;

    // Stores the image url.
    private String thumbnail;

    /**
     * Stores the layout in which news info. gets displayed.
     * <p>
     * Default number of news items present in a single page in "The Guardian" Section's API
     * Endpoint is 10, which matches the size of all hard-coded patterns in
     * {@link CategoryActivityConstants}.
     * <p>
     * In-case changes are made to the api end-point itself, and the default number
     * of news items per page turns to be greater than 10, items having index
     * greater than 10 are set have view type - {@link NewsAdapterConstants#TYPE_FOUR}.
     */
    private int viewType = NewsAdapterConstants.TYPE_FOUR;

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
     * Sets the author info. for this News.
     */
    public void setByLine(String byLine) {
        this.byLine = byLine;
    }

    /**
     * Sets the publication for this News.
     */
    public void setPublication(String publication) {
        this.publication = publication;
    }

    /**
     * Sets the publication date for this News.
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

    /**
     * Sets the layout in which all news info gets displayed.
     */
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    /**
     * @return The news headline.
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * @return The section under which this news belongs.
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @return The string URL pointing to this news article in "The Guardian" website.
     */
    public String getArticleURL() {
        return articleURL;
    }

    /**
     * @return Article's author(s).
     */
    public String getByLine() {
        return byLine;
    }

    /**
     * @return Article's publication.
     */
    public String getPublication() {
        return publication;
    }

    /**
     * @return Article's publication date.
     */
    public String getDate() {
        return date;
    }

    /**
     * @return Article's thumbnail.
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return Layout to show all news info.
     */
    public int getViewType() {
        return viewType;
    }
}