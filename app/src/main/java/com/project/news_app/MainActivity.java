package com.project.news_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.project.news_app.constants.ConstantsActivityMain;
import com.project.news_app.utils.UtilsJson;
import com.project.news_app.utils.UtilsNetwork;

import java.net.URL;
import java.util.ArrayList;

/**
 * Stage II
 *
 * App parses the downloaded "sport" Section information to an ArrayList of type {@link News}.
 */
public class MainActivity extends AppCompatActivity implements Runnable, ConstantsActivityMain {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Performs a network operation.
        Thread backgroundThread = new Thread(this);
        // Calls the "run" method of the background thread.
        backgroundThread.start();
    }

    @Override
    public void run() {
        // Form a URL that points to the "sport" section.
        URL sportsURL = UtilsNetwork.makeURL(this, SECTION_SPORTS);

        // Connects to "The Guardian" API and downloads "sport" info.
        String jsonResponse = UtilsNetwork.downloadNewsData(sportsURL);

        // Parsing the downloaded response to an ArrayList of type News.
        ArrayList<News> news = UtilsJson.parseNewsList(jsonResponse);

        // Logging News.
        for(News item: news) {
            Log.v(TAG, item.toString());
        }
    }
}