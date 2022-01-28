package com.project.news_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.project.news_app.adapters.NewsAdapter;
import com.project.news_app.constants.CategoryActivityConstants;
import com.project.news_app.constants.NetworkUtilsConstants;
import com.project.news_app.data.News;
import com.project.news_app.databinding.BasicRecyclerViewBinding;
import com.project.news_app.utils.CommonUtils;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Shows a list of {@link News} in RecyclerView in a VERTICAL orientation.
 */
public class CategoryActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<News>>, CategoryActivityConstants {

    /**
     * Stores the path that locates the clicked News category/title in "The Guardian" API.
     */
    private String path;

    /**
     * Stores the title of the clicked news category.
     */
    private String title;

    /**
     * Adapter provides {@link News} items to RecyclerView.
     */
    private NewsAdapter adapter;

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

            // Get the clicked news title and path.
            if (!(intent != null && intent.hasExtra(EXTRA_PATH) && intent.hasExtra(
                    EXTRA_TITLE))) {
                // Control goes back to CategoryFragment.
                return;
            } else {
                path = intent.getStringExtra(EXTRA_PATH);
                title = intent.getStringExtra(EXTRA_TITLE);
            }
        } else {
            // Restoring clicked news title and path.
            path = savedInstanceState.getString(KEY_PATH);
            title = savedInstanceState.getString(KEY_TITLE);
        }

        // Setting content view.
        BasicRecyclerViewBinding binding =
                BasicRecyclerViewBinding.inflate((LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        // Set AppBar's title to the clicked news category's title.
        setTitle(title);

        // Initializing Adapter.
        adapter = new NewsAdapter(this, null);

        // Setting up RecyclerView.
        CommonUtils.setupRecyclerView(this, binding.recyclerViewDark, adapter,
                LinearLayoutManager.VERTICAL);

        // Downloading clicked news category data in a background Thread.
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up the clicked news category's path.
        outState.putString(KEY_PATH, path);

        // Backing up the clicked news category title.
        outState.putString(KEY_TITLE, title);
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

            // Stores the downloaded clicked news category's feed.
            private ArrayList<News> news;

            @Override
            protected void onStartLoading() {
                // Checks if cached (previously downloaded) news feed is available.
                if (news != null && news.size() > 0) {
                    // Use cached result.
                    deliverResult(news);
                } else {
                    // Starts a background thread to download fresh news info.
                    forceLoad();
                }
            }

            @NonNull
            @Override
            public ArrayList<News> loadInBackground() {
                // Stores downloaded news info.
                ArrayList<News> newsFeed;

                // Downloads news feed from "The Guardian" API's Section Endpoint.
                String jsonResponse = NetworkUtils.downloadNewsData(
                        NetworkUtils.makeNewsUrl(CategoryActivity.this, path,
                                NetworkUtilsConstants.QP_VALUE_FIELDS, 100));

                // Parses JSON response to a list of type News.
                newsFeed = JsonUtils.parseNewsList(jsonResponse);

                // Checks if news data is available before setting view type.
                if (newsFeed.size() > 0) {
                    // Sets "viewType" off all elements in "newsFeed" ArrayList.
                    setViewType(newsFeed);
                }
                return newsFeed;
            }

            /**
             * Chooses a random pattern for listing news items.
             * <br/>
             * Available Patterns - 6.
             *
             * @return Integer array containing view types.
             */
            private int[] getNewsPattern() {
                // Picks a random number between 1 to 6.
                int type = (int) (Math.random() * 6 + 1);

                // Returns a random pattern.
                switch (type) {
                    case 2:
                        return PATTERN_TWO;
                    case 3:
                        return PATTERN_THREE;
                    case 4:
                        return PATTERN_FOUR;
                    case 5:
                        return PATTERN_FIVE;
                    case 6:
                        return PATTERN_SIX;
                    case 1:
                    default:
                        return PATTERN_ONE;
                }
            }

            /**
             * Sets view type for all {@link News} items stored in the list.
             * <br/>
             * View type is used by {@link NewsAdapter} to set custom item layout at every position.
             */
            private void setViewType(ArrayList<News> newsList) {
                // Stores different view types that will be applied to the download news list.
                int[] viewTypeArray = getNewsPattern();

                // Used as "index" to access contents of "viewTypeArray".
                int index = 0;

                // Lopping through the downloaded news items in order to set view types.
                for (int i = 0; i < newsList.size(); i++) {
                    News item = newsList.get(i);

                    // Changes pattern after every 10th news item.
                    if (i % 10 == 0) {
                        index = 0;
                        viewTypeArray = getNewsPattern();
                    }

                    // Set view type to news item.
                    item.setViewType(viewTypeArray[index]);
                    index++;
                }
            }

            @Override
            public void deliverResult(@Nullable ArrayList<News> data) {
                // Caching downloaded news feed.
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
            // Updating the contents of NewsAdapter.
            adapter.setNewsData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        // Clearing up the NewsAdapter.
        adapter.setNewsData(null);
    }
}