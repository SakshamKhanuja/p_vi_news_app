package com.project.news_app.constants;

import com.project.news_app.activities.MainActivity;
import com.project.news_app.fragments.HeadlineFragment;
import com.project.news_app.fragments.CategoryFragment;
import com.project.news_app.fragments.PodcastFragment;

/**
 * Contains constants for {@link MainActivity}.
 */
public interface MainActivityConstants {
    /**
     * Represents the TAG set to retrieve {@link HeadlineFragment}.
     */
    String TAG_HEADLINE = "headline_fragment";

    /**
     * Represents the TAG set to retrieve {@link CategoryFragment}.
     */
    String TAG_CATEGORY = "category_fragment";

    /**
     * Represents the TAG set to retrieve {@link PodcastFragment}.
     */
    String TAG_PODCAST = "podcast_fragment";

    /**
     * Used to access the tag of the visible/active Fragment.
     */
    String KEY_TAG = "fragment_tag";
}