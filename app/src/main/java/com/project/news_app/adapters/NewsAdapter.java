package com.project.news_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.project.news_app.R;
import com.project.news_app.data.News;
import com.project.news_app.activities.CategoryActivity;

import java.util.ArrayList;

/**
 * Provides {@link NewsViewHolder} ViewHolder inflated from {@link R.layout#news_item_main}
 * and {@link R.layout#news_item} item view layout to {@link R.id#recycler_view} RecyclerView.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    /**
     * Provides click functionality to news items in {@link CategoryActivity}.
     */
    private final NewsItemClickListener newsItemClickListener;

    // Stores news item under clicked news category.
    private ArrayList<News> newsItems;

    // Used to load images via Glide library.
    private Context context;

    /**
     * View type inflated from {@link R.layout#news_item_main} layout.
     */
    private static final int TYPE_MAIN = 1;

    public NewsAdapter(ArrayList<News> newsItems, NewsItemClickListener newsItemClickListener) {
        this.newsItems = newsItems;
        this.newsItemClickListener = newsItemClickListener;
    }

    // Provides click functionality to News items.
    public interface NewsItemClickListener {
        /**
         * Opens up the clicked news article on the user's browser.
         *
         * @param webPage Uri points to the clicked news article of "The Guardian".
         */
        void onNewsItemClick(Uri webPage);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sets context.
        context = parent.getContext();

        // Represents the news item in the RecyclerView.
        View newsItem;

        // Inflates item views based on the adapter position.
        if (viewType == 1) {
            // Inflates the news item from "news_item_top_three" layout.
            newsItem = LayoutInflater.from(context).inflate(R.layout.news_item_main, parent,
                    false);
        } else {
            // Inflates the news item from "news_item_rest" layout.
            newsItem = LayoutInflater.from(context).inflate(R.layout.news_item, parent,
                    false);
        }

        // Initializing NewsViewHolder.
        return new NewsViewHolder(newsItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        // Get the news at specified position.
        News currentNews = newsItems.get(position);

        // Binding current news data based on ViewHolder's view type.
        if (holder.getItemViewType() == TYPE_MAIN) {
            holder.setNewsData(currentNews);
        } else {
            holder.setCommonFields(currentNews);
        }
    }

    @Override
    public int getItemCount() {
        // Returns 0 if list is empty.
        if (newsItems == null) {
            return 0;
        }

        // Returns total number of news items under the clicked category.
        return newsItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Sets view type - 1 for item position 0, 4 and 5.
        switch (position) {
            case 0:
            case 4:
            case 5:
                return TYPE_MAIN;
            default:
                // Default view type 0.
                return super.getItemViewType(position);
        }
    }

    /**
     * Sets new news data to Adapter.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setEarthquakeData(ArrayList<News> newsItems) {
        this.newsItems = newsItems;
        notifyDataSetChanged();
    }

    /**
     * Class describes {@link R.layout#news_item_main} item view and is responsible for caching
     * views. It also provides click functionality to the RecyclerView holding news items.
     * <p>
     * Hence, when user clicks on any news, the entire article gets open on the user's browser.
     */
    protected class NewsViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        // Shows the section name under which this news belongs.
        private final TextView newsSection;

        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the web publication date.
        private final TextView newsDate;

        // Shows the publication of the news article.
        private final TextView newsPublication;

        public NewsViewHolder(View itemView) {
            super(itemView);

            // Initialize Views from "news_item" layout.
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail);
            newsSection = itemView.findViewById(R.id.text_section_name);
            newsHeadline = itemView.findViewById(R.id.text_headline);
            newsByline = itemView.findViewById(R.id.text_by_line);
            newsDate = itemView.findViewById(R.id.text_date);
            newsPublication = itemView.findViewById(R.id.text_publication);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(this);
        }

        /**
         * Sets data for item having default View type
         * {@link RecyclerView.Adapter#getItemViewType(int)}.
         */
        public void setCommonFields(News news) {
            // Checks if thumbnail is available.
            String thumbnail = news.getThumbnail();
            if (!TextUtils.isEmpty(thumbnail)) {
                // Makes thumbnail ImageView visible.
                newsThumbnail.setVisibility(View.VISIBLE);

                // Downloads and sets thumbnail using the "Glide" Library.
                Glide.with(context)
                        .load(thumbnail)
                        .placeholder(R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(newsThumbnail);
            } else {
                // Hides thumbnail ImageView.
                newsThumbnail.setVisibility(View.GONE);
            }

            // Sets news section.
            setText(newsSection, news.getSectionName());

            // Sets news headline.
            setText(newsHeadline, news.getHeadline());

            // Sets news author info.
            setText(newsByline, news.getByLine());
        }

        /**
         * Sets data for item view having View type {@link NewsAdapter#TYPE_MAIN}.
         */
        public void setNewsData(News news) {
            setCommonFields(news);

            // Sets news date.
            setText(newsDate, news.getDate());

            // Sets news publication.
            setText(newsPublication, news.getPublication());
        }

        /**
         * Sets text to a TextView. If data is not available, visibility is set to
         * {@link View#GONE}.
         */
        private void setText(TextView textView, String text) {
            if (TextUtils.isEmpty(text)) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(text);
                textView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            // Get the clicked News item.
            News clickedNews = newsItems.get(getAdapterPosition());

            // Set the clicked News item's URL to NewsItemClickListener.
            newsItemClickListener.onNewsItemClick(Uri.parse(clickedNews.getArticleURL()));
        }
    }
}