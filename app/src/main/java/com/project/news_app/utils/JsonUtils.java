package com.project.news_app.utils;

import android.util.Log;

import androidx.core.text.HtmlCompat;

import com.project.news_app.adapters.EpisodeAdapter;
import com.project.news_app.data.Episode;
import com.project.news_app.data.News;
import com.project.news_app.constants.JsonUtilsConstants;
import com.project.news_app.data.Podcast;
import com.project.news_app.fragments.PodcastFragment;

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
     * @return ArrayList containing news info.
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
                        mNews.setThumbnailUrl(fields.optString(PRIMITIVE_THUMBNAIL, EMPTY));
                    } else {
                        /*
                         * Setting news headline, by-line, publication, thumbnail URL and body as
                         * val. EMPTY.
                         */
                        mNews.setHeadline(EMPTY);
                        mNews.setByLine(EMPTY);
                        mNews.setPublication(EMPTY);
                        mNews.setThumbnailUrl(EMPTY);
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
     * Converts (String) date in [ISO-8601 Date and time in UTC] format to a (String) data
     * having a custom pattern.
     *
     * @param publicationDate (String) Date when news/podcast feed was published on "The Guardian"
     *                        api in [ISO-8601 Date and time in UTC] format.
     *                        <br/>
     *                        Example - 2021-12-03T10:15:30Z.
     * @param pattern         Formats the publication data to a desired pattern.
     * @return Formatted publication date.
     * @see <a href="https://en.wikipedia.org/wiki/ISO_8601">ISO 8601</a>
     */
    private static String formatDate(String publicationDate, String pattern) {
        Instant instant = Instant.parse(publicationDate);
        Date date = new Date(instant.getEpochSecond() * 1000);

        // Parsing to Indian Local Time (UTC+5:30)
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, new Locale("eng",
                "IN"));

        // Set date for News.
        return dateFormat.format(date);
    }

    /**
     * Removes HTML tags and trims the entire string.
     */
    private static String removeHtmlTags(String string) {
        if (!string.equals(EMPTY)) {
            return HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim();
        } else {
            return string;
        }
    }

    /**
     * Parses JSON response from one of "The Guardian" API Endpoint to an ArrayList of type
     * {@link Episode}.
     * <p>
     * Adds the clicked {@link Podcast} info. at index 0 of the list to setup
     * {@link EpisodeAdapter}.
     *
     * @param jsonResponse   Response from the API in a single String.
     * @param clickedPodcast Contains info. about the {@link Podcast} that was clicked in
     *                       {@link PodcastFragment}.
     * @return ArrayList containing episodes of a podcast.
     */
    public static ArrayList<Episode> parsePodcastList(String jsonResponse,
                                                      Podcast clickedPodcast) {
        // Initializing list of type Episode.
        ArrayList<Episode> episodes = new ArrayList<>();

        try {
            // Initialize JSONObject using "jsonResponse" and set it to the root position.
            JSONObject root = new JSONObject(jsonResponse);

            // Traverse to JSONObject having key "response".
            JSONObject response = root.getJSONObject(OBJECT_RESPONSE);

            // Traverse to JSONObject having key "tag".
            JSONObject tag = response.getJSONObject(OBJECT_TAG);

            // Adds String URL to access the clicked Podcast in "The Guardian" website.
            clickedPodcast.setWebUrl(tag.optString(PRIMITIVE_WEB_URL, EMPTY));

            // Adds description about the podcast.
            clickedPodcast.setDescription(removeHtmlTags(tag.optString(PRIMITIVE_DESCRIPTION,
                    EMPTY)));

            // Traverse to JSONObject having key "podcast".
            JSONObject podcastObj = tag.getJSONObject(OBJECT_PODCAST);

            // Adds the "Apple Podcast" URL in String format.
            clickedPodcast.setApplePodcastUrl(podcastObj.optString(PRIMITIVE_PODCAST_APPLE,
                    EMPTY));

            // Adds status whether this podcast contains explicit topics or not.
            clickedPodcast.setExplicit(podcastObj.optBoolean(PRIMITIVE_EXPLICIT, false));

            // Stores the "Google Podcast" URL in String format.
            clickedPodcast.setGooglePodcastUrl(podcastObj.optString(PRIMITIVE_PODCAST_GOOGLE,
                    EMPTY));

            // Stores the "Spotify" URL in String format.
            clickedPodcast.setSpotifyUrl(podcastObj.optString(PRIMITIVE_PODCAST_SPOTIFY, EMPTY));

            // Adding info. about the clicked podcast as the first item in the list of episodes.
            Episode aboutPodcast = new Episode();
            aboutPodcast.setViewType(EpisodeAdapter.PODCAST_ABOUT);
            aboutPodcast.setPodcast(clickedPodcast);
            episodes.add(aboutPodcast);

            // Traverse to JSONArray having key "leadContent".
            JSONArray content = response.optJSONArray(ARRAY_CONTENT);

            // Iterating through "leadContent".
            if (content != null) {
                for (int i = 0; i < content.length(); i++) {
                    // Initialize JSONObject in JSONArray.
                    JSONObject jsonResultObject = content.getJSONObject(i);

                    // Initializes Episode object.
                    Episode episode = new Episode();

                    // Setting Episode date.
                    episode.setDate(formatDate(jsonResultObject.optString(PRIMITIVE_DATE),
                            PATTERN_PODCAST));

                    // Setting Episode URL.
                    episode.setEpisodeUrl(jsonResultObject.optString(PRIMITIVE_WEB_URL, EMPTY));

                    // Traverse to JSONObject having key "fields".
                    JSONObject fields = jsonResultObject.optJSONObject(OBJECT_FIELDS);

                    if (fields != null) {
                        // Setting Episode headline.
                        episode.setHeadline(fields.optString(PRIMITIVE_HEADLINE, EMPTY));

                        // Setting Episode info.
                        episode.setStandFirst(removeHtmlTags(fields.optString(
                                PRIMITIVE_ABOUT_EPISODE, EMPTY)));

                        // Setting Episode byline.
                        episode.setByLine(fields.optString(PRIMITIVE_BYLINE, EMPTY));

                        // Setting Episode thumbnail String URL.
                        episode.setThumbnailUrl(fields.optString(PRIMITIVE_THUMBNAIL, EMPTY));
                    } else {
                        /*
                         * Setting all the unavailable values ("fields" JSONObject) i.e "headline",
                         * "about", "by-line" and "thumbnail" to val. EMPTY.
                         */
                        episode.setHeadline(EMPTY);
                        episode.setStandFirst(EMPTY);
                        episode.setByLine(EMPTY);
                        episode.setThumbnailUrl(EMPTY);
                    }

                    // Adding episode to "episodes".
                    episodes.add(episode);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON - " + e.getMessage());
        }
        return episodes;
    }
}