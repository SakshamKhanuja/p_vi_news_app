package com.project.news_app.data;

import java.util.ArrayList;

import com.project.news_app.activities.CategoryActivity;

/**
 * Defines a single news feed.
 */
public class NewsFeed {
    // Stores the news feed label.
    private String label;

    /**
     * Stores the feed title which is shown as title of {@link CategoryActivity} when user clicks
     * the "See More" TextView.
     */
    private String title;

    // Stores the path to access the section endpoint of "The Guardian" API.
    private String path;

    // Stores the view type for this feed.
    private int type;

    // Stores news based on path.
    private ArrayList<News> news;

    /**
     * Sets the label for this news feed.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Sets the title for this news feed.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the path for this news feed.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Sets the view type for this news feed.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Adds {@link News} items to this news feed.
     */
    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    /**
     * @return News feed's label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return News feed's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return News feed's path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @return News feed's type.
     */
    public int getType() {
        return type;
    }

    /**
     * @return News feed's contents.
     */
    public ArrayList<News> getNews() {
        return news;
    }
}