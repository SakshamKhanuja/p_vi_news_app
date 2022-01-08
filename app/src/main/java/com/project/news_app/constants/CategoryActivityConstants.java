package com.project.news_app.constants;

import com.project.news_app.data.News;
import com.project.news_app.adapters.NewsAdapter;
import com.project.news_app.activities.CategoryActivity;

/**
 * Contains constants for {@link CategoryActivity}.
 */
public interface CategoryActivityConstants extends NewsAdapterConstants {
    /**
     * String extra contains the clicked news category's path that points to it's section in
     * "The Guardian" API.
     */
    String EXTRA_PATH = "com.project.news_app.Path";

    /**
     * String extra contains the clicked news category's title.
     */
    String EXTRA_TITLE = "com.project.news_app.Title";

    /**
     * Key accesses the category news String URL across orientation changes.
     */
    String KEY_PATH = "path";

    /**
     * Key accesses the news category title.
     */
    String KEY_TITLE = "title";

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