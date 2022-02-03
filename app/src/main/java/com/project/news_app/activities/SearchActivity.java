package com.project.news_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.project.news_app.R;
import com.project.news_app.adapters.NewsAdapter;
import com.project.news_app.constants.NetworkUtilsConstants;
import com.project.news_app.constants.NewsAdapterConstants;
import com.project.news_app.data.News;
import com.project.news_app.databinding.ActivitySearchBinding;
import com.project.news_app.utils.CommonUtils;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Shows a list of {@link News} based on query provided from an TextInputEditText.
 */
public class SearchActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<News>> {
    /**
     * Performs View Binding.
     */
    private ActivitySearchBinding binding;

    /**
     * Checks for connection availability.
     */
    private ConnectivityManager connectivityManager;

    /**
     * Stores the query.
     */
    private String query;

    /**
     * Adapter provides {@link News} items to RecyclerView.
     */
    private NewsAdapter adapter;

    /**
     * Accesses the term provided by the user to search across configuration changes.
     */
    private static final String KEY_SEARCH = "query";

    /**
     * Notifies user when app loses/gains internet connectivity using a {@link Snackbar}.
     */
    private ConnectivityManager.NetworkCallback networkCallback;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Perform operations when SearchActivity is started for the first time.
        if (savedInstanceState == null) {
            // Default search query.
            query = getString(R.string.default_search_query);
        } else {
            // Restoring the search query.
            query = savedInstanceState.getString(KEY_SEARCH);
        }

        // Setting content view.
        binding = ActivitySearchBinding.inflate((LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        // Replace Toolbar as ActionBar.
        setSupportActionBar(binding.toolbarSearch);

        // Show the "Up" Button on Toolbar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initializing ConnectivityManager.
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initializing NetworkCallback.
        networkCallback = CommonUtils.getNetworkCallback(this,
                binding.searchCoordinatorLayout, getLifecycle());

        // Registering NetworkCallback.
        connectivityManager.registerDefaultNetworkCallback(networkCallback);

        // Initializing LoaderManager.
        LoaderManager loaderManager = LoaderManager.getInstance(this);

        // Attaching a listener which will hide the keyboard as soon as user touches the layout.
        binding.recyclerViewSearch.setOnTouchListener((v, event) -> {
            hideKeyboard();
            return false;
        });

        // Attaching a OnClickListener to TextInputEditText which enables the cursor.
        binding.editTextSearch.setOnClickListener(view ->
                binding.editTextSearch.setCursorVisible(true));

        /*
         * Attaching a listener to TextInputEditText which gets a callback whenever the user
         * presses the ENTER key on the keyboard.
         *
         * Restarts the Loader to download news based on new query.
         */
        binding.editTextSearch.setOnEditorActionListener((v, actionId, event) -> {

            // Checks the if (Search) key is clicked.
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Hides the keyboard.
                hideKeyboard();

                // Disables the
                binding.editTextSearch.setCursorVisible(false);

                // Getting the entered query from EditText.
                Editable editable = binding.editTextSearch.getText();
                if (editable != null) {
                    String query = editable.toString();
                    if (TextUtils.isEmpty(query)) {
                        // Notify user entered query is empty via Toast.
                        CommonUtils.showToast(SearchActivity.this,
                                R.string.toast_query_unavailable);
                    } else {
                        // Trimming the entered query.
                        query = query.trim();

                        // Checks if entered query is NOT same as the previous query.
                        if (!(SearchActivity.this.query.equalsIgnoreCase(query))) {
                            // Updating the query.
                            SearchActivity.this.query = query;

                            // Emptying the adapter.
                            adapter.setNewsData(null);

                            // Scrolls the RecyclerView back to top.
                            binding.recyclerViewSearch.scrollToPosition(0);

                            // Hide the "No Result Found" TextView.
                            hideEmptyView();

                            // Show Progress Bar.
                            binding.progressBarSearch.setVisibility(View.VISIBLE);

                            // Restarting Loader.
                            loaderManager.restartLoader(22, null, SearchActivity.this);
                        }
                    }
                }
                return true;
            }
            return false;
        });

        // Initializing Adapter.
        adapter = new NewsAdapter(this, null);

        // Setting up RecyclerView.
        CommonUtils.setupRecyclerView(this, binding.recyclerViewSearch, adapter,
                LinearLayoutManager.VERTICAL);

        // Downloading searched news data in a background Thread.
        loaderManager.initLoader(22, null, this);
    }

    /**
     * Hides the soft keyboard.
     */
    private void hideKeyboard() {
        // View which is currently being in focus in layout.
        View focus = getCurrentFocus();

        if (focus != null) {
            // Hides the soft keyboard.
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up the user provided query.
        outState.putString(KEY_SEARCH, query);
    }

    /**
     * Hides the empty view.
     */
    private void hideEmptyView() {
        binding.statusDataNotAvailableSearch.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregistering NetworkCallback.
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    /**
     * Hides indeterminate {@link R.id#progressBar_search}.
     */
    private void hideProgressBar() {
        binding.progressBarSearch.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<News>>(this) {

            // Stores the downloaded searched news data.
            private ArrayList<News> news;

            @Override
            protected void onStartLoading() {
                // Checks if cached (previously downloaded) searched news data is available.
                if (news != null && news.size() > 0) {
                    // Use cached result.
                    deliverResult(news);
                } else {
                    // Starts a background thread to download new searched news data.
                    forceLoad();
                }
            }

            /**
             * Creates search news item URL that points to "The Guardian" API's Content Endpoint.
             */
            private URL getSearchUrl() {
                URL url = NetworkUtils.makeNewsUrl(SearchActivity.this,
                        NetworkUtilsConstants.PATH_SEARCH, NetworkUtilsConstants.QP_VALUE_FIELDS,
                        100);

                // Adding "query" to "url".
                Uri uri = Uri.parse(url.toString());
                uri = uri.buildUpon()
                        .appendQueryParameter(NetworkUtilsConstants.QP_KEY_SEARCH, query)
                        .build();

                try {
                    // Updating search url.
                    return new URL(uri.toString());
                } catch (MalformedURLException e) {
                    return null;
                }
            }

            @NonNull
            @Override
            public ArrayList<News> loadInBackground() {
                // Stores downloaded searched news data.
                ArrayList<News> searchedNews;

                // Downloads searched items from "The Guardian" API's Content Endpoint.
                String jsonResponse = NetworkUtils.downloadNewsData(getSearchUrl());

                // Parses JSON response to a list of type News.
                searchedNews = JsonUtils.parseNewsList(jsonResponse);

                // Checks if news data is available before setting view type.
                if (searchedNews.size() > 0) {
                    for (News item : searchedNews) {
                        item.setViewType(NewsAdapterConstants.TYPE_TWELVE);
                    }
                }
                return searchedNews;
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

        if (data != null && data.size() > 0) {
            // Hide status TextView.
            hideEmptyView();

            // Updating the contents of NewsAdapter.
            adapter.setNewsData(data);
        } else {
            // Show status TextView.
            binding.statusDataNotAvailableSearch.setText(getString(R.string.search_failed));
            binding.statusDataNotAvailableSearch.setVisibility(View.VISIBLE);

            // Clearing up the NewsAdapter.
            adapter.setNewsData(null);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        // Clearing up the NewsAdapter.
        adapter.setNewsData(null);
    }
}