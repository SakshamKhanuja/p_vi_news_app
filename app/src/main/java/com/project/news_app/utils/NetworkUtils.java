package com.project.news_app.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.project.news_app.R;
import com.project.news_app.constants.NetworkUtilsConstants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Class responsible for creating a HTTP Network Request to download news feed from "The Guardian"
 * API Endpoint.
 *
 * @see <a href="https://open-platform.theguardian.com/documentation/">The Guardian API</a>
 */
public class NetworkUtils implements NetworkUtilsConstants {

    // Setting default Constructor to private.
    private NetworkUtils() {
    }

    /**
     * Forms a {@link URL} that points to "Section" endpoint of "The Guardian" API.
     *
     * @param context Sets the API Key.
     * @param path    Downloads feed from this section.
     * @param fields  Picks custom components from the selected section.
     * @param number  Number of items downloaded from the feed.
     * @return A custom {@link URL} that points to a custom news feed available from "The Guardian"
     * API.
     */
    public static URL makeNewsUrl(Context context, String path, String fields, int number) {
        // Initializing URL.
        URL url = null;

        try {
            // Building URL.
            Uri uri = Uri.parse(DOMAIN).buildUpon()
                    .encodedPath(path)
                    .appendQueryParameter(QP_KEY_FIELDS, fields)
                    .appendQueryParameter(QP_KEY_PAGE_SIZE, String.valueOf(number))
                    .appendQueryParameter(QP_KEY_API, context.getString(R.string.api_key))
                    .build();

            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Cannot form URL - " + e.getMessage());
        }
        return url;
    }

    /**
     * Connects to one of "The Guardian" API endpoint to download news info.
     *
     * @param url Points to one of "The Guardian" API Endpoints.
     * @return String containing the downloaded news info.
     */
    public static String downloadNewsData(URL url) {
        if (url != null) {
            /*
             * Forms a HTTP Network Request and establishes a Connection to one of the API
             * Endpoints provided by "The Guardian" API.
             */
            HttpURLConnection urlConnection = null;

            // Downloads stream of bytes from the web-servers of "The Guardian".
            InputStream inputStream = null;

            // Parses the downloaded stream of bytes to a single String.
            Scanner scanner = null;

            try {
                /*
                 * Forms a network connection based on "url". HTTP method is set to "GET" by
                 * default.
                 */
                urlConnection = (HttpURLConnection) url.openConnection();

                // Establishes Connection.
                urlConnection.connect();

                // Get the response code from the servers off of "The Guardian" Api.
                int responseCode = urlConnection.getResponseCode();

                // Checks if request is granted.
                if (responseCode == RESPONSE_CODE_OK) {
                    // Request Granted by the API. Downloading news info.
                    inputStream = urlConnection.getInputStream();

                    // Parse stream of data in bytes to a SINGLE String.
                    scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");

                    // Reads the available token.
                    return scanner.hasNext() ? scanner.next() : EMPTY;
                } else {
                    // Request Failed.
                    return parseResponseCode(responseCode);
                }
            } catch (IOException e) {
                Log.e(TAG, "Cannot create a network connection - " + e.getMessage());
            } finally {
                // Closes connection.
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                // Releases InputStream related memory resources.
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Cannot close InputStream - " + e.getMessage());
                    }
                }

                // Releases Scanner related memory resources.
                if (scanner != null) {
                    scanner.close();
                }
            }
        }

        // No data available.
        return EMPTY;
    }

    /**
     * Logs the response code result from the web-servers off of "The Guardian" API Endpoint.
     *
     * @param responseCode Determines the result of the app's request to download news information
     *                     from the "The Guardian" API.
     * @return An EMPTY String.
     */
    private static String parseResponseCode(int responseCode) {
        switch (responseCode) {
            case RESPONSE_CODE_BAD_REQUEST:
                Log.i(TAG, "Bad Request (" + responseCode + ")");
                return EMPTY;

            case RESPONSE_CODE_UNAUTHORIZED_REQUEST:
                Log.i(TAG, "Authentication Failed (" + responseCode + ")");
                return EMPTY;

            case RESPONSE_CODE_FORBIDDEN:
                Log.i(TAG, "Restricted Access (" + responseCode + ")");
                return EMPTY;

            case RESPONSE_CODE_NOT_FOUND:
                Log.i(TAG, "Not Found (" + responseCode + ")");
                return EMPTY;

            default:
                Log.e(TAG, "Error, Response Code - " + responseCode);
                return EMPTY;
        }
    }
}