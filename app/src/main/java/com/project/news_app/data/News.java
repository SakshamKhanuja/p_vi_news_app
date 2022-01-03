package com.project.news_app.data;

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
     * @return Article's thumbnail;
     */
    public String getThumbnail() {
        return thumbnail;
    }
}