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
import com.project.news_app.databinding.BasicRecyclerViewLightBinding;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;
import com.project.news_app.fragments.CategoryFragment;

import java.util.ArrayList;

/**
 * Activity shows news category clicked in {@link CategoryFragment} Fragment.
 */
public class CategoryActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<News>>, CategoryActivityConstants {

    // Stores the path that locates the clicked News category in "The Guardian" API.
    private String mPath;

    // Stores the title of the clicked news category.
    private String mTitle;

    // Adapter provides News items to RecyclerView.
    private NewsAdapter mAdapter;

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
                mPath = intent.getStringExtra(EXTRA_PATH);
                mTitle = intent.getStringExtra(EXTRA_TITLE);
            }
        } else {
            // Restoring clicked news title and path.
            mPath = savedInstanceState.getString(KEY_PATH);
            mTitle = savedInstanceState.getString(KEY_TITLE);
        }

        // Setting content view.
        BasicRecyclerViewLightBinding binding =
                BasicRecyclerViewLightBinding.inflate((LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        // Set AppBar's title to the clicked news category's title.
        setTitle(mTitle);

        // Linking LayoutManager to RecyclerView.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewLight.setLayoutManager(linearLayoutManager);

        // Optimizes RecyclerView.
        binding.recyclerViewLight.setHasFixedSize(true);

        // Linking Adapter to RecyclerView.
        mAdapter = new NewsAdapter(null);
        binding.recyclerViewLight.setAdapter(mAdapter);

        // Downloading clicked news category data in a background Thread.
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up the clicked news category's path.
        outState.putString(KEY_PATH, mPath);

        // Backing up the clicked news category title.
        outState.putString(KEY_TITLE, mTitle);
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
                        NetworkUtils.makeNewsUrl(CategoryActivity.this, mPath,
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
             * Sets view type for all {@link News} items stored in the list.<br/>
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
            mAdapter.setNewsData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        // Clearing up the NewsAdapter.
        mAdapter.setNewsData(null);
    }
}