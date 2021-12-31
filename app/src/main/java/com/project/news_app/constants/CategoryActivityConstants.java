package com.project.news_app.constants;

import com.project.news_app.activities.CategoryActivity;
/**
 * Contains constants for {@link CategoryActivity}.
 */
public interface CategoryActivityConstants {
    // Used for Logs.
    String TAG = "CategoryActivity";

    // String extra contains the clicked news category's section API endpoint string url.
    String EXTRA_STRING_URL = "com.project.news_app.StringUrl";

    // String extra contains the clicked news category's title.
    String EXTRA_TITLE = "com.project.news_app.Title";

    // Key accesses the category news String URL across orientation changes.
    String KEY_CATEGORY_STRING_URL = "category_string_url";

    // Key accesses the news category title.
    String KEY_CATEGORY_TITLE = "category_title";

    // Empty String.
    String EMPTY = "";

    // Differentiates the Loader.
    int LOADER_ID = 19;
}
