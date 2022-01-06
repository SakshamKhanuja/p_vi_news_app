package com.project.news_app.constants;

import com.project.news_app.utils.NetworkUtils;

/**
 * Contains constants for {@link NetworkUtils}.
 */
public interface NetworkUtilsConstants {
    // Used for Logs.
    String TAG = "NetworkUtils";

    // Contains domain info. for "The Guardian".
    String DOMAIN = "https://content.guardianapis.com";

    // Query parameter sets Api Key.
    String QP_KEY_API = "api-key";

    // Query parameter adds fields to News.
    String QP_KEY_FIELDS = "show-fields";

    // Path points to the "Today in Focus" section of "The Guardian" api.
    String PATH_FOCUS = "news/series/todayinfocus";

    // Query value adds - headline, by-line, and thumbnail.
    String QP_VALUE_FIELDS = "headline,byline,publication,thumbnail";

    // Query value adds - headline, trail-text, and thumbnail.
    String QP_VALUE_FIELDS_FOCUS = "headline,trailText,thumbnail";

    // Query parameter sets the current page number.
    String QP_KEY_PAGE = "page";

    // Empty String.
    String EMPTY = "";

    // Response Status - OK.
    int RESPONSE_CODE_OK = 200;

    // Response Status - Server could not understand the request.
    int RESPONSE_CODE_BAD_REQUEST = 400;

    // Response Code 400.
    String RESPONSE_400 = "Server couldn't understand request.";

    // Response Status - Client hasn't provided any authentication.
    int RESPONSE_CODE_UNAUTHORIZED_REQUEST = 401;

    // Response Code 401.
    String RESPONSE_401 = "Authentication required.";

    // Response Status - Client has no access rights to the content.
    int RESPONSE_CODE_FORBIDDEN = 403;

    // Response Code 403.
    String RESPONSE_403 = "Client is forbidden to access this content.";

    // Response Status - Not Found.
    int RESPONSE_CODE_NOT_FOUND = 404;

    // Response Code 404.
    String RESPONSE_404 = "Server could not find requested resource.";

    // Response Status - Error / Not Listed.
    String RESPONSE_UNKNOWN = "Error";
}