package com.project.news_app.constants;

import com.project.news_app.activities.CategoryActivity;

/**
 * Contains constants for {@link CategoryActivity}.
 */
public interface CategoryActivityConstants {
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
}
