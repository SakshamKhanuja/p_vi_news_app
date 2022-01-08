package com.project.news_app.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.R;
import com.project.news_app.activities.CategoryActivity;
import com.project.news_app.adapters.NewsAdapter;
import com.project.news_app.constants.HeadlineFragmentConstants;
import com.project.news_app.constants.NetworkUtilsConstants;
import com.project.news_app.constants.NewsAdapterConstants;
import com.project.news_app.data.News;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Shows a top headlines of US, UK and Australia. Each of these regions are show in separate
 * {@link RecyclerView} that scrolls HORIZONTALLY.
 */
public class HeadlineFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<News>>, NewsAdapter.NewsItemClickListener,
        HeadlineFragmentConstants {

    // Adapter provides News items containing top headlines for US.
    private NewsAdapter mAdapterUS;

    // Adapter provides News item containing top headlines for UK.
    private NewsAdapter mAdapterUK;

    // Adapter provides News item containing top headlines for Australia.
    private NewsAdapter mAdapterAUS;

    // Notifies the unavailability of Browser in user's app.
    private Toast mToast;


    // Required Default Constructor.
    public HeadlineFragment() {
        // Providing a layout to inflate.
        super(R.layout.fragment_headline);
    }

    /**
     * Opens up {@link CategoryActivity} to show the clicked section's news feed.
     */
    private final View.OnClickListener seeMoreClickListener = view -> {
        // Get the ID of the clicked "See More" TextView.
        int clickedID = view.getId();

        // Checks if user clicked "See More" Label TextView of "US News".
        if (clickedID == R.id.see_more_us_news) {
            showMore(NetworkUtilsConstants.PATH_US, TITLE_US);
        }

        // Checks if user clicked "See More" Label TextView of "UK News".
        else if (clickedID == R.id.see_more_uk_news) {
            showMore(NetworkUtilsConstants.PATH_UK, TITLE_UK);
        }

        // Checks if user has clicked "See More" Label TextView of "Australia News".
        else if (clickedID == R.id.see_more_aus_news) {
            showMore(NetworkUtilsConstants.PATH_AUS, TITLE_AUS);
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Initializing TextView that opens up US news feed.
        TextView seeMoreUS = view.findViewById(R.id.see_more_us_news);
        seeMoreUS.setOnClickListener(seeMoreClickListener);

        // Initializing TextView that opens up UK news feed.
        TextView seeMoreUK = view.findViewById(R.id.see_more_uk_news);
        seeMoreUK.setOnClickListener(seeMoreClickListener);

        // Initializing TextView that opens up Australia news feed.
        TextView seeMoreAUS = view.findViewById(R.id.see_more_aus_news);
        seeMoreAUS.setOnClickListener(seeMoreClickListener);

        // Initializing RecyclerView to show US News.
        RecyclerView recyclerUS = view.findViewById(R.id.container_us_news);

        // Initializing RecyclerView to show UK News.
        RecyclerView recyclerUK = view.findViewById(R.id.container_uk_news);

        // Initializing RecyclerView to show Australia News.
        RecyclerView recyclerAUS = view.findViewById(R.id.container_aus_news);

        // Setting up RecyclerView for "US News" and initializing linked adapter.
        mAdapterUS = setupRecyclerView(recyclerUS);

        // Setting up RecyclerView for "UK News" and initializing linked adapter.
        mAdapterUK = setupRecyclerView(recyclerUK);

        // Setting up RecyclerView for "UK News" and initializing linked adapter.
        mAdapterAUS = setupRecyclerView(recyclerAUS);

        // Manages loaders - starting, stopping, restarting and destroying.
        LoaderManager manager = LoaderManager.getInstance(this);

        // Downloads top headlines for US from the "The Guardian" api.
        manager.initLoader(LOADER_US, null, this);

        // Downloads top headlines for UK from the "The Guardian" api.
        manager.initLoader(LOADER_UK, null, this);

        // Downloads top headlines for Australia from the "The Guardian" api.
        manager.initLoader(LOADER_AUS, null, this);
    }

    /**
     * Wires up a {@link RecyclerView} to the {@link NewsAdapter} containing a list of downloaded
     * {@link News} items.
     * <p>
     * It is then managed by a {@link LinearLayoutManager} to lay its items in
     * {@link LinearLayoutManager#HORIZONTAL} orientation.
     *
     * @param recyclerView Inflated RecyclerView.
     * @return {@link NewsAdapter} linked to the RecyclerView.
     */
    private NewsAdapter setupRecyclerView(RecyclerView recyclerView) {
        // Linking LayoutManager to RecyclerView.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        // Optimizes RecyclerView.
        recyclerView.setHasFixedSize(true);

        // Linking Adapter to RecyclerView;
        NewsAdapter adapter = new NewsAdapter(null, this);
        recyclerView.setAdapter(adapter);

        // Converts 8dp to pixels based on screen density.
        int pixel4Dp = convertDpToPixels(8);

        // Adds adequate spacing in item views.
        recyclerView.addItemDecoration(new PaddingItemDecoration(convertDpToPixels(24),
                pixel4Dp, pixel4Dp, convertDpToPixels(16)));
        return adapter;
    }


    /**
     * Converts Density-Independent values to pixel value based on screen density.
     */
    private int convertDpToPixels(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                requireContext().getResources().getDisplayMetrics());
    }


    /**
     * Applies layout margin individually to all items stored in a RecyclerView's adapter based
     * on their position.
     */
    private static class PaddingItemDecoration extends RecyclerView.ItemDecoration {
        // Adds 24dp (in pixels) of margin to LEFT of the item having position 0.
        private final int leftFirst;

        // Adds 8dp (in pixels) of margin to LEFT of all items.
        private final int leftRest;

        // Adds 8dp (in pixels) of margin to RIGHT of all items.
        private final int rightRest;

        // Adds 16dp (in pixels) of margin to RIGHT of the last item.
        private final int rightLast;

        /**
         * Adds margin (left, and right) to all items present in the RecyclerView's adapter.
         *
         * @param leftFirst Margin (in pixels) added at LEFT of the first item.
         * @param leftRest  Margin (in pixels) added to LEFT of item.
         * @param rightRest Margin (in pixels) added to RIGHT of item.
         * @param rightLast Margin (in pixels) added to RIGHT of the last item.
         */
        public PaddingItemDecoration(int leftFirst, int leftRest, int rightRest, int rightLast) {
            this.leftFirst = leftFirst;
            this.leftRest = leftRest;
            this.rightRest = rightRest;
            this.rightLast = rightLast;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            // Stores the position of the passed child view.
            int currentPosition = parent.getChildAdapterPosition(view);

            // Checks if the position belongs to the FIRST child view.
            if (currentPosition == 0) {
                // Adding 24dp (in pixels) of margin to LEFT.
                outRect.left += leftFirst;

                // Adding 8dp (in pixels) of margin to RIGHT.
                outRect.right += rightRest;
            }

            // Checks if the position belongs to the LAST child view.
            else if (currentPosition == (NetworkUtils.SIZE_HEADLINES - 1)) {
                // Adding 8dp (in pixels) of margin to LEFT.
                outRect.left += leftRest;

                // Adding 16dp (in pixels) of margin to RIGHT.
                outRect.right += rightLast;
            }

            // The child view belongs in between the FIRST and LAST.
            else {
                // Adding 8dp (in pixels) of margin to LEFT.
                outRect.left += leftRest;

                // Adding 8dp (in pixels) of margin to RIGHT.
                outRect.right += rightRest;
            }
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<News>>(requireContext()) {

            // Stores news info. of all regions - US, UK, Australia.
            private ArrayList<News> usNews, ukNews, ausNews;

            /**
             * Checks whether previous news data is available or Loader needs to fetch data from
             * "The Guardian" API.
             * @param news Stores the cached info.
             */
            private void check(ArrayList<News> news) {
                // Using previous available result.
                if (news != null && news.size() > 0) {
                    // Use cached result.
                    deliverResult(news);
                } else {
                    // Starts a background thread to download news info.
                    forceLoad();
                }
            }

            @Override
            protected void onStartLoading() {
                // Starts Loader based on ID.
                switch (getId()) {
                    case LOADER_US:
                        check(usNews);
                        break;

                    case LOADER_UK:
                        check(ukNews);
                        break;

                    case LOADER_AUS:
                        check(ausNews);
                        break;
                }
            }

            /**
             * @return The path to download news feed for the running Loader.
             */
            private String getPath() {
                switch (getId()) {
                    case LOADER_UK:
                        return NetworkUtilsConstants.PATH_UK;
                    case LOADER_AUS:
                        return NetworkUtilsConstants.PATH_AUS;
                    case LOADER_US:
                    default:
                        return NetworkUtilsConstants.PATH_US;
                }
            }

            @NonNull
            @Override
            public ArrayList<News> loadInBackground() {
                // Downloads JSON response.
                String jsonResponse = NetworkUtils.downloadNewsData(
                        NetworkUtils.makeNewsUrl(
                                requireContext(),
                                getPath(),
                                NetworkUtilsConstants.QP_VALUE_HEADLINE_FIELDS,
                                NetworkUtilsConstants.SIZE_HEADLINES));

                // Parses JSON response to a list of type News.
                ArrayList<News> news = JsonUtils.parseNewsList(jsonResponse);

                // Checks if news data is available before setting view type.
                if (news.size() > 0) {
                    // Setting background layout for every news item.
                    for (News item : news) {
                        item.setViewType(NewsAdapterConstants.TYPE_SIX);
                    }
                }
                return news;
            }

            @Override
            public void deliverResult(@Nullable ArrayList<News> data) {
                if (data != null) {
                    // Caching downloaded news info. based on region.
                    switch (getId()) {
                        case LOADER_US:
                            usNews = data;
                            break;
                        case LOADER_UK:
                            ukNews = data;
                            break;
                        case LOADER_AUS:
                            ausNews = data;
                            break;
                    }
                }
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {
        // Provides the downloaded news data for every region to their respective Adapters.
        switch (loader.getId()) {
            case LOADER_US:
                setNewsData(mAdapterUS, data);
                break;

            case LOADER_UK:
                setNewsData(mAdapterUK, data);
                break;

            case LOADER_AUS:
                setNewsData(mAdapterAUS, data);
                break;
        }
    }

    /**
     * Provides a list {@link News} to {@link NewsAdapter} containing top headlines.
     */
    private void setNewsData(NewsAdapter adapter, ArrayList<News> data) {
        if (data != null && data.size() > 0) {
            adapter.setNewsData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        // Clearing up the NewsAdapter.
        mAdapterUS.setNewsData(null);
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
        mToast = Toast.makeText(getContext(), messageID, Toast.LENGTH_SHORT);
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

    /**
     * Opens {@link CategoryActivity} to load news feed.
     *
     * @param path  Path of the news feed.
     * @param title Title of the clicked news feed.
     */
    private void showMore(String path, String title) {
        // Starting CategoryActivity to load up news on the clicked news category.
        Intent explicit = new Intent(getContext(), CategoryActivity.class);

        // Passing the path where the clicked category can be accessed in "The Guardian" API.
        explicit.putExtra(CategoryActivity.EXTRA_PATH, path);

        // Passing the clicked news category's title.
        explicit.putExtra(CategoryActivity.EXTRA_TITLE, title);
        startActivity(explicit);
    }
}