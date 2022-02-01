package com.project.news_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.project.news_app.R;
import com.project.news_app.activities.CategoryActivity;
import com.project.news_app.constants.HeadlineFragmentConstants;
import com.project.news_app.data.News;
import com.project.news_app.data.NewsFeed;
import com.project.news_app.fragments.TopNewsFragment;
import com.project.news_app.fragments.HomeFragment;
import com.project.news_app.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Adapter provides {@link NewsFeed} to RecyclerView in {@link HomeFragment}.
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        HeadlineFragmentConstants {
    /**
     * Stores news feed from different sections. Check documentation of {@link HomeFragment} to
     * know more about it.
     */
    private ArrayList<NewsFeed> newsFeeds;

    /**
     * Multiple use case.
     */
    private final Context context;

    /**
     * Used to initialize {@link NewsFeedHolderTop.TopNewsAdapter}.
     */
    private final Lifecycle lifecycle;

    // Adds, Replaces, Removes fragments.
    private final FragmentManager fragmentManager;

    public NewsFeedAdapter(Context context, ArrayList<NewsFeed> newsFeeds,
                           FragmentManager fragmentManager, Lifecycle lifecycle) {
        this.context = context;
        this.newsFeeds = newsFeeds;
        this.fragmentManager = fragmentManager;
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        // Inflates views from layout.
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (viewType) {
            case FEED_TYPE_TOP:
                /*
                 * Shows each news item in its own Fragment managed by ViewPager2 and
                 * FragmentStateAdapter.
                 */
                return new NewsFeedHolderTop(layoutInflater.inflate(R.layout.layout_top_feed,
                        parent, false));

            case FEED_TYPE_BLACK:
                // Shows news items from "World", "US", "UK" and "Australia" section.
                return new NewsFeedHolderBlack(LayoutInflater.from(context).inflate(
                        R.layout.layout_nested_recycler_item_black, parent, false));

            case FEED_TYPE_DISCOVER:
                // Section advertises the "Guardian Puzzles & Crosswords" app.
                return new NewsFeedHolderDiscover(layoutInflater.inflate(R.layout.layout_discover,
                        parent, false));

            case FEED_TYPE_READERS:
                // Section points the "Guardian Weekly Global Community" in "The Guardian" website.
                return new NewsFeedHolderReader(layoutInflater.inflate(R.layout.layout_readers,
                        parent, false));

            case FEED_TYPE_DARK:
                // Shows news items from "Editorial" section.
                return new NewsFeedHolderDark(layoutInflater.inflate(
                        R.layout.layout_nested_recycler_item_dark, parent, false));

            case FEED_TYPE_SOCIAL:
            default:
                // Section shows all social platforms where users can access "The Guardian".
                return new NewsFeedHolderSocial(layoutInflater.inflate(R.layout.layout_social,
                        parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Get NewsFeed at specified position.
        NewsFeed feed = newsFeeds.get(position);

        // Binding current NewsFeed data based on view type.
        switch (holder.getItemViewType()) {
            case FEED_TYPE_TOP:
                ((NewsFeedHolderTop) holder).setTopNewsData(feed);
                break;

            case FEED_TYPE_BLACK:
                ((NewsFeedHolderBlack) holder).setupNewsFeed(feed);
                break;

            case FEED_TYPE_DARK:
                ((NewsFeedHolderDark) holder).setupNewsFeed(feed);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return newsFeeds.get(position).getType();
    }

    /**
     * Sets new news feed data to Adapter.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setNewsFeeds(ArrayList<NewsFeed> newsFeeds) {
        this.newsFeeds = newsFeeds;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // Returns 0 if list is empty.
        if (newsFeeds == null) {
            return 0;
        }

        // Returns total number of available news feed.
        return newsFeeds.size();
    }

    /**
     * Sets contents of {@link NewsFeed}.
     *
     * @param newsFeed     News feed.
     * @param labelRegion  Shows the label of {@link NewsFeed}.
     * @param recyclerView Shows the contents of {@link NewsFeed} in a {@link RecyclerView}.
     *                     <br/>
     *                     Orientation of this RecyclerView is set to
     *                     {@link LinearLayoutManager#HORIZONTAL}.
     * @param labelSeeMore Opens up the {@link NewsFeed} in {@link CategoryActivity}.
     */
    private void showRegionalNews(NewsFeed newsFeed, TextView labelRegion,
                                  RecyclerView recyclerView, TextView labelSeeMore) {
        // Sets news feed label.
        labelRegion.setText(newsFeed.getLabel());

        // Setting up RecyclerView.
        CommonUtils.setupRecyclerView(context, recyclerView, new NewsAdapter(context,
                newsFeed.getNews()), LinearLayoutManager.HORIZONTAL);

        // Opens up CategoryActivity.
        labelSeeMore.setOnClickListener(view -> CommonUtils.openCategoryActivity(context,
                newsFeed.getPath(), newsFeed.getTitle()));
    }

    /**
     * Shows the news feed having a black background.
     * <br/>
     * Layout resource - {@link R.layout#layout_nested_recycler_item_black}
     */
    protected class NewsFeedHolderBlack extends RecyclerView.ViewHolder {
        /**
         * Shows the {@link NewsFeed} in a HORIZONTAL orientation.
         */
        private final RecyclerView recyclerView;

        /**
         * Shows the {@link NewsFeed} label.
         */
        private final TextView labelRegion;

        /**
         * Opens up the {@link NewsFeed} in {@link CategoryActivity}.
         */
        private final TextView labelSeeMore;

        public NewsFeedHolderBlack(View itemView) {
            super(itemView);

            // Initialize Views.
            recyclerView = itemView.findViewById(R.id.item_recycler_view_one);
            labelRegion = itemView.findViewById(R.id.item_label_one);
            labelSeeMore = itemView.findViewById(R.id.item_see_more_one);
        }

        /**
         * Setups the {@link NewsFeed}.
         */
        public void setupNewsFeed(NewsFeed newsFeed) {
            showRegionalNews(newsFeed, labelRegion, recyclerView, labelSeeMore);
        }
    }

    /**
     * Shows the news feed having a dark background.
     * <br/>
     * Layout resource - {@link R.layout#basic_recycler_view}
     */
    protected class NewsFeedHolderDark extends RecyclerView.ViewHolder {
        /**
         * Shows the {@link NewsFeed} in a HORIZONTAL orientation.
         */
        private final RecyclerView recyclerView;

        /**
         * Shows the {@link NewsFeed} label.
         */
        private final TextView labelRegion;

        /**
         * Opens up the news feed in {@link CategoryActivity}.
         */
        private final TextView labelSeeMore;

        public NewsFeedHolderDark(View itemView) {
            super(itemView);

            // Initialize Views.
            recyclerView = itemView.findViewById(R.id.item_recycler_view_two);
            labelRegion = itemView.findViewById(R.id.item_label_two);
            labelSeeMore = itemView.findViewById(R.id.item_see_more_two);
        }

        /**
         * Setups the {@link NewsFeed}.
         */
        public void setupNewsFeed(NewsFeed newsFeed) {
            showRegionalNews(newsFeed, labelRegion, recyclerView, labelSeeMore);
        }
    }

    /**
     * Shows each {@link News} item in its own {@link Fragment} managed by {@link ViewPager2} and
     * {@link FragmentStateAdapter}.
     * <br/>
     * Layout resource - {@link R.layout#layout_top_feed}
     */
    protected class NewsFeedHolderTop extends RecyclerView.ViewHolder {
        /**
         * Stores {@link News} items from multiple sections from "The Guardian" -> "World", "US",
         * "UK", "Australia" and "Editorial".
         */
        private ArrayList<News> topNewsList;

        public NewsFeedHolderTop(View itemView) {
            super(itemView);

            // Provides swipe-able screens.
            ViewPager2 viewPager = itemView.findViewById(R.id.top_viewpager);

            // Linking adapter.
            viewPager.setAdapter(new TopNewsAdapter(fragmentManager, lifecycle));
        }

        /**
         * Get {@link News} list from the feed.
         *
         * @param feed Must contain a list of {@link News} from a valid section endpoint from
         *             "The Guardian" api.
         */
        public void setTopNewsData(NewsFeed feed) {
            topNewsList = feed.getNews();
        }

        /**
         * Provides {@link TopNewsFragment} containing {@link News} headline, section name and
         * thumbnail to {@link ViewPager2}.
         */
        private class TopNewsAdapter extends FragmentStateAdapter {

            public TopNewsAdapter(@NonNull FragmentManager fragmentManager,
                                  @NonNull Lifecycle lifecycle) {
                super(fragmentManager, lifecycle);
            }

            /**
             * Creates a {@link TopNewsFragment} containing {@link News} info.
             *
             * @param news   Must contain headline, section name and thumbnail.
             * @param number Index of passed news from 1 to 5.
             */
            private TopNewsFragment createTopNewsFragment(News news, int number) {
                TopNewsFragment topNewsFragment = new TopNewsFragment();

                // Bundle is passed to "topNewsFragment" at creation.
                Bundle bundle = new Bundle();

                // Attaching "news" to "bundle".
                bundle.putParcelable(TopNewsFragment.KEY_NEWS, news);

                // Attaching "number" to "bundle".
                bundle.putInt(TopNewsFragment.KEY_NEWS_NUMBER, number);

                // Passing "bundle".
                topNewsFragment.setArguments(bundle);
                return topNewsFragment;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (topNewsList != null && topNewsList.size() == 5) {
                    switch (position) {
                        case 0:
                            // Shows World news.
                            return createTopNewsFragment(topNewsList.get(0), 1);

                        case 1:
                            // Shows US news.
                            return createTopNewsFragment(topNewsList.get(1), 2);

                        case 2:
                            // Shows UK news.
                            return createTopNewsFragment(topNewsList.get(2), 3);

                        case 3:
                            // Shows Australia news.
                            return createTopNewsFragment(topNewsList.get(3), 4);

                        case 4:
                            // Shows Opinion.
                            return createTopNewsFragment(topNewsList.get(4), 5);
                    }
                }
                // Returns empty fragment.
                return new Fragment(R.layout.layout_top_news_fragment);
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        }
    }

    /**
     * Shows all social platforms where users can access "The Guardian" -> "Facebook", "Instagram",
     * "LinkedIn", "Twitter" and "YouTube".
     * <br/>
     * Layout resource - {@link R.layout#layout_social}
     */
    protected class NewsFeedHolderSocial extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public NewsFeedHolderSocial(View itemView) {
            super(itemView);

            // Initialize Views.
            ImageView socialFacebook = itemView.findViewById(R.id.social_facebook);
            ImageView socialInstagram = itemView.findViewById(R.id.social_instagram);
            ImageView socialLinkedIn = itemView.findViewById(R.id.social_linked_in);
            ImageView socialTwitter = itemView.findViewById(R.id.social_twitter);
            ImageView socialYouTube = itemView.findViewById(R.id.social_you_tube);

            // Attaching OnClickListener to show "The Guardian" on Facebook in (app/webpage).
            socialFacebook.setOnClickListener(this);

            // Attaching OnClickListener to show "The Guardian" on Instagram in (app/webpage).
            socialInstagram.setOnClickListener(this);

            // Attaching OnClickListener to show "The Guardian" on LinkedIn in (app/webpage).
            socialLinkedIn.setOnClickListener(this);

            // Attaching OnClickListener to show "The Guardian" on Twitter in (app/webpage).
            socialTwitter.setOnClickListener(this);

            // Attaching OnClickListener to show "The Guardian" on YouTube in (app/webpage).
            socialYouTube.setOnClickListener(this);
        }

        @Override
        public void onClick(View platform) {
            // Get the clicked platform (view) ID.
            int platformID = platform.getId();

            // Stores the url of the clicked platform.
            String url = "";

            // Checks if user clicked "Facebook".
            if (platformID == R.id.social_facebook) {
                url = FACEBOOK_URL;
            }

            // Checks if user clicked "Instagram".
            else if (platformID == R.id.social_instagram) {
                url = INSTAGRAM_URL;
            }

            // Checks if user clicked "LinkedIn".
            else if (platformID == R.id.social_linked_in) {
                url = LINKEDIN_URL;
            }

            // Checks if user clicked "Twitter".
            else if (platformID == R.id.social_twitter) {
                url = TWITTER_URL;
            }

            // Checks if user clicked "YouTube".
            else if (platformID == R.id.social_you_tube) {
                url = YOUTUBE_URL;
            }

            /*
             * Opens the clicked platform in its individual app or load its page in device's
             * browser.
             */
            CommonUtils.openBrowserOrApp(context, url, R.string.toast_browser_unavailable);
        }
    }

    /**
     * Shows a section that advertises the "Guardian Puzzles & Crosswords" app.
     * <br/>
     * Layout resource - {@link R.layout#layout_discover}
     */
    protected class NewsFeedHolderDiscover extends RecyclerView.ViewHolder {

        public NewsFeedHolderDiscover(View itemView) {
            super(itemView);

            // Initialize View.
            TextView textGetTheApp = itemView.findViewById(R.id.text_go_to_app);

            /*
             * OnClickListener opens the "Play Store" that shows the "Guardian Puzzles &
             * Crosswords".
             */
            textGetTheApp.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context,
                    DISCOVER_URL, R.string.toast_browser_unavailable));
        }
    }

    /**
     * Shows a section that points to "Guardian Weekly Global Community" on "The Guardian".
     * <br/>
     * Layout resource - {@link R.layout#layout_readers}
     */
    protected class NewsFeedHolderReader extends RecyclerView.ViewHolder {

        public NewsFeedHolderReader(View itemView) {
            super(itemView);

            // Initialize View.
            TextView textGetInvolved = itemView.findViewById(R.id.text_get_involved);

            /*
             * OnClickListener opens "Guardian Weekly Global Community" webpage in "The Guardian"
             * website on the device's browser.
             */
            textGetInvolved.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context,
                    READERS_URL, R.string.toast_browser_unavailable));
        }
    }
}