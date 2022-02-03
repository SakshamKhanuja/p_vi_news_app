package com.project.news_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.project.news_app.R;
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

    /**
     * Performs View Binding.
     */
    private BasicRecyclerViewBinding binding;

    /**
     * Checks for connection availability.
     */
    private ConnectivityManager connectivityManager;

    /**
     * Notifies user when app loses/gains internet connectivity using a {@link Snackbar}.
     */
    private ConnectivityManager.NetworkCallback networkCallback;

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
        binding = BasicRecyclerViewBinding.inflate((LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        // Hiding Logo.
        binding.textLogo.setVisibility(View.GONE);

        // Replace Toolbar as ActionBar.
        setSupportActionBar(binding.toolbar);

        // Set clicked news category as app bar title.
        setTitle(title);
        ActionBar actionBar = getSupportActionBar();

        // Show the "Up" Button on Toolbar.
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initializing SwipeRefreshLayout.
        CommonUtils.setRefreshLayoutColors(binding.swipeToRefresh);

        // Initializing LoaderManager.
        LoaderManager loaderManager = LoaderManager.getInstance(this);

        // Attaching listener.
        binding.swipeToRefresh.setOnRefreshListener(() ->
                loaderManager.restartLoader(LOADER_ID, null, this));

        // Initializing ConnectivityManager.
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Checks if app is NOT connected/connecting to internet.
        if (!CommonUtils.checkNetworkAvailability(connectivityManager)) {
            // Hide ProgressBar.
            hideProgressBar();

            // No news available.
            showEmptyView();
        }

        // Initializing NetworkCallback.
        networkCallback = CommonUtils.getNetworkCallback(this,
                binding.detailCoordinatorLayout, getLifecycle());

        // Registering NetworkCallback.
        connectivityManager.registerDefaultNetworkCallback(networkCallback);

        // Initializing Adapter.
        adapter = new NewsAdapter(this, null);

        // Setting up RecyclerView.
        CommonUtils.setupRecyclerView(this, binding.recyclerViewDark, adapter,
                LinearLayoutManager.VERTICAL);

        // Downloading clicked news category data in a background Thread.
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregistering NetworkCallback.
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    /**
     * Hides indeterminate {@link R.id#progressBar}.
     */
    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up the clicked news category's path.
        outState.putString(KEY_PATH, path);

        // Backing up the clicked news category title.
        outState.putString(KEY_TITLE, title);
    }

    /**
     * Indicates no {@link News} items are available.
     */
    private void showEmptyView() {
        CommonUtils.setText(binding.statusDataNotAvailable, getString(R.string.news_not_available));
    }

    /**
     * Hides the empty view.
     */
    private void hideEmptyView() {
        binding.statusDataNotAvailable.setVisibility(View.GONE);
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
        // Hiding ProgressBar.
        hideProgressBar();

        // Hiding SwipeRefreshLayout.
        if(binding.swipeToRefresh.isRefreshing()) {
            binding.swipeToRefresh.setRefreshing(false);
        }

        if (data != null && data.size() > 0) {
            // Hide status TextView.
            hideEmptyView();

            // Updating the contents of NewsAdapter.
            adapter.setNewsData(data);
        } else {
            // No news available.
            showEmptyView();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        // Clearing up the NewsAdapter.
        adapter.setNewsData(null);
    }
}