package com.project.news_app.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.project.news_app.R;
import com.project.news_app.constants.ConstantsUtilsNetwork;

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
public class UtilsNetwork implements ConstantsUtilsNetwork {

    // Setting default Constructor to private.
    private UtilsNetwork() {
    }

    /**
     * Forms a URL that points to "The Guardian" API's "Section" Endpoint.
     * <p>
     * Note - Add your API Key in {@link R.string#api_key} string resource.
     *
     * @param context Sets the API Key.
     * @param section Name of the section.
     * @return A URL to connect to a "The Guardian" API Endpoint.
     */
    public static URL makeURL(Context context, String section) {
        // Initializing URL.
        URL url = null;

        try {
            // Building URL.
            Uri uri = Uri.parse(PATH).buildUpon()
                    .appendPath(section)
                    .appendQueryParameter(QP_KEY_FIELDS, QP_VALUE_FIELDS)
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
     * @return String containing news info.
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
     * Logs and returns the response code result from the web-servers off of "The Guardian"
     * API Endpoint.
     *
     * @param responseCode Determines the result of the app's request to download news information
     *                     from the "The Guardian" API.
     * @return The result of the app's request in read-able terms.
     */
    private static String parseResponseCode(int responseCode) {
        switch (responseCode) {
            case RESPONSE_CODE_BAD_REQUEST:
                Log.i(TAG, "Bad Request (" + responseCode + ")");
                return RESPONSE_400;

            case RESPONSE_CODE_UNAUTHORIZED_REQUEST:
                Log.i(TAG, "Authentication Failed (" + responseCode + ")");
                return RESPONSE_401;

            case RESPONSE_CODE_FORBIDDEN:
                Log.i(TAG, "Restricted Access (" + responseCode + ")");
                return RESPONSE_403;

            case RESPONSE_CODE_NOT_FOUND:
                Log.i(TAG, "Not Found (" + responseCode + ")");
                return RESPONSE_404;

            default:
                Log.e(TAG, "Error, Response Code - " + responseCode);
                return RESPONSE_UNKNOWN;
        }
    }
}