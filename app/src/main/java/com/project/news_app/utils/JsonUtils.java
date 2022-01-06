package com.project.news_app.utils;

import android.util.Log;

import com.project.news_app.data.News;
import com.project.news_app.constants.JsonUtilsConstants;
import com.project.news_app.data.Podcast;

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
                    News mNews = new News();

                    // Setting News section.
                    mNews.setSectionName(jsonResultObject.optString(PRIMITIVE_SECTION, EMPTY));

                    // Setting News published date.
                    mNews.setDate(formatDate(jsonResultObject.optString(PRIMITIVE_DATE, EMPTY),
                            PATTERN_CATEGORY));

                    // Setting News article url.
                    mNews.setArticleURL(jsonResultObject.optString(PRIMITIVE_ARTICLE_URL, EMPTY));

                    // Traverse to JSONObject having key "fields".
                    JSONObject fields = jsonResultObject.optJSONObject(OBJECT_FIELDS);

                    if (fields != null) {
                        // Setting News headline.
                        mNews.setHeadline(fields.optString(PRIMITIVE_HEADLINE, EMPTY));

                        // Setting News article author info. (byline)
                        mNews.setByLine(fields.optString(PRIMITIVE_BYLINE, EMPTY));

                        // Setting News publication info.
                        mNews.setPublication(fields.optString(PRIMITIVE_PUBLICATION, EMPTY));

                        // Setting News article thumbnail String URL.
                        mNews.setThumbnail(fields.optString(PRIMITIVE_THUMBNAIL, EMPTY));
                    } else {
                        /*
                         * Setting news headline, by-line, publication, thumbnail URL and body as
                         * val. EMPTY.
                         */
                        mNews.setHeadline(EMPTY);
                        mNews.setByLine(EMPTY);
                        mNews.setPublication(EMPTY);
                        mNews.setThumbnail(EMPTY);
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
     * Converts (String) date in ISO-8601 format to a custom pattern.
     *
     * @param stringDate Date in ISO-8601 format.
     *                   <br/>
     *                   Example - 2021-12-03T10:15:30Z.
     * @param pattern    Pattern in which the date should be formatted.
     * @return Custom formatted (String) Date.
     */
    private static String formatDate(String stringDate, String pattern) {
        Instant instant = Instant.parse(stringDate);
        Date date = new Date(instant.getEpochSecond() * 1000);

        // Parsing to Indian Local Time (UTC+5:30)
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, new Locale("eng",
                "IN"));

        // Set date for News.
        return dateFormat.format(date);
    }

    /**
     * Parses JSON response from one of "The Guardian" API Endpoint to an ArrayList of type
     * {@link Podcast}
     *
     * @param jsonResponse Response from the API in a single String.
     * @return ArrayList containing all podcast info.
     */
    public static ArrayList<Podcast> parsePodcastList(String jsonResponse) {
        // Initializing list of type Podcast.
        ArrayList<Podcast> podcasts = new ArrayList<>();

        try {
            // Initialize JSONObject using "jsonResponse" and set it to the root position.
            JSONObject root = new JSONObject(jsonResponse);

            // Traverse to JSONObject having key "response".
            JSONObject response = root.getJSONObject(OBJECT_RESPONSE);

            // Traverse to JSONObject having key "tag".
            JSONObject tag = response.getJSONObject(OBJECT_TAG);

            // Traverse to JSONObject having key "podcast".
            JSONObject podcastObj = tag.getJSONObject(OBJECT_PODCAST);

            // Stores the "Google Podcast" URL in String format.
            String googlePodcastUrl = podcastObj.optString(PRIMITIVE_PODCAST_GOOGLE, EMPTY);

            // Stores the "Spotify" URL in String format.
            String spotifyUrl = podcastObj.optString(PRIMITIVE_PODCAST_SPOTIFY, EMPTY);

            // Traverse to JSONArray having key "leadContent".
            JSONArray content = response.optJSONArray(ARRAY_CONTENT);

            // Iterating through "results".
            if (content != null) {
                for (int i = 0; i < content.length(); i++) {
                    // Initialize JSONObject in JSONArray.
                    JSONObject jsonResultObject = content.getJSONObject(i);

                    // Initializes Podcast object.
                    Podcast podcast = new Podcast();

                    // Setting Podcast google url.
                    podcast.setGooglePodcastUrl(googlePodcastUrl);

                    // Setting Podcast spotify url.
                    podcast.setSpotifyUrl(spotifyUrl);

                    // Setting Podcast date.
                    podcast.setDate(formatDate(jsonResultObject.optString(PRIMITIVE_DATE),
                            PATTERN_PODCAST));

                    // Traverse to JSONObject having key "fields".
                    JSONObject fields = jsonResultObject.optJSONObject(OBJECT_FIELDS);

                    if (fields != null) {
                        // Setting Podcast headline.
                        podcast.setHeadline(fields.optString(PRIMITIVE_HEADLINE, EMPTY));

                        // Setting Podcast info.
                        podcast.setTrailText(fields.optString(PRIMITIVE_TRAIL, EMPTY));

                        // Setting Podcast thumbnail String URL.
                        podcast.setThumbnailUrl(fields.optString(PRIMITIVE_THUMBNAIL, EMPTY));
                    } else {
                        //Setting podcast's headline, trail-Text and thumbnail as val. EMPTY.
                        podcast.setHeadline(EMPTY);
                        podcast.setTrailText(EMPTY);
                        podcast.setThumbnailUrl(EMPTY);
                    }

                    // Adding podcast to "podcasts".
                    podcasts.add(podcast);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON - " + e.getMessage());
        }
        return podcasts;
    }
}