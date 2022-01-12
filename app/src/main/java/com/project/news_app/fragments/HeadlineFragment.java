package com.project.news_app.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.R;
import com.project.news_app.adapters.NewsFeedAdapter;
import com.project.news_app.constants.HeadlineFragmentConstants;
import com.project.news_app.constants.NetworkUtilsConstants;
import com.project.news_app.constants.NewsAdapterConstants;
import com.project.news_app.data.NewsFeed;
import com.project.news_app.data.News;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Shows top headlines from different sections of "The Guardian" API - "World", "US", "UK",
 * "Australia" and "Editorial".
 */
public class HeadlineFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<NewsFeed>>, HeadlineFragmentConstants {

    // Stores top headlines of "World", "US", "UK", "Australia" and "Editorial".
    private NewsFeedAdapter mAdapter;

    // Required Default Constructor.
    public HeadlineFragment() {
        // Providing a layout to inflate.
        super(R.layout.basic_recycler_view_light);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Initializing Parent RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_light);

        // Linking LayoutManager to RecyclerView.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        // Optimizes RecyclerView.
        recyclerView.setHasFixedSize(true);

        // Linking Adapter to RecyclerView.
        mAdapter = new NewsFeedAdapter(null);
        recyclerView.setAdapter(mAdapter);

        // Downloads top headlines from "World", "US", "UK", "Australia" and "Editorial".
        LoaderManager.getInstance(this).initLoader(12, null, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<NewsFeed>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<NewsFeed>>(requireContext()) {

            // Stores news feed for - "US", "UK", "Australia", "World" (Rest) and "Editorial".
            private ArrayList<NewsFeed> newsFeeds;

            @Override
            protected void onStartLoading() {
                // Using previous available result.
                if (newsFeeds != null && newsFeeds.size() > 0) {
                    // Use cached result.
                    deliverResult(newsFeeds);
                } else {
                    // Starts a background thread to download top regional headlines.
                    forceLoad();
                }
            }

            /**
             * @return News title.
             */
            private String getTitle(String path) {
                switch (path) {
                    case NetworkUtilsConstants.PATH_WORLD:
                        return getString(R.string.title_world_news);
                    case NetworkUtilsConstants.PATH_US:
                        return getString(R.string.title_us_news);
                    case NetworkUtilsConstants.PATH_UK:
                        return getString(R.string.title_uk_news);
                    case NetworkUtilsConstants.PATH_AUS:
                        return getString(R.string.title_aus_news);
                    case NetworkUtilsConstants.PATH_GUARDIAN:
                        return getString(R.string.title_editorial_news);
                }
                return null;
            }

            /**
             * @return News feed label.
             */
            private String getLabel(String path) {
                switch (path) {
                    case NetworkUtilsConstants.PATH_WORLD:
                        return getString(R.string.top_story_world);
                    case NetworkUtilsConstants.PATH_US:
                        return getString(R.string.top_story_us);
                    case NetworkUtilsConstants.PATH_UK:
                        return getString(R.string.top_story_uk);
                    case NetworkUtilsConstants.PATH_AUS:
                        return getString(R.string.top_story_aus);
                    case NetworkUtilsConstants.PATH_GUARDIAN:
                        return getString(R.string.title_editorial_news);
                }
                return null;
            }

            /**
             * Sets view type to each {@link News} item in the feed based on {@link NewsFeed} type.
             */
            private void setViewType(ArrayList<News> news, int newsFeedType) {
                // Checks if news data is available before setting view type.
                if (news.size() > 0) {

                    // Stores view type of single News item.
                    int viewType;

                    // Setting background layout for every news item.
                    for (int i = 0; i < news.size(); i++) {
                        News item = news.get(i);

                        // View type for the first news item in feed.
                        if (i == 0) {
                            if (newsFeedType == FEED_TYPE_LIGHT) {
                                // View type contains 24dp padding (START) and 8dp padding (END).
                                viewType = NewsAdapterConstants.TYPE_SEVEN;
                            } else {
                                // View type contains 24dp padding (START) and 8dp padding (END).
                                viewType = NewsAdapterConstants.TYPE_TEN;
                            }
                        }

                        // View type for the last news item in feed.
                        else if (i == news.size() - 1) {
                            if (newsFeedType == FEED_TYPE_LIGHT) {
                                // View type contains 8dp padding (START) and 16dp padding (END).
                                viewType = NewsAdapterConstants.TYPE_EIGHT;
                            } else {
                                // View type contains 8dp padding (START) and 16dp padding (END).
                                viewType = NewsAdapterConstants.TYPE_ELEVEN;
                            }
                        }

                        // View type for news items in between the first and last.
                        else {
                            if (newsFeedType == FEED_TYPE_LIGHT) {
                                // View type contains 16dp padding (HORIZONTAL).
                                viewType = NewsAdapterConstants.TYPE_SIX;
                            } else {
                                // View type contains 16dp padding (HORIZONTAL).
                                viewType = NewsAdapterConstants.TYPE_NINE;
                            }
                        }
                        item.setViewType(viewType);
                    }
                }
            }

            @NonNull
            @Override
            public ArrayList<NewsFeed> loadInBackground() {
                // Stores news feed of different sections from "The Guardian" API.
                ArrayList<NewsFeed> feeds = new ArrayList<>();

                // Array stores all json responses based on section from "The Guardian" API.
                String[] jsonResponseArray = new String[pathArray.length];

                // Downloads json responses per section path (one-by-one).
                for (int i = 0; i < jsonResponseArray.length; i++) {
                    jsonResponseArray[i] =
                            NetworkUtils.downloadNewsData(
                                    NetworkUtils.makeNewsUrl(
                                            requireContext(),
                                            pathArray[i],
                                            NetworkUtilsConstants.QP_VALUE_HEADLINE_FIELDS,
                                            NetworkUtilsConstants.SIZE_HEADLINES));
                }

                // Setting up all news feeds.
                for (int i = 0; i < jsonResponseArray.length; i++) {
                    // Initializing a feed.
                    NewsFeed newsFeed = new NewsFeed();

                    // Get current path.
                    String path = pathArray[i];

                    // Parses JSON response to a list of type News.
                    ArrayList<News> news = JsonUtils.parseNewsList(jsonResponseArray[i]);

                    /*
                     * Sets NewsFeed type for each downloaded feed.
                     *
                     * The last feed from in "pathArray" i.e. "Editorial" is set to have a dark
                     * background. The rest of the news feed follow the same type.
                     */
                    if (i == pathArray.length - 1) {
                        newsFeed.setType(FEED_TYPE_DARK);
                    } else {
                        newsFeed.setType(FEED_TYPE_LIGHT);
                    }

                    // Sets view type for each news item based on NewsFeed type.
                    setViewType(news, newsFeed.getType());

                    // Sets path for this feed.
                    newsFeed.setPath(path);

                    // Sets title for this feed.
                    newsFeed.setTitle(getTitle(path));

                    // Sets downloaded news items to NewsFeed.
                    newsFeed.setNews(news);

                    // Sets label of this news feed.
                    newsFeed.setLabel(getLabel(path));

                    // Adding feed to list.
                    feeds.add(newsFeed);
                }
                return feeds;
            }

            @Override
            public void deliverResult(@Nullable ArrayList<NewsFeed> data) {
                // Caches News Feed for all regions.
                if (data != null) {
                    newsFeeds = data;
                }
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<NewsFeed>> loader,
                               ArrayList<NewsFeed> data) {
        if (data != null && data.size() > 0) {
            // Updating the contents of NewsFeedAdapter.
            mAdapter.setNewsFeeds(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<NewsFeed>> loader) {
        // Clearing up the NewsFeedAdapter
        mAdapter.setNewsFeeds(null);
    }
}