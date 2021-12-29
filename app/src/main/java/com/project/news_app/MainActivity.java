package com.project.news_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.project.news_app.constants.ConstantsActivityMain;
import com.project.news_app.utils.UtilsNetwork;

import java.net.URL;

/**
 * Stage I
 * <p>
 * App establishes a connection to the "Sections" API endpoint of "The Guardian". To check its
 * functioning, "sport" section is accessed and downloaded.
 * <p>
 * The downloaded response is then logged.
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
        Log.v(TAG, UtilsNetwork.downloadNewsData(sportsURL));
    }
}