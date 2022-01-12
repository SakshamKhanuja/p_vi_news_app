package com.project.news_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.R;
import com.project.news_app.activities.CategoryActivity;
import com.project.news_app.constants.HeadlineFragmentConstants;
import com.project.news_app.data.NewsFeed;

import java.util.ArrayList;

public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        HeadlineFragmentConstants {
    /*
     * Stores top headlines of "World", "US", "UK", "Australia" and "Editorial" sections from
     * "The Guardian".
     */
    private ArrayList<NewsFeed> newsFeeds;

    // Used to setup nested RecyclerView's.
    private Context context;

    public NewsFeedAdapter(ArrayList<NewsFeed> newsFeeds) {
        this.newsFeeds = newsFeeds;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        // Setting context.
        context = parent.getContext();

        // Inflates views from layout.
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (viewType) {
            case FEED_TYPE_DARK:
                // Shows news feed in a dark background.
                return new NewsFeedHolderDark(layoutInflater.inflate(
                        R.layout.layout_nested_recycler_item_dark, parent, false));

            case FEED_TYPE_LIGHT:
            default:
                // Shows news feed in a light background.
                return new NewsFeedHolderLight(LayoutInflater.from(context).inflate(
                        R.layout.layout_nested_recycler_item_light, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Get the news feed at specified position.
        NewsFeed feed = newsFeeds.get(position);

        // Binding current news feed data based on view type.
        switch (holder.getItemViewType()) {
            case FEED_TYPE_LIGHT:
                ((NewsFeedHolderLight) holder).setupNewsFeed(feed);
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
     * @param labelRegion  Shows the label of the news feed.
     * @param recyclerView Shows the contents of the news feed in a {@link RecyclerView}.
     *                     <br/>
     *                     Orientation of this RecyclerView is set to
     *                     {@link LinearLayoutManager#HORIZONTAL}.
     * @param labelSeeMore Opens up the news feed in {@link CategoryActivity}.
     */
    private void showRegionalNews(NewsFeed newsFeed, TextView labelRegion, RecyclerView recyclerView,
                                  TextView labelSeeMore) {
        // Sets news feed label.
        labelRegion.setText(newsFeed.getLabel());

        // Links LayoutManager to RecyclerView.
        recyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));

        // Links recyclerView to a NewsAdapter.
        recyclerView.setAdapter(new NewsAdapter(newsFeed.getNews()));

        // Optimizes RecyclerView.
        recyclerView.setHasFixedSize(true);

        // Opens up CategoryActivity.
        labelSeeMore.setOnClickListener(view -> {
            /*
             * Starting CategoryActivity to load up news on the clicked news feed's "See More"
             * TextView.
             */
            Intent explicit = new Intent(context, CategoryActivity.class);

            // Passing the path where the clicked category can be accessed in "The Guardian" API.
            explicit.putExtra(CategoryActivity.EXTRA_PATH, newsFeed.getPath());

            // Passing the clicked news category's title.
            explicit.putExtra(CategoryActivity.EXTRA_TITLE, newsFeed.getTitle());
            context.startActivity(explicit);
        });
    }

    /**
     * Shows the news feed having a light background.
     */
    protected class NewsFeedHolderLight extends RecyclerView.ViewHolder {
        // Shows the news feed in a HORIZONTAL orientation.
        private final RecyclerView recyclerView;

        // Shows the label of the news feed.
        private final TextView labelRegion;

        /**
         * Opens up the news feed in {@link CategoryActivity}.
         */
        private final TextView labelSeeMore;

        public NewsFeedHolderLight(View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.item_recycler_view_one);
            labelRegion = itemView.findViewById(R.id.item_label_one);
            labelSeeMore = itemView.findViewById(R.id.item_see_more_one);
        }

        /**
         * Setups news feed.
         */
        public void setupNewsFeed(NewsFeed newsFeed) {
            showRegionalNews(newsFeed, labelRegion, recyclerView, labelSeeMore);
        }
    }

    /**
     * Shows the news feed having a dark background.
     */
    protected class NewsFeedHolderDark extends RecyclerView.ViewHolder {
        // Shows the news feed in a HORIZONTAL orientation.
        private final RecyclerView recyclerView;

        // Shows the label of the news feed.
        private final TextView labelRegion;

        /**
         * Opens up the news feed in {@link CategoryActivity}.
         */
        private final TextView labelSeeMore;

        public NewsFeedHolderDark(View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.item_recycler_view_two);
            labelRegion = itemView.findViewById(R.id.item_label_two);
            labelSeeMore = itemView.findViewById(R.id.item_see_more_two);
        }

        /**
         * Setups news feed.
         */
        public void setupNewsFeed(NewsFeed newsFeed) {
            showRegionalNews(newsFeed, labelRegion, recyclerView, labelSeeMore);
        }
    }
}