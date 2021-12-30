package com.project.news_app.utils;

import android.util.Log;

import com.project.news_app.data.News;
import com.project.news_app.constants.JsonUtilsConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Class contains methods to parse the downloaded JSON response.
 */
public class JsonUtils implements JsonUtilsConstants {

    // Setting default Constructor to private.
    private JsonUtils() {
    }

    /**
     * Parses JSON response from one of "The Guardian" API Endpoint to an ArrayList of type
     * {@link News}.
     *
     * @param jsonResponse Response from the API in a single String.
     * @return ArrayList containing all news info.
     */
    public static ArrayList<News> parseNewsList(String jsonResponse) {

        // Initializing list of type News.
        ArrayList<News> news = new ArrayList<>();

        try {
            // Initialize JSONObject using "jsonResponse" and set it to the root position.
            JSONObject root = new JSONObject(jsonResponse);

            // Traverse to JSONObject having key "response".
            JSONObject response = root.getJSONObject(OBJECT_RESPONSE);

            // Traverse to JSONArray having key "results".
            JSONArray results = response.optJSONArray(ARRAY_RESULTS);

            // Iterating through "results".
            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    // Initialize JSONObject in JSONArray.
                    JSONObject jsonResultObject = results.getJSONObject(i);

                    // Initialize News object.
                    // Stores news info - headline, about, published date, thumbnail etc.
                    News mNews = new News();

                    // Setting News section.
                    mNews.setSectionName(jsonResultObject.optString(PRIMITIVE_SECTION, EMPTY));

                    // Setting News published date.
                    mNews.setDate(formatDate(jsonResultObject.optString(PRIMITIVE_DATE, EMPTY)));

                    // Setting News article url.
                    mNews.setArticleURL(jsonResultObject.optString(PRIMITIVE_ARTICLE_URL, EMPTY));

                    // Traverse to JSONObject having key "fields".
                    JSONObject fields = jsonResultObject.optJSONObject(OBJECT_FIELDS);

                    if (fields != null) {
                        // Setting News headline.
                        mNews.setHeadline(fields.optString(PRIMITIVE_HEADLINE, EMPTY));

                        // Setting News article author info. (byline)
                        mNews.setByLine(fields.optString(PRIMITIVE_BYLINE, EMPTY));

                        // Setting News article thumbnail String URL.
                        mNews.setThumbnail(fields.optString(PRIMITIVE_THUMBNAIL, EMPTY));

                        // Setting News article body text.
                        mNews.setBody(fields.optString(PRIMITIVE_BODY, EMPTY));
                    } else {
                        // Setting news headline, by-line, thumbnail URL and body as val. EMPTY.
                        mNews.setHeadline(EMPTY);
                        mNews.setByLine(EMPTY);
                        mNews.setThumbnail(EMPTY);
                        mNews.setBody(EMPTY);
                    }
                    // Adding mNews to "news".
                    news.add(mNews);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON - " + e.getMessage());
        }
        return news;
    }

    /**
     * Converts date in ISO-8601 format to a date having "EEE, MMM dd at hh:mm a" as its format.
     *
     * @param stringDate String contains a date in ISO-8601 format. Example - 2021-12-03T10:15:30Z.
     * @return String date having format "EEE, MMM dd at hh:mm a".
     * Example - Sun, Dec 03 at 10:15 AM.
     */
    private static String formatDate(String stringDate) {
        Instant instant = Instant.parse(stringDate);
        Date date = new Date(instant.getEpochSecond());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd 'at' hh:mm a",
                new Locale("eng", "IN"));

        // Set date for News.
        return dateFormat.format(date);
    }
}