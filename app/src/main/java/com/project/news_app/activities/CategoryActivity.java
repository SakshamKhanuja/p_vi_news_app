package com.project.news_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.project.news_app.R;
import com.project.news_app.adapters.NewsAdapter;
import com.project.news_app.constants.CategoryActivityConstants;
import com.project.news_app.data.News;
import com.project.news_app.databinding.BasicRecyclerViewLayoutBinding;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;
import com.project.news_app.fragments.CategoryFragment;

import java.util.ArrayList;

/**
 * Activity shows news category clicked in {@link CategoryFragment} Fragment.
 */
public class CategoryActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<News>>, CategoryActivityConstants,
        NewsAdapter.NewsItemClickListener {

    // Stores the URL in String format of the clicked news category.
    private String mNewsStringUrl;

    // Stores the title of the clicked news category.
    private String mNewsCategoryTitle;

    // Adapter provides News items to RecyclerView.
    private NewsAdapter mAdapter;

    // Notifies the unavailability of Browser in user's app.
    private Toast mToast;

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
        BasicRecyclerViewLayoutBinding binding =
                BasicRecyclerViewLayoutBinding.inflate((LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        // Set AppBar's title to the clicked news category's title.
        setTitle(mNewsCategoryTitle);

        // Linking LayoutManager to RecyclerView.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        // Optimizes RecyclerView.
        binding.recyclerView.setHasFixedSize(true);

        // Linking Adapter to RecyclerView.
        mAdapter = new NewsAdapter(null, this);
        binding.recyclerView.setAdapter(mAdapter);

        // Downloading clicked news category data in a background Thread.
        LoaderManager.getInstance(this).initLoader(0, null, this);
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
                // Using previous available result.
                if (news != null && news.size() > 0) {
                    // Use cached result.
                    deliverResult(news);
                } else {
                    // Starts a background thread to download news info.
                    forceLoad();
                }
            }

            @NonNull
            @Override
            public ArrayList<News> loadInBackground() {
                // Stores news items contained in a single page in "The Guardian" api endpoint.
                ArrayList<News> singlePageNews;

                // Stores combined items in multiple pages.
                ArrayList<News> allNews = new ArrayList<>();

                // Downloading news info. page by page from "The Guardian".
                for (int i = FIRST_PAGE; i <= LAST_PAGE; i++) {
                    // Downloads JSON response.
                    String jsonResponse = NetworkUtils.downloadNewsData(
                            NetworkUtils.makeNewsURL(mNewsStringUrl, i));

                    // Parses JSON response to a list of type News.
                    singlePageNews = JsonUtils.parseNewsList(jsonResponse);

                    // Sets "viewType" off all elements in "singlePageNews" ArrayList.
                    setViewType(singlePageNews);

                    // Adding contents of "news" ArrayList to "allNews" ArrayList
                    allNews.addAll(singlePageNews);
                }
                return allNews;
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

                // Lopping through the downloaded news items in order to set view types.
                for (int i = 0; i < viewTypeArray.length; i++) {
                    News item = newsList.get(i);
                    item.setViewType(viewTypeArray[i]);
                }
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
            // Updating the contents of NewsAdapter.
            mAdapter.setEarthquakeData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        // Clearing up the NewsAdapter.
        mAdapter.setEarthquakeData(null);
    }

    /**
     * Shows a {@link Toast} containing custom messages. Method also removes the currently showing
     * Toast if any.
     *
     * @param messageID String resource containing a custom message.
     */
    private void showToast(int messageID) {
        // Cancels the current showing Toast.
        if (mToast != null) {
            mToast.cancel();
        }

        // Sets new message and displays the Toast.
        mToast = Toast.makeText(this, messageID, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void onNewsItemClick(Uri webPage) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, webPage));
        } catch (ActivityNotFoundException e) {
            showToast(R.string.toast_browser_unavailable);
        }
    }
}