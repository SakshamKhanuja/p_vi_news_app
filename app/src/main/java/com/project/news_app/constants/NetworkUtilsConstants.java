package com.project.news_app.constants;

import com.project.news_app.utils.NetworkUtils;

/**
 * Contains constants for {@link NetworkUtils}.
 */
public interface NetworkUtilsConstants {
    /**
     * Used for Logs.
     */
    String TAG = "NetworkUtils";

    /**
     * Contains domain info. for "The Guardian".
     */
    String DOMAIN = "https://content.guardianapis.com";

    /**
     * Query parameter sets Api Key.
     */
    String QP_KEY_API = "api-key";

    /**
     * Query parameter adds fields to News.
     */
    String QP_KEY_FIELDS = "show-fields";

    /**
     * Query parameters sets the query to perform search operation.
     */
    String QP_KEY_SEARCH = "q";

    /**
     * Query value adds - headline, by-line, publication and thumbnail.
     */
    String QP_VALUE_FIELDS = "headline,byline,publication,thumbnail";

    /**
     * Query value adds - headline, thumbnail and by-line.
     */
    String QP_VALUE_HEADLINE_FIELDS = "headline,byline,thumbnail";

    /**
     * Query parameters sets the number of page items.
     */
    String QP_KEY_PAGE_SIZE = "page-size";

    /**
     * Query value adds - headline, trail-text, by-line and thumbnail.
     */
    String QP_VALUE_PODCAST = "headline,standfirst,byline,thumbnail";

    /**
     * Empty String.
     */
    String EMPTY = "";

    /**
     * Response Status - OK.
     */
    int RESPONSE_CODE_OK = 200;

    /**
     * Response Status - Server could not understand the request.
     */
    int RESPONSE_CODE_BAD_REQUEST = 400;

    /**
     * Response Status - Client hasn't provided any authentication.
     */
    int RESPONSE_CODE_UNAUTHORIZED_REQUEST = 401;
    /**
     * Response Status - Client has no access rights to the content.
     */
    int RESPONSE_CODE_FORBIDDEN = 403;

    /**
     * Response Status - Not Found.
     */
    int RESPONSE_CODE_NOT_FOUND = 404;

    /**
     * Path points to the "World News" section of "The Guardian" api.
     */
    String PATH_WORLD = "world";

    /**
     * Path points to the "US News" section of "The Guardian" api.
     */
    String PATH_US = "us-news";

    /**
     * Path points to the "UK News" section of "The Guardian" api.
     */
    String PATH_UK = "uk-news";

    /**
     * Path points to the "Australia News" section of "The Guardian" api.
     */
    String PATH_AUS = "australia-news";

    /**
     * Path points to the "Editorial" section of "The Guardian" api.
     */
    String PATH_GUARDIAN = "profile/editorial";

    /**
     * Path points to "Content" section of "The Guardian" api.
     */
    String PATH_SEARCH = "search";

    /**
     * Number of how many episodes under "Today in Focus" podcast to be downloaded.
     */
    int SIZE_PODCAST = 10;

    /**
     * Number of items in "fragment_headlines" news feed.
     */
    int SIZE_HEADLINES = 15;
}