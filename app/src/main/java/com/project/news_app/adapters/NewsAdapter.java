package com.project.news_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.project.news_app.R;
import com.project.news_app.constants.NewsAdapterConstants;
import com.project.news_app.data.News;
import com.project.news_app.fragments.HomeFragment;
import com.project.news_app.utils.CommonUtils;
import com.project.news_app.activities.CategoryActivity;

import java.util.ArrayList;

/**
 * Adapter provides {@link News} to RecyclerView in {@link NewsFeedAdapter} and
 * {@link CategoryActivity}.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        NewsAdapterConstants {
    /**
     * Stores news item under clicked news category.
     */
    private ArrayList<News> newsItems;

    /**
     * Used to load images via Glide library.
     */
    private final Context context;

    public NewsAdapter(Context context, ArrayList<News> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

            case TYPE_SEVEN:
                // Shows News headline, author info. and thumbnail.
                return new NewsTypeSevenHolder(layoutInflater.inflate(R.layout.news_item_seven,
                        parent, false));

            case TYPE_EIGHT:
                // Shows News headline, author info. and thumbnail.
                return new NewsTypeEightHolder(layoutInflater.inflate(R.layout.news_item_eight,
                        parent, false));

            case TYPE_NINE:
                // Shows News headline, author info. and thumbnail.
                return new NewsTypeNineHolder(layoutInflater.inflate(R.layout.news_item_nine,
                        parent, false));

            case TYPE_TEN:
                // Shows News headline, author info. and thumbnail.
                return new NewsTypeTenHolder(layoutInflater.inflate(R.layout.news_item_ten,
                        parent, false));

            case TYPE_ELEVEN:
                // Shows News headline, author info. and thumbnail.
                return new NewsTypeElevenHolder(layoutInflater.inflate(R.layout.news_item_eleven,
                        parent, false));

            case TYPE_TWELVE:
                // Shows News headline, section, author info. and thumbnail.
                return new NewsTypeTwelveHolder(layoutInflater.inflate(R.layout.news_item_twelve,
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

            case TYPE_SEVEN:
                ((NewsTypeSevenHolder) holder).setData(currentNews);
                break;

            case TYPE_EIGHT:
                ((NewsTypeEightHolder) holder).setData(currentNews);
                break;

            case TYPE_NINE:
                ((NewsTypeNineHolder) holder).setData(currentNews);
                break;

            case TYPE_TEN:
                ((NewsTypeTenHolder) holder).setData(currentNews);
                break;

            case TYPE_ELEVEN:
                ((NewsTypeElevenHolder) holder).setData(currentNews);
                break;

            case TYPE_TWELVE:
                ((NewsTypeTwelveHolder) holder).setData(currentNews);
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
     * Binds data for news items show in {@link HomeFragment}.
     *
     * @param thumbnail Shows the news article's thumbnail.
     * @param headline  Shows the news article's headline.
     * @param byLine    Shows the news article's author info.
     * @param news      Stores news info. of item.
     */
    private void setupNewsFeed(ImageView thumbnail, TextView headline, TextView byLine, News news) {
        // Setting news thumbnail.
        String thumbnailUrl = news.getThumbnailUrl();

        // Checks if thumbnail link is available.
        if (!TextUtils.isEmpty(thumbnailUrl)) {
            // Shows ImageView.
            thumbnail.setVisibility(View.VISIBLE);

            // Downloading news image.
            CommonUtils.setThumbnail(context, thumbnail, thumbnailUrl);
        } else {
            // Hides ImageView.
            thumbnail.setVisibility(View.GONE);
        }

        // Setting news headline.
        CommonUtils.setText(headline, news.getHeadline());

        // Setting news author info.
        CommonUtils.setText(byLine, news.getByLine());
    }

    /**
     * Binds data for news items having view type {@link NewsAdapterConstants#TYPE_FOUR} and
     * {@link NewsAdapterConstants#TYPE_FIVE}.
     *
     * @param section   Shows the news article's section.
     * @param headline  Shows the news article's headline.
     * @param byLine    Shows the news article's author info.
     * @param thumbnail Shows the news article's thumbnail.
     * @param news      Stores news info. of item.
     */
    private void setupNewsTypeFour(TextView section, TextView headline,
                                   TextView byLine, ImageView thumbnail, News news) {
        // Setting news section.
        CommonUtils.setText(section, news.getSectionName());

        // Setting news headline.
        CommonUtils.setText(headline, news.getHeadline());

        // Setting news author info.
        CommonUtils.setText(byLine, news.getByLine());

        // Setting news thumbnail.
        String thumbnailUrl = news.getThumbnailUrl();

        // Checks if thumbnail link is available.
        if (!TextUtils.isEmpty(thumbnailUrl)) {
            // Shows ImageView.
            thumbnail.setVisibility(View.VISIBLE);

            // Downloading news image.
            CommonUtils.setThumbnail(context, thumbnail, thumbnailUrl);
        } else {
            // Hides ImageView.
            thumbnail.setVisibility(View.GONE);
        }
    }

    /**
     * ViewHolder shows the news' headline.
     * <p>
     * Layout Resource - {@link R.layout#news_item_one}.
     */
    protected class NewsTypeOneHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeOneHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsHeadline = itemView.findViewById(R.id.text_headline_one);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' headline.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            // Setting news headline.
            CommonUtils.setText(newsHeadline, news.getHeadline());
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

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeTwoHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsHeadline = itemView.findViewById(R.id.text_headline_two);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' headline.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            // Setting news headline.
            CommonUtils.setText(newsHeadline, news.getHeadline());
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

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeThreeHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsHeadline = itemView.findViewById(R.id.text_headline_three);
            newsSection = itemView.findViewById(R.id.text_section_name_three);
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_three);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
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
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            // Setting news section.
            CommonUtils.setText(newsSection, news.getSectionName());

            // Converting 16dp to pixels based on screen density.
            int pixels16DP = convertDpToPixels(16F);

            // Converting 8dp to pixels based on screen density.
            int pixels8DP = convertDpToPixels(8F);

            // Setting news thumbnail.
            String thumbnail = news.getThumbnailUrl();

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
                CommonUtils.setThumbnail(context, newsThumbnail, thumbnail);
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
            CommonUtils.setText(newsHeadline, news.getHeadline());
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

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeFourHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsHeadline = itemView.findViewById(R.id.text_headline_four);
            newsSection = itemView.findViewById(R.id.text_section_name_four);
            newsByline = itemView.findViewById(R.id.text_by_line_four);
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_four);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' section, headline, author info. and headline.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            setupNewsTypeFour(newsSection, newsHeadline, newsByline, newsThumbnail, news);
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

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

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
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' section, headline, author info., date, publication and headline.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            setupNewsTypeFour(newsSection, newsHeadline, newsByline, newsThumbnail, news);

            // Setting news publication.
            CommonUtils.setText(newsPublication, news.getPublication());

            // Sets news publication date.
            CommonUtils.setText(newsDate, news.getDate());
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

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeSixHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_six);
            newsHeadline = itemView.findViewById(R.id.text_headline_six);
            newsByline = itemView.findViewById(R.id.text_by_line_six);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' thumbnail, headline and author info.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            setupNewsFeed(newsThumbnail, newsHeadline, newsByline, news);
        }
    }

    /**
     * ViewHolder shows the news' headline, author info. and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_seven}.
     */
    protected class NewsTypeSevenHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeSevenHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_seven);
            newsHeadline = itemView.findViewById(R.id.text_headline_seven);
            newsByline = itemView.findViewById(R.id.text_by_line_seven);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' thumbnail, headline and author info.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            setupNewsFeed(newsThumbnail, newsHeadline, newsByline, news);
        }
    }

    /**
     * ViewHolder shows the news' headline, author info. and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_eight}.
     */
    protected class NewsTypeEightHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeEightHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_eight);
            newsHeadline = itemView.findViewById(R.id.text_headline_eight);
            newsByline = itemView.findViewById(R.id.text_by_line_eight);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' thumbnail, headline and author info.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            setupNewsFeed(newsThumbnail, newsHeadline, newsByline, news);
        }
    }

    /**
     * ViewHolder shows the news' headline, author info. and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_nine}.
     */
    protected class NewsTypeNineHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeNineHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_nine);
            newsHeadline = itemView.findViewById(R.id.text_headline_nine);
            newsByline = itemView.findViewById(R.id.text_by_line_nine);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' thumbnail, headline and author info.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            setupNewsFeed(newsThumbnail, newsHeadline, newsByline, news);
        }
    }

    /**
     * ViewHolder shows the news' headline, author info. and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_ten}.
     */
    protected class NewsTypeTenHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeTenHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_ten);
            newsHeadline = itemView.findViewById(R.id.text_headline_ten);
            newsByline = itemView.findViewById(R.id.text_by_line_ten);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' thumbnail, headline and author info.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            setupNewsFeed(newsThumbnail, newsHeadline, newsByline, news);
        }
    }

    /**
     * ViewHolder shows the news' headline, author info. and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_eleven}.
     */
    protected class NewsTypeElevenHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeElevenHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_eleven);
            newsHeadline = itemView.findViewById(R.id.text_headline_eleven);
            newsByline = itemView.findViewById(R.id.text_by_line_eleven);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' thumbnail, headline and author info.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            setupNewsFeed(newsThumbnail, newsHeadline, newsByline, news);
        }
    }

    /**
     * ViewHolder shows the news' headline, author info. and thumbnail.
     * <p>
     * Layout Resource - {@link R.layout#news_item_twelve}.
     */
    protected class NewsTypeTwelveHolder extends RecyclerView.ViewHolder {
        // Shows the news headline.
        private final TextView newsHeadline;

        // Shows the section name under which this news belongs.
        private final TextView newsSection;

        // Shows the author info.
        private final TextView newsByline;

        // Shows the news thumbnail.
        private final ImageView newsThumbnail;

        // Stores news article.
        private String articleUrl;

        // Stores news headline;
        private String headline;

        public NewsTypeTwelveHolder(View itemView) {
            super(itemView);

            // Initialize Views.
            newsThumbnail = itemView.findViewById(R.id.image_thumbnail_twelve);
            newsSection = itemView.findViewById(R.id.text_section_name_twelve);
            newsHeadline = itemView.findViewById(R.id.text_headline_twelve);
            newsByline = itemView.findViewById(R.id.text_by_line_twelve);

            // Attach OnClickListener to the entire item view.
            itemView.setOnClickListener(view -> CommonUtils.openBrowserOrApp(context, articleUrl,
                    R.string.toast_browser_unavailable));

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });

            // Attaching OnLongClickListener to the entire item view.
            itemView.setOnLongClickListener(view -> {
                CommonUtils.shareNews(context, headline, articleUrl);
                return true;
            });
        }

        /**
         * Sets news' thumbnail, headline and author info.
         */
        public void setData(News news) {
            // Initializing news article url.
            articleUrl = news.getArticleURL();

            // Initializing news headline.
            headline = news.getHeadline();

            setupNewsTypeFour(newsSection, newsHeadline, newsByline, newsThumbnail, news);
        }
    }
}