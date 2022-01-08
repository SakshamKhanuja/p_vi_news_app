package com.project.news_app.constants;

import com.project.news_app.fragments.HeadlineFragment;

/**
 * Contains constants for {@link HeadlineFragment}.
 */
public interface HeadlineFragmentConstants {
    /**
     * Represents the title of news feed for the region "United States".
     */
    String TITLE_US = "US News";

    /**
     * Represents the title of news feed for the region "United Kingdom".
     */
    String TITLE_UK = "UK News";

    /**
     * Represents the title of news feed for the region "Australia".
     */
    String TITLE_AUS = "Australia News";

    /**
     * Represents the ID of Loader responsible for downloading US News feed.
     */
    int LOADER_US = 111;

    /**
     * Represents the ID of Loader responsible for downloading UK News feed.
     */
    int LOADER_UK = 112;

    /**
     * Represents the ID of Loader responsible for downloading Austraila News feed.
     */
    int LOADER_AUS = 113;
}