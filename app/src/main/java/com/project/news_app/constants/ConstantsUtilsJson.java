package com.project.news_app.constants;

import com.project.news_app.utils.UtilsJson;

/**
 * Contains Constants for {@link UtilsJson}.
 */
public interface ConstantsUtilsJson {
    // JSONObject having key "response".
    String OBJECT_RESPONSE = "response";

    // JSONArray having key "results".
    String ARRAY_RESULTS = "results";

    // JSON Primitive having key "sectionName".
    String PRIMITIVE_SECTION = "sectionName";

    // JSON Primitive having key "webPublicationDate".
    String PRIMITIVE_DATE = "webPublicationDate";

    // JSON Primitive having key "webUrl".
    String PRIMITIVE_ARTICLE_URL = "webUrl";

    // JSONObject having key "fields".
    String OBJECT_FIELDS = "fields";

    // JSON Primitive having key "headline".
    String PRIMITIVE_HEADLINE = "headline";

    // JSON Primitive having key "byline".
    String PRIMITIVE_BYLINE = "byline";

    // JSON Primitive having key "thumbnail".
    String PRIMITIVE_THUMBNAIL = "thumbnail";

    // JSON Primitive having key "bodyText".
    String PRIMITIVE_BODY = "bodyText";

    // Empty String.
    String EMPTY = "";

    // Used for Logs.
    String TAG = "JSONUtils";
}