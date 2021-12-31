package com.project.news_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.project.news_app.R;
import com.project.news_app.constants.CategoryActivityConstants;
import com.project.news_app.data.News;
import com.project.news_app.databinding.LayoutCategoryBinding;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<News>>, CategoryActivityConstants {

    // Stores the URL in String format of the clicked news category.
    private String mNewsStringUrl;

    // Stores the title of the clicked news category.
    private String mNewsCategoryTitle;

    // Performs View Binding.
    private LayoutCategoryBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Perform operations when CategoryActivity is started for the first time.
        if (savedInstanceState == null) {
            /*
             * Get Intent that started this Activity to get the clicked news section API endpoint
             * string URL.
             */
            Intent intent = getIntent();

            // Get the clicked news title and String URL.
            if (!(intent != null && intent.hasExtra(EXTRA_STRING_URL) && intent.hasExtra(
                    EXTRA_TITLE))) {
                // Control goes back to CategoryFragment.
                return;
            } else {
                mNewsStringUrl = intent.getStringExtra(EXTRA_STRING_URL);
                mNewsCategoryTitle = intent.getStringExtra(EXTRA_TITLE);
            }
        } else {
            // Restoring clicked news String URL and title.
            mNewsCategoryTitle = savedInstanceState.getString(KEY_CATEGORY_TITLE);
            mNewsStringUrl = savedInstanceState.getString(KEY_CATEGORY_STRING_URL);
        }

        // Setting content view.
        mBinding = LayoutCategoryBinding.inflate((LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE));
        setContentView(mBinding.getRoot());

        // Set AppBar's title to the clicked news category's title.
        setTitle(mNewsCategoryTitle);

        // Downloading clicked news category data in a background Thread.
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up the clicked news category String URL.
        outState.putString(KEY_CATEGORY_STRING_URL, mNewsStringUrl);

        // Backing up the clicked news category title.
        outState.putString(KEY_CATEGORY_TITLE, mNewsCategoryTitle);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // The "Up" Button functions as "Back" Button.
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<News>>(this) {

            // Stores the downloaded clicked news category.
            private ArrayList<News> news;

            @Override
            protected void onStartLoading() {
                if (news != null) {
                    // Use cached result.
                    deliverResult(news);
                } else {
                    // Starts a background thread to download news info.
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public ArrayList<News> loadInBackground() {
                // Stores the list containing news info.
                ArrayList<News> news = null;
                try {

                    // Logging URL.
                    Log.v(TAG, "URL - " + mNewsStringUrl);

                    // Download JSON response.
                    String jsonResponse = NetworkUtils.downloadNewsData(new URL(mNewsStringUrl));

                    // Parse JSON response to a list of type News.
                    news = JsonUtils.parseNewsList(jsonResponse);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "Cannot form URL - " + e.getMessage());
                }
                return news;
            }

            @Override
            public void deliverResult(@Nullable ArrayList<News> data) {
                // Caching downloaded news info.
                if (data != null) {
                    news = data;
                }
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {
        if (data != null && data.size() > 0) {
            // Putting contents of "data" into a Single string.
            StringBuilder builder = new StringBuilder();

            // Looping through "data".
            for (News item : data) {
                builder.append(item.toString()).append("\n\n");
            }

            // Setting this data to a single scrollable TextView.
            mBinding.textNewsCategory.setText(builder.toString());
        } else {
            // Informing user no data is available.
            mBinding.textNewsCategory.setText(getString(R.string.label_no_data_available));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        // Clearing the TextView.
        mBinding.textNewsCategory.setText(EMPTY);
    }
}