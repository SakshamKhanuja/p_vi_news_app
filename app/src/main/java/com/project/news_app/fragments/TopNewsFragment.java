package com.project.news_app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.project.news_app.R;
import com.project.news_app.activities.MainActivity;
import com.project.news_app.data.News;
import com.project.news_app.utils.CommonUtils;

/**
 * Fragment shows a single {@link News} -> headline, section name and thumbnail.
 */
public class TopNewsFragment extends Fragment {
    /**
     * Sets context when this fragment is attached to {@link MainActivity}.
     */
    private Context context;

    /**
     * Must store news' "headline", "section name" and "thumbnail".
     */
    private News news;

    /**
     * Stores the number which represents the fragment index.
     */
    private int index;

    /**
     * Accesses {@link News} from the supplied argument.
     */
    public static final String KEY_NEWS = "news";

    /**
     * Accesses number of top news from the supplied arguments.
     */
    public static final String KEY_NEWS_NUMBER = "serial";

    /**
     * Notifies the unavailability of Browser in user's device.
     */
    private Toast toast;

    // Required Default Constructor.
    public TopNewsFragment() {
        // Providing a layout to inflate.
        super(R.layout.layout_top_news_fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get arguments.
        Bundle bundle = getArguments();
        if (bundle != null) {
            news = bundle.getParcelable(KEY_NEWS);
            index = bundle.getInt(KEY_NEWS_NUMBER, 0);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Setting Context.
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Checks "news" is available.
        if (news != null) {
            // Initializing ImageView showing the news thumbnail.
            ImageView imageView = view.findViewById(R.id.top_news_thumbnail);

            // Shows the index number of News.
            TextView serialTextIVew = view.findViewById(R.id.text_top_serial);
            serialTextIVew.setText(context.getString(R.string.top_news_serial, index));

            // Sets news thumbnail.
            CommonUtils.setThumbnail(context, imageView, news.getThumbnailUrl());

            // Shows the section name under which the news belongs.
            TextView sectionTextView = view.findViewById(R.id.top_news_section);
            sectionTextView.setText(news.getSectionName());

            // Shows the news headline.
            TextView headlineTextView = view.findViewById(R.id.top_news_headline);
            headlineTextView.setText(news.getHeadline());

            // News contents is displayed in this view.
            ConstraintLayout layout = view.findViewById(R.id.top_news_layout);

            // Attaches OnClickListener to open the news article in device's browser.
            layout.setOnClickListener(v -> CommonUtils.openBrowserOrApp(context,
                    news.getArticleURL(), toast, R.string.toast_browser_unavailable));
        }
    }
}