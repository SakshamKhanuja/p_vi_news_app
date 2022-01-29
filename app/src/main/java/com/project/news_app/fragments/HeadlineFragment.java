package com.project.news_app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.project.news_app.R;
import com.project.news_app.adapters.NewsFeedAdapter;
import com.project.news_app.constants.HeadlineFragmentConstants;
import com.project.news_app.constants.NetworkUtilsConstants;
import com.project.news_app.constants.NewsAdapterConstants;
import com.project.news_app.data.NewsFeed;
import com.project.news_app.data.News;
import com.project.news_app.utils.CommonUtils;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;
import com.project.news_app.activities.MainActivity;

import java.util.ArrayList;

/**
 * {@link RecyclerView} displays the following sections in order:
 * <ol>
 *     <li>{@link ViewPager2} shows a single {@link News} item from different (Guardian api)
 *     sections in its own {@link Fragment} -> "World", "United States", "United Kingdom",
 *     "Australia" and "The Guardian View - Editorial".</li>
 *
 *     <li>List of {@link News} from "World" (Guardian api) section.</li>
 *
 *     <li>List of {@link News} from "United States" (Guardian api) section.</li>
 *
 *     <li>Section advertises the
 *     <a href="https://play.google.com/store/apps/details?id=uk.co.guardian.puzzles">Guardian
 *     Puzzles & Crosswords</a> app.</li>
 *
 *     <li>Section points the <a href="https://www.theguardian.com/global/ng-interactive/2018/oct/12/guardian-weekly-community-map">
 *     Guardian Weekly Global Community</a> (section) in "The Guardian" website.</li>
 *
 *     <li>List of {@link News} from "United Kingdom" (Guardian api) section.</li>
 *
 *     <li>List of {@link News} from "Australia" (Guardian api) section.</li>
 *
 *     <li>List of {@link News} from "Editorial" (Guardian api) section.</li>
 *
 *     <li>Section shows all social platforms where users can access "The Guardian" in ->
 *     "Facebook", "Instagram", "LinkedIn", "Twitter" and "YouTube".</li>
 * </ol>
 */
public class HeadlineFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<NewsFeed>>, HeadlineFragmentConstants {
    /**
     * Splits and provides the downloaded news info. to RecyclerView.
     */
    private NewsFeedAdapter adapter;

    /**
     * Sets context when this fragment is attached to {@link MainActivity}.
     */
    private Context context;

    // Required Default Constructor.
    public HeadlineFragment() {
        // Providing a layout to inflate.
        super(R.layout.basic_recycler_view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Setting context.
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Setting title.
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bottom_headlines);

        // Initializing Parent RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_dark);

        // Hiding the scroll bar.
        recyclerView.setVerticalScrollBarEnabled(false);

        // Initializing Adapter.
        adapter = new NewsFeedAdapter(context, null, getChildFragmentManager(),
                getLifecycle());

        // Setting up the Parent RecyclerView.
        CommonUtils.setupRecyclerView(context, recyclerView, adapter,
                LinearLayoutManager.VERTICAL);

        // Downloads top headlines from "World", "US", "UK", "Australia" and "Editorial".
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<NewsFeed>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<NewsFeed>>(context) {
            /**
             * Stores news feeds for different region/section.
             */
            private ArrayList<NewsFeed> newsFeeds;

            @Override
            protected void onStartLoading() {
                // Using previous available result.
                if (newsFeeds != null && newsFeeds.size() > 0) {
                    // Use cached result.
                    deliverResult(newsFeeds);
                } else {
                    // Starts a background thread to download news feed.
                    forceLoad();
                }
            }

            /**
             * Sets {@link NewsFeed} title and label based on path.
             *
             * @param feed Downloaded news feed.
             * @param path Points to the section in "The Guardian" api.
             */
            private void setNewsFeedTitleAndLabel(NewsFeed feed, String path) {
                switch (path) {
                    case NetworkUtilsConstants.PATH_WORLD:
                        feed.setTitle(context.getString(R.string.title_world_news));
                        feed.setLabel(context.getString(R.string.top_story_world));
                        break;

                    case NetworkUtilsConstants.PATH_US:
                        feed.setTitle(context.getString(R.string.title_us_news));
                        feed.setLabel(context.getString(R.string.top_story_us));
                        break;

                    case NetworkUtilsConstants.PATH_UK:
                        feed.setTitle(context.getString(R.string.title_uk_news));
                        feed.setLabel(context.getString(R.string.top_story_uk));
                        break;

                    case NetworkUtilsConstants.PATH_AUS:
                        feed.setTitle(context.getString(R.string.title_aus_news));
                        feed.setLabel(context.getString(R.string.top_story_aus));
                        break;

                    case NetworkUtilsConstants.PATH_GUARDIAN:
                        feed.setTitle(context.getString(R.string.title_editorial_news));
                        feed.setLabel(context.getString(R.string.title_editorial_news));
                        break;
                }
            }

            /**
             * Sets view type to each {@link News} item in the feed based on {@link NewsFeed} type.
             */
            private void setNewsViewType(ArrayList<News> news, int newsFeedType) {
                // Checks if news data is available before setting view type.
                if (news.size() > 0) {

                    // View type for the first news item in feed.
                    int firstItemViewType;

                    // View type for the last news item in feed.
                    int lastItemViewType;

                    // View type for news items in between the first and last.
                    int betweenItemViewType;

                    // Setting view type based on "newsFeedType".
                    if (newsFeedType == FEED_TYPE_BLACK) {
                        // View type contains 24dp padding (START) and 8dp padding (END).
                        firstItemViewType = NewsAdapterConstants.TYPE_SEVEN;

                        // View type contains 16dp padding (HORIZONTAL).
                        betweenItemViewType = NewsAdapterConstants.TYPE_SIX;

                        // View type contains 8dp padding (START) and 16dp padding (END).
                        lastItemViewType = NewsAdapterConstants.TYPE_EIGHT;
                    } else {
                        // View type contains 24dp padding (START) and 8dp padding (END).
                        firstItemViewType = NewsAdapterConstants.TYPE_TEN;

                        // View type contains 16dp padding (HORIZONTAL).
                        betweenItemViewType = NewsAdapterConstants.TYPE_NINE;

                        // View type contains 8dp padding (START) and 16dp padding (END).
                        lastItemViewType = NewsAdapterConstants.TYPE_ELEVEN;
                    }

                    // Setting view type for news item in the first position.
                    News firstItem = news.get(0);
                    firstItem.setViewType(firstItemViewType);

                    // Setting view type for news item in the last position.
                    int lastItemPos = news.size() - 1;
                    News lastItem = news.get(lastItemPos);
                    lastItem.setViewType(lastItemViewType);

                    // Setting view type for news item in between.
                    for (int i = 1; i < lastItemPos; i++) {
                        News betweenItem = news.get(i);
                        betweenItem.setViewType(betweenItemViewType);
                    }
                }
            }

            @NonNull
            @Override
            public ArrayList<NewsFeed> loadInBackground() {
                // Stores all section info. that is shown by the RecyclerView.
                ArrayList<NewsFeed> sectionFeeds = new ArrayList<>();

                /*
                 * Stores news item that are shown in their own Fragments by ViewPager2. List
                 * contains the first item (News) from every "section" feed - World, US, UK,
                 * Australia and Editorial.
                 */
                ArrayList<News> sectionOneNews = new ArrayList<>();

                /*
                 * Array stores downloaded news info. from "sections" - World, US, UK, Australia
                 * and Editorial.
                 */
                String[] jsonResponseArray = new String[pathArray.length];

                // Downloads json responses per section path (one-by-one).
                for (int i = 0; i < jsonResponseArray.length; i++) {
                    jsonResponseArray[i] =
                            NetworkUtils.downloadNewsData(
                                    NetworkUtils.makeNewsUrl(
                                            context,
                                            pathArray[i],
                                            NetworkUtilsConstants.QP_VALUE_HEADLINE_FIELDS,
                                            NetworkUtilsConstants.SIZE_HEADLINES));
                }

                /*
                 * Setting up Sections showing:
                 *
                 * 1. List showing the top news all around the globe.
                 * 2. List showing the top news from "United States".
                 * 3. List showing the top news from "United Kingdom".
                 * 4. List showing the top news from "Australia".
                 * 5. List showing the top news from "Editorial".
                 */
                for (int i = 0; i < jsonResponseArray.length; i++) {
                    // Initializing a feed.
                    NewsFeed feed = new NewsFeed();

                    // Get current path.
                    String path = pathArray[i];

                    // Parses JSON response to a list of type News.
                    ArrayList<News> news = JsonUtils.parseNewsList(jsonResponseArray[i]);

                    // Storing the first news to "sectionOneNews".
                    if (news.size() > 0) {
                        sectionOneNews.add(news.get(0));
                    }

                    /*
                     * Sets NewsFeed type for each downloaded feed.
                     *
                     * The last feed from in "pathArray" i.e. "Editorial" is set to have a dark
                     * background. The rest of the news feed follow the same type.
                     */
                    if (i == pathArray.length - 1) {
                        feed.setType(FEED_TYPE_DARK);
                    } else {
                        feed.setType(FEED_TYPE_BLACK);
                    }

                    // Sets view type of news items in "news" based on NewsFeed view type.
                    setNewsViewType(news, feed.getType());

                    // Sets path of NewsFeed.
                    feed.setPath(path);

                    /*
                     * Sets title and label of NewsFeed. Title is passed as CategoryActivity's
                     * title.
                     */
                    setNewsFeedTitleAndLabel(feed, path);

                    // Sets downloaded news items to NewsFeed.
                    feed.setNews(news);

                    // Adding "feed" to "sectionFeeds".
                    sectionFeeds.add(feed);
                }

                // Setting up Section 1 -> ViewPager2 showing top news in Fragments.
                NewsFeed feedOne = new NewsFeed();
                feedOne.setType(FEED_TYPE_TOP);
                feedOne.setNews(sectionOneNews);
                sectionFeeds.add(0, feedOne);

                /*
                 * Setting up Section 4 -> Points to "Guardian Puzzles & Crosswords" app in Play
                 * Store.
                 */
                NewsFeed feedThree = new NewsFeed();
                feedThree.setType(FEED_TYPE_DISCOVER);
                sectionFeeds.add(3, feedThree);

                /*
                 * Setting up Section 5 -> Points to "Guardian Weekly's Global Community" section
                 * in "The Guardian" website.
                 */
                NewsFeed feedFour = new NewsFeed();
                feedFour.setType(FEED_TYPE_READERS);
                sectionFeeds.add(4, feedFour);

                /*
                 * Setting up Section 9 (Last Section) -> Shows all social platforms where users
                 * can access "The Guardian".
                 */
                NewsFeed feedNine = new NewsFeed();
                feedNine.setType(FEED_TYPE_SOCIAL);
                sectionFeeds.add(feedNine);

                return sectionFeeds;
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
            adapter.setNewsFeeds(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<NewsFeed>> loader) {
        // Clearing up the NewsFeedAdapter
        adapter.setNewsFeeds(null);
    }
}