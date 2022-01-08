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

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sets context.
        context = parent.getContext();

        // Inflates views from layout.
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (viewType) {
            case TYPE_ONE:
                // Shows News headline.
                return new NewsTypeOneHolder(layoutInflater.inflate(R.layout.news_item_one,
                        parent, false));

            case TYPE_TWO:
                // Shows News headline having bottom padding.
                return new NewsTypeTwoHolder(layoutInflater.inflate(R.layout.news_item_two,
                        parent, false));

            case TYPE_THREE:
                // Shows News headline, section, and thumbnail.
                return new NewsTypeThreeHolder(layoutInflater.inflate(R.layout.news_item_three,
                        parent, false));

            case TYPE_FIVE:
                /*
                 * Shows News headline, section, author info., publication date, publication
                 * and thumbnail.
                 */
                return new NewsTypeFiveHolder(layoutInflater.inflate(R.layout.news_item_five,
                        parent, false));

            case TYPE_SIX:
                // Shows News headline, author info. and thumbnail.
                return new NewsTypeSixHolder(layoutInflater.inflate(R.layout.news_item_six,
                        parent, false));

            case TYPE_FOUR:
            default:
                // Shows News headline, section, author info. and thumbnail.
                return new NewsTypeFourHolder(layoutInflater.inflate(R.layout.news_item_four,
                        parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Get the news at specified position.
        News currentNews = newsItems.get(position);

        // Binding current news data based on view type.
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                ((NewsTypeOneHolder) holder).setData(currentNews);
                break;

            case TYPE_TWO:
                ((NewsTypeTwoHolder) holder).setData(currentNews);
                break;

            case TYPE_THREE:
                ((NewsTypeThreeHolder) holder).setData(currentNews);
                break;

            case TYPE_FOUR:
                ((NewsTypeFourHolder) holder).setData(currentNews);
                break;

            case TYPE_FIVE:
                ((NewsTypeFiveHolder) holder).setData(currentNews);
                break;

            case TYPE_SIX:
                ((NewsTypeSixHolder) holder).setData(currentNews);
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
    public void setNewsData(ArrayList<News> newsItems) {
        this.newsItems = newsItems;
        notifyDataSetChanged();
    }

    /**
     * Passes the {@link Uri} of the clicked {@link News} item to {@link NewsItemClickListener}.
     *
     * @param adapterPosition Position of the clicked news item in adapter.
     */
    private void setClickedNewsURL(int adapterPosition) {
        News clickedNews = newsItems.get(adapterPosition);

        // Set the clicked News item's URL to NewsItemClickListener.
        newsItemClickListener.onNewsItemClick(Uri.parse(clickedNews.getArticleURL()));
    }

    /**
     * Sets text to a TextView.
     */
    private void setText(TextView textView, String text) {
        textView.setText(text);
    }

    /**
     * Downloads and sets news' thumbnail.
     */
    private void downloadAndSetImage(ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * ViewHolder shows the news' headline.
     * <p>
     * Layout Resource - {@link R.layout#news_item_one}.
     */
    protected class NewsTypeOneHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        public NewsTypeOneHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsHeadline = itemView.findViewById(R.id.text_headline_one);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> setClickedNewsURL(getAdapterPosition()));
        }

        /**
         * Sets news' headline.
         */
        public void setData(News news) {
            // Setting news headline.
            setText(newsHeadline, news.getHeadline());
        }
    }

    /**
     * ViewHolder shows the news' headline and an additional space at the bottom that mimics a
     * divider.
     * <p>
     * Layout Resource - {@link R.layout#news_item_two}.
     */
    protected class NewsTypeTwoHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        public NewsTypeTwoHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsHeadline = itemView.findViewById(R.id.text_headline_two);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> setClickedNewsURL(getAdapterPosition()));
        }

        /**
         * Sets news' headline.
         */
        public void setData(News news) {
            // Setting news headline.
            setText(newsHeadline, news.getHeadline());
        }
    }

    /**
     * ViewHolder shows the news' headline, section name and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_three}.
     */
    protected class NewsTypeThreeHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the section name under which this news belongs.
        private final TextView newsSection;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        public NewsTypeThreeHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsHeadline = itemView.findViewById(R.id.text_headline_three);
            newsSection = itemView.findViewById(R.id.text_section_name_three);
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_three);


            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> setClickedNewsURL(getAdapterPosition()));
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
         * Sets news' section, thumbnail and headline.
         */
        public void setData(News news) {
            // Setting news section.
            setText(newsSection, news.getSectionName());

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
                downloadAndSetImage(newsThumbnail, thumbnail);
            } else {
                // Set Padding on all sides to be 16dp.
                newsHeadline.setPadding(pixels16DP, pixels16DP, pixels16DP, pixels16DP);

                // Set Black background to news headline.
                newsHeadline.setBackgroundColor(ContextCompat.getColor(context,
                        R.color.colorAccent));

                // Hides ImageView.
                newsThumbnail.setVisibility(View.GONE);
            }

            // Setting news headline.
            setText(newsHeadline, news.getHeadline());
        }
    }

    /**
     * ViewHolder shows the news' headline, section name, author info. and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_four}.
     */
    protected class NewsTypeFourHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the section name under which this news belongs.
        private final TextView newsSection;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;


        public NewsTypeFourHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsHeadline = itemView.findViewById(R.id.text_headline_four);
            newsSection = itemView.findViewById(R.id.text_section_name_four);
            newsByline = itemView.findViewById(R.id.text_by_line_four);
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_four);


            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> setClickedNewsURL(getAdapterPosition()));
        }

        /**
         * Sets news' section, headline, author info. and headline.
         */
        public void setData(News news) {
            // Setting news section.
            setText(newsSection, news.getSectionName());

            // Setting news headline.
            setText(newsHeadline, news.getHeadline());

            // Setting news author info.
            setText(newsByline, news.getByLine());

            // Setting news thumbnail.
            String thumbnail = news.getThumbnail();

            // Checks if thumbnail link is available.
            if (!TextUtils.isEmpty(thumbnail)) {
                // Shows ImageView.
                newsThumbnail.setVisibility(View.VISIBLE);

                // Downloading news image.
                downloadAndSetImage(newsThumbnail, thumbnail);
            } else {
                // Hides ImageView.
                newsThumbnail.setVisibility(View.GONE);
            }
        }
    }

    /**
     * ViewHolder shows the news' headline, section name, author info., publication data,
     * publication and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_five}.
     */
    protected class NewsTypeFiveHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the section name under which this news belongs.
        private final TextView newsSection;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the web publication date.
        private final TextView newsDate;

        // Shows the publication of the news article.
        private final TextView newsPublication;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        public NewsTypeFiveHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsHeadline = itemView.findViewById(R.id.text_headline_five);
            newsSection = itemView.findViewById(R.id.text_section_name_five);
            newsByline = itemView.findViewById(R.id.text_by_line_five);
            newsDate = itemView.findViewById(R.id.text_date_five);
            newsPublication = itemView.findViewById(R.id.text_publication_five);
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_five);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> setClickedNewsURL(getAdapterPosition()));
        }

        /**
         * Sets news' section, headline, author info., date, publication and headline.
         */
        public void setData(News news) {
            // Setting news thumbnail.
            String thumbnail = news.getThumbnail();

            // Checks if thumbnail link is available.
            if (!TextUtils.isEmpty(thumbnail)) {
                // Shows ImageView.
                newsThumbnail.setVisibility(View.VISIBLE);

                // Downloading news image.
                downloadAndSetImage(newsThumbnail, thumbnail);
            } else {
                // Hides ImageView.
                newsThumbnail.setVisibility(View.GONE);
            }

            // Setting news section.
            setText(newsSection, news.getSectionName());

            // Setting news headline.
            setText(newsHeadline, news.getHeadline());

            // Setting news author info.
            setText(newsByline, news.getByLine());

            // Setting news publication.
            setText(newsPublication, news.getPublication());

            // Sets news publication date.
            setText(newsDate, news.getDate());
        }
    }

    /**
     * ViewHolder shows the news' headline, author info. and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_six}.
     */
    protected class NewsTypeSixHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        public NewsTypeSixHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_six);
            newsHeadline = itemView.findViewById(R.id.text_headline_six);
            newsByline = itemView.findViewById(R.id.text_by_line_six);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> setClickedNewsURL(getAdapterPosition()));
        }

        /**
         * Sets news' thumbnail, headline and author info.
         */
        public void setData(News news) {
            // Setting news thumbnail.
            String thumbnail = news.getThumbnail();

            // Checks if thumbnail link is available.
            if (!TextUtils.isEmpty(thumbnail)) {
                // Shows ImageView.
                newsThumbnail.setVisibility(View.VISIBLE);

                // Downloading news image.
                downloadAndSetImage(newsThumbnail, thumbnail);
            } else {
                // Hides ImageView.
                newsThumbnail.setVisibility(View.GONE);
            }

            // Setting news headline.
            setText(newsHeadline, news.getHeadline());

            // Setting news author info.
            setText(newsByline, news.getByLine());
        }
    }
}