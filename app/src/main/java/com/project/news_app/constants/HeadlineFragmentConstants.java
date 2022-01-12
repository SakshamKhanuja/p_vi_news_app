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
     * View type for News feed inflated from {@link R.layout#layout_nested_recycler_item_light}.
     */
    int FEED_TYPE_LIGHT = 1;

    /**
     * View type for News feed inflated from {@link R.layout#layout_nested_recycler_item_dark}.
     */
    int FEED_TYPE_DARK = 2;
}