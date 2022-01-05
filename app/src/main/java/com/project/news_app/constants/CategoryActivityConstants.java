package com.project.news_app.constants;

import com.project.news_app.data.News;
import com.project.news_app.adapters.NewsAdapter;
import com.project.news_app.activities.CategoryActivity;

/**
 * Contains constants for {@link CategoryActivity}.
 */
public interface CategoryActivityConstants extends NewsAdapterConstants {
    // String extra contains the clicked news category's section API endpoint string url.
    String EXTRA_STRING_URL = "com.project.news_app.StringUrl";

    // String extra contains the clicked news category's title.
    String EXTRA_TITLE = "com.project.news_app.Title";

    // Key accesses the category news String URL across orientation changes.
    String KEY_CATEGORY_STRING_URL = "category_string_url";

    // Key accesses the news category title.
    String KEY_CATEGORY_TITLE = "category_title";

    // App starts downloading news info from this page in "The Guardian" api.
    int FIRST_PAGE = 1;

    // App stops downloading news info after this page in "The Guardian" api.
    int LAST_PAGE = 8;

    /**
     * Pattern - I
     * <br/>
     * Stores view types of 10 {@link News} items displayed by {@link NewsAdapter}.
     */
    int[] PATTERN_ONE = {TYPE_FIVE, TYPE_ONE, TYPE_ONE, TYPE_ONE, TYPE_TWO, TYPE_THREE,
            TYPE_FOUR, TYPE_FOUR, TYPE_FOUR, TYPE_FOUR};

    /**
     * Pattern - II
     * <br/>
     * Stores view types of 10 {@link News} items displayed by {@link NewsAdapter}.
     */
    int[] PATTERN_TWO = {TYPE_FIVE, TYPE_ONE, TYPE_TWO, TYPE_THREE, TYPE_FOUR, TYPE_FOUR,
            TYPE_FIVE, TYPE_ONE, TYPE_ONE, TYPE_TWO};

    /**
     * Pattern - III
     * <br/>
     * Stores view types of 10 {@link News} items displayed by {@link NewsAdapter}.
     */
    int[] PATTERN_THREE = {TYPE_THREE, TYPE_FOUR, TYPE_FOUR, TYPE_FIVE, TYPE_ONE, TYPE_ONE,
            TYPE_TWO, TYPE_FOUR, TYPE_FOUR, TYPE_FIVE};

    /**
     * Pattern - IV
     * <br/>
     * Stores view types of 10 {@link News} items displayed by {@link NewsAdapter}.
     */
    int[] PATTERN_FOUR = {TYPE_THREE, TYPE_FOUR, TYPE_FOUR, TYPE_FIVE, TYPE_FIVE, TYPE_ONE,
            TYPE_ONE, TYPE_ONE, TYPE_ONE, TYPE_TWO};

    /**
     * Pattern - V
     * <br/>
     * Stores view types of 10 {@link News} items displayed by {@link NewsAdapter}.
     */
    int[] PATTERN_FIVE = {TYPE_FIVE, TYPE_FIVE, TYPE_ONE, TYPE_TWO, TYPE_THREE, TYPE_FOUR,
            TYPE_FOUR, TYPE_FOUR, TYPE_FIVE, TYPE_TWO};

    /**
     * Pattern - VI
     * <br/>
     * Stores view types of 10 {@link News} items displayed by {@link NewsAdapter}.
     */
    int[] PATTERN_SIX = {TYPE_THREE, TYPE_THREE, TYPE_THREE, TYPE_FIVE, TYPE_TWO, TYPE_FIVE,
            TYPE_ONE, TYPE_TWO, TYPE_THREE, TYPE_FOUR};
}