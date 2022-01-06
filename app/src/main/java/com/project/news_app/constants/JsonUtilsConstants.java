package com.project.news_app.constants;

import com.project.news_app.utils.JsonUtils;
import com.project.news_app.activities.CategoryActivity;
import com.project.news_app.fragments.PodcastFragment;

/**
 * Contains constants for {@link JsonUtils}.
 */
public interface JsonUtilsConstants {
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

    // JSON Primitive having key "publication".
    String PRIMITIVE_PUBLICATION = "publication";

    // JSON Primitive having key "thumbnail".
    String PRIMITIVE_THUMBNAIL = "thumbnail";

    // JSONObject having key "tag".
    String OBJECT_TAG = "tag";

    // JSONObject having key "podcast".
    String OBJECT_PODCAST = "podcast";

    // JSON Primitive having key "googlePodcastsUrl".
    String PRIMITIVE_PODCAST_GOOGLE = "googlePodcastsUrl";

    // JSON Primitive having key "spotifyUrl".
    String PRIMITIVE_PODCAST_SPOTIFY = "spotifyUrl";

    // JSONArray having key "leadContent".
    String ARRAY_CONTENT = "leadContent";

    // JSON Primitive having key "trailText".
    String PRIMITIVE_TRAIL = "trailText";

    // Empty String.
    String EMPTY = "";

    // Used for Logs.
    String TAG = "JSONUtils";

    /**
     * Pattern for formatting Date in {@link CategoryActivity}
     */
    String PATTERN_CATEGORY = "EEE d',' MMM yyyy";

    /**
     * Pattern for formatting Date in {@link PodcastFragment}.
     */
    String PATTERN_PODCAST = "MMM dd, yyyy";
}