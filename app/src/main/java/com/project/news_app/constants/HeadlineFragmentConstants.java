package com.project.news_app.constants;

import com.project.news_app.R;
import com.project.news_app.fragments.HeadlineFragment;

/**
 * Contains constants for {@link HeadlineFragment}.
 */
public interface HeadlineFragmentConstants {
    /**
     * Stores the path to access news feed from "The Guardian" API.
     */
    String[] pathArray = {NetworkUtilsConstants.PATH_WORLD, NetworkUtilsConstants.PATH_US,
            NetworkUtilsConstants.PATH_UK, NetworkUtilsConstants.PATH_AUS,
            NetworkUtilsConstants.PATH_GUARDIAN};

    /**
     * View type for News feed inflated from {@link R.layout#layout_nested_recycler_item_black}.
     */
    int FEED_TYPE_BLACK = 1;

    /**
     * View type for News feed inflated from {@link R.layout#layout_nested_recycler_item_dark}.
     */
    int FEED_TYPE_DARK = 2;

    /**
     * View type for News feed inflated from {@link R.layout#layout_top_feed}.
     */
    int FEED_TYPE_TOP = 3;

    /**
     * View type for News feed inflated from {@link R.layout#layout_social}.
     */
    int FEED_TYPE_SOCIAL = 4;

    /**
     * View type for News feed inflated from {@link R.layout#layout_discover}.
     */
    int FEED_TYPE_DISCOVER = 5;

    /**
     * View type for News feed inflated from {@link R.layout#layout_readers}.
     */
    int FEED_TYPE_READERS = 6;

    /**
     * Unique identifier for Loader.
     */
    int LOADER_ID = 12;

    /**
     * Points to "The Guardian" page on Facebook.
     */
    String FACEBOOK_URL = "https://www.facebook.com/theguardian";

    /**
     * Points to "The Guardian" page on Instagram.
     */
    String INSTAGRAM_URL = "https://www.instagram.com/guardian/";

    /**
     * Points to "The Guardian" page on LinkedIn.
     */
    String LINKEDIN_URL = "https://www.linkedin.com/company/theguardian";

    /**
     * Points to "The Guardian" page on Twitter.
     */
    String TWITTER_URL = "https://twitter.com/guardian";

    /**
     * Points to "The Guardian" page on YouTube.
     */
    String YOUTUBE_URL = "https://www.youtube.com/user/TheGuardian";

    /**
     * Points to "Guardian Puzzles & Crosswords" app on Play Store.
     */
    String DISCOVER_URL = "https://play.google.com/store/apps/details?id=uk.co.guardian.puzzles";

    /**
     * Points to "Guardian Weekly Global Community" on "The Guardian".
     */
    String READERS_URL = "https://www.theguardian.com/global/ng-interactive/2018/oct/12/guardian-weekly-community-map";
}