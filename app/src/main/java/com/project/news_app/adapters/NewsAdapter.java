package com.project.news_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.project.news_app.R;
import com.project.news_app.constants.NewsAdapterConstants;
import com.project.news_app.data.News;
import com.project.news_app.activities.CategoryActivity;

import java.util.ArrayList;

/**
 * Provides {@link NewsViewHolder} ViewHolder inflated from {@link R.layout#news_item_five}
 * and {@link R.layout#news_item_four} item view layout to {@link R.id#recycler_view} RecyclerView.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements
        NewsAdapterConstants {

    /**
     * Provides click functionality to news items in {@link CategoryActivity}.
     */
    private final NewsItemClickListener newsItemClickListener;

    // Stores news item under clicked news category.
    private ArrayList<News> newsItems;

    // Used to load images via Glide library.
    private Context context;

    public NewsAdapter(ArrayList<News> newsItems, NewsItemClickListener newsItemClickListener) {
        this.newsItems = newsItems;
        this.newsItemClickListener = newsItemClickListener;
    }

    // Provides click functionality to News items.
    public interface
    NewsItemClickListener {
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

        // Stores the layout resource ID of the item View.
        int resource;

        // Setting the news item layout based on View type.
        switch (viewType) {
            case 1:
                // Shows news' headline.
                resource = R.layout.news_item_one;
                break;

            case 2:
                // Shows news' headline and a divider.
                resource = R.layout.news_item_two;
                break;

            case 3:
                // Shows news' section, thumbnail, and headline.
                resource = R.layout.news_item_three;
                break;

            case 5:
                /*
                 * Shows news' thumbnail, section, headline, author info., publication date, and
                 * publication.
                 */
                resource = R.layout.news_item_five;
                break;

            case 4:
            default:
                // Shows news' section, headline, author info., and thumbnail.
                resource = R.layout.news_item_four;
                break;
        }

        // Initializing NewsViewHolder.
        return new NewsViewHolder(LayoutInflater.from(context).inflate(resource, parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        // Get the news at specified position.
        News currentNews = newsItems.get(position);

        // Binding current news data based on ViewHolder's view type.
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
            case TYPE_TWO:
                holder.bindDataTypeOneAndTwo(currentNews);
                break;

            case TYPE_THREE:
                holder.bindDateTypeThree(currentNews);
                break;

            case TYPE_FOUR:
                holder.bindDateTypeFour(currentNews);
                break;

            case TYPE_FIVE:
                holder.bindDataTypeFive(currentNews);
                break;
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
        return newsItems.get(position).getViewType();
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
     * Class describes {@link R.layout#news_item_five} item view and is responsible for caching
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
         * Converts Density-Independent values to pixel value based on screen density.
         */
        private int convertDpToPixels(float dp) {
            return (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp,
                    context.getResources().getDisplayMetrics());
        }

        /**
         * Download news' thumbnail and sets to {@link NewsViewHolder#newsThumbnail} ImageView.
         *
         * @param thumbnailUrl Url in String format that points to news' thumbnail.
         */
        private void downloadAndSetImage(String thumbnailUrl) {
            Glide.with(context)
                    .load(thumbnailUrl)
                    .placeholder(R.drawable.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(newsThumbnail);
        }

        /**
         * Sets the news' headline.
         * <p>
         * Used for View Type {@link NewsAdapterConstants#TYPE_ONE},
         * {@link NewsAdapterConstants#TYPE_TWO} and {@link NewsAdapterConstants#TYPE_THREE}.
         *
         * @param news Contains news info for item view.
         */
        public void bindDataTypeOneAndTwo(News news) {
            setText(newsHeadline, news.getHeadline());
        }

        /**
         * Sets the news' section, thumbnail, and headline.
         * <p>
         * Used for View Type {@link NewsAdapterConstants#TYPE_THREE}.
         *
         * @param news Contains news info for item view.
         */
        public void bindDateTypeThree(News news) {
            // Sets news section.
            setText(newsSection, news.getSectionName());

            // Sets news headline.
            bindDataTypeOneAndTwo(news);

            // Converting 16dp to pixels based on screen density.
            int pixels16DP = convertDpToPixels(16F);

            // Converting 8dp to pixels based on screen density.
            int pixels8DP = convertDpToPixels(8F);

            // Setting news thumbnail.
            String thumbnail = news.getThumbnail();

            // Checks if thumbnail link is available.
            if (!TextUtils.isEmpty(thumbnail)) {
                // Set Bottom Padding to 8dp.
                newsHeadline.setPadding(pixels16DP, pixels16DP, pixels16DP, pixels8DP);

                // Set gradient background to news headline.
                newsHeadline.setBackground(AppCompatResources.getDrawable(context,
                        R.drawable.gradient_headline));

                // Shows ImageView.
                newsThumbnail.setVisibility(View.VISIBLE);

                // Downloading news image.
                downloadAndSetImage(thumbnail);
            } else {
                // Set Padding on all sides to be 16dp.
                newsHeadline.setPadding(pixels16DP, pixels16DP, pixels16DP, pixels16DP);

                // Set Black background to news headline.
                newsHeadline.setBackgroundColor(ContextCompat.getColor(context,
                        R.color.colorAccent));

                // Hides ImageView.
                newsThumbnail.setVisibility(View.GONE);
            }
        }

        /**
         * Sets the news' section, headline, author info., and thumbnail.
         * <p>
         * Used for View Type {@link NewsAdapterConstants#TYPE_FOUR}.
         *
         * @param news Contains news info for item view.
         */
        public void bindDateTypeFour(News news) {
            // Sets news section.
            setText(newsSection, news.getSectionName());

            // Sets news headline.
            bindDataTypeOneAndTwo(news);

            // Sets news author info.
            setText(newsByline, news.getByLine());

            // Setting news thumbnail.
            String thumbnail = news.getThumbnail();

            // Checks if thumbnail link is available.
            if (!TextUtils.isEmpty(thumbnail)) {
                // Shows ImageView.
                newsThumbnail.setVisibility(View.VISIBLE);

                // Downloading news image.
                downloadAndSetImage(thumbnail);
            } else {
                // Hides ImageView.
                newsThumbnail.setVisibility(View.GONE);
            }
        }

        /**
         * Sets the news' thumbnail, section, headline, author info., publication date, and
         * publication.
         * <p>
         * Used for View Type {@link NewsAdapterConstants#TYPE_FIVE}.
         *
         * @param news Contains news info for item view.
         */
        public void bindDataTypeFive(News news) {
            // Sets news section, headline, author info., and thumbnail.
            bindDateTypeFour(news);

            // Sets publication date.
            setText(newsDate, news.getDate());

            // Sets publication.
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