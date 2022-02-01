package com.project.news_app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.project.news_app.R;
import com.project.news_app.adapters.PodcastAdapter;
import com.project.news_app.data.Podcast;
import com.project.news_app.activities.MainActivity;

import java.util.ArrayList;

/**
 * {@link RecyclerView} displays a list of {@link Podcast} in a grid.
 */
public class PodcastFragment extends Fragment {
    /**
     * Stores info. of available podcasts from "The Guardian" API.
     */
    private ArrayList<Podcast> podcasts;

    /**
     * Used to access all podcasts info. on orientation changes.
     */
    private static final String KEY_PODCASTS = "podcasts";

    /**
     * Sets context when this fragment is attached to {@link MainActivity}.
     */
    private Context context;

    // Required Default Constructor.
    public PodcastFragment() {
        // Providing a layout to inflate.z
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
        if (savedInstanceState != null) {
            // Restoring all available podcasts.
            podcasts = savedInstanceState.getParcelableArrayList(KEY_PODCASTS);
        } else {
            // Initializing list of all available podcasts.
            podcasts = getPodcasts();
        }

        // Hide ProgressBar.
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        // Disable SwipeRefreshLayout.
        SwipeRefreshLayout layout = view.findViewById(R.id.swipe_to_refresh);
        layout.setEnabled(false);

        // Setting title.
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bottom_podcast);

        // Initializing RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_dark);

        // Optimizes RecyclerView.
        recyclerView.setHasFixedSize(true);

        // Linking a GridLayoutManager to RecyclerView.
        GridLayoutManager layoutManager = new GridLayoutManager(context, getSpanCount());
        recyclerView.setLayoutManager(layoutManager);

        // Linking Adapter to RecyclerView.
        recyclerView.setAdapter(new PodcastAdapter(context, podcasts));
    }

    /**
     * Calculates how many columns of podcast can be shown based on screen properties and
     * individual column width.
     *
     * @return Span count based on column and screen width.
     */
    private int getSpanCount() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / 196 + 0.5); // +0.5 for correct rounding to int.
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up all available podcasts.
        outState.putParcelableArrayList(KEY_PODCASTS, podcasts);
    }

    /**
     * Returns available podcasts from "The Guardian" API.
     */
    private ArrayList<Podcast> getPodcasts() {
        ArrayList<Podcast> podcasts = new ArrayList<>();

        // Adding Podcast - "Today in Focus".
        podcasts.add(new Podcast(R.string.podcast_title_today_in_focus,
                R.string.podcast_path_today_in_focus, R.drawable.thumbnail_today_in_focus));

        // Adding Podcast - "Football Weekly".
        podcasts.add(new Podcast(R.string.podcast_title_football_weekly,
                R.string.podcast_path_football_weekly, R.drawable.thumbnail_football_weekly));

        // Adding Podcast - "The Audio Long Read".
        podcasts.add(new Podcast(R.string.podcast_title_the_audio_long_read,
                R.string.podcast_path_the_audio_long_read,
                R.drawable.thumbnail_the_audio_long_read));

        // Adding Podcast - "Politics Weekly".
        podcasts.add(new Podcast(R.string.podcast_title_politics_weekly,
                R.string.podcast_path_politics_weekly, R.drawable.thumbnail_politics_weekly));

        // Adding Podcast - "Science Weekly".
        podcasts.add(new Podcast(R.string.podcast_title_science_weekly,
                R.string.podcast_path_science_weekly, R.drawable.thumbnail_science_weekly));

        // Adding Podcast - "Comfort Eating with Grace Dent".
        podcasts.add(new Podcast(R.string.podcast_title_comfort_eating_with_grace_dent,
                R.string.podcast_path_comfort_eating_with_grace_dent,
                R.drawable.thumbnail_comfort_eating_with_grace_dent));

        // Adding Podcast - "Australia Reads".
        podcasts.add(new Podcast(R.string.podcast_title_australia_reads,
                R.string.podcast_path_australia_reads,
                R.drawable.thumbnail_australia_reads));

        // Adding Podcast - "Full Story".
        podcasts.add(new Podcast(R.string.podcast_title_full_story,
                R.string.podcast_path_full_story, R.drawable.thumbnail_full_story));

        // Adding Podcast - "Australian Politics Live".
        podcasts.add(new Podcast(R.string.podcast_title_australian_politics_live,
                R.string.podcast_path_australian_politics_live,
                R.drawable.thumbnail_australian_politics));

        // Adding Podcast - "The Guardian Books".
        podcasts.add(new Podcast(R.string.podcast_title_the_guardian_books,
                R.string.podcast_path_the_guardian_books, R.drawable.thumbnail_the_guardian_books));

        // Adding Podcast - "The Guardian's Brexit Means".
        podcasts.add(new Podcast(R.string.podcast_title_the_guardian_brexit_means,
                R.string.podcast_path_the_guardian_brexit_means,
                R.drawable.thumbnail_the_guardians_brexit_means));

        // Adding Podcast - "Forgotten Stories of Football".
        podcasts.add(new Podcast(R.string.podcast_title_forgotten_stories_of_football,
                R.string.podcast_path_forgotten_stories_of_football,
                R.drawable.thumbnail_forgotten_stories_of_football));

        // Adding Podcast - "Innermost".
        podcasts.add(new Podcast(R.string.podcast_title_innermost,
                R.string.podcast_path_innermost, R.drawable.thumbnail_innermost));

        // Adding Podcast - "The Spin".
        podcasts.add(new Podcast(R.string.podcast_title_the_spin,
                R.string.podcast_path_the_spin, R.drawable.thumbnail_the_spin));

        // Adding Podcast - "Chips with Everything".
        podcasts.add(new Podcast(R.string.podcast_title_chips_with_everything,
                R.string.podcast_path_chips_with_everything,
                R.drawable.thumbnail_chips_with_everything));

        // Adding Podcast - "Beyond the Blade".
        podcasts.add(new Podcast(R.string.podcast_title_beyond_the_blade,
                R.string.podcast_path_beyond_the_blade, R.drawable.thumbnail_beyond_the_blade));

        // Adding Podcast - "We Need to Talk About".
        podcasts.add(new Podcast(R.string.podcast_title_we_need_to_talk_about,
                R.string.podcast_path_we_need_to_talk_about,
                R.drawable.thumbnail_we_need_to_talk_about));

        // Adding Podcast - "The Story".
        podcasts.add(new Podcast(R.string.podcast_title_the_story, R.string.podcast_path_the_story,
                R.drawable.thumbnail_the_story));

        // Adding Podcast - "Small Changes".
        podcasts.add(new Podcast(R.string.podcast_title_small_changes,
                R.string.podcast_path_small_changes, R.drawable.thumbnail_small_changes));

        // Adding Podcast - "A Neuroscientist Explains".
        podcasts.add(new Podcast(R.string.podcast_title_a_neuroscientist_explains,
                R.string.podcast_path_a_neuroscientist_explains,
                R.drawable.thumbnail_a_neuroscientist_explains));

        // Adding Podcast - "The Guardian UK: Culture Podcast".
        podcasts.add(new Podcast(R.string.podcast_title_the_guardian_uk_culture_podcast,
                R.string.podcast_path_the_guardian_uk_culture_podcast,
                R.drawable.thumbnail_the_guardian_uk_culture_podcast));

        // Adding Podcast - "Behind the Lines".
        podcasts.add(new Podcast(R.string.podcast_title_behind_the_lines,
                R.string.podcast_path_behind_the_lines, R.drawable.thumbnail_behind_the_lines));

        // Adding Podcast - "Close Encounters".
        podcasts.add(new Podcast(R.string.podcast_title_close_encounters,
                R.string.podcast_path_close_encounters,
                R.drawable.thumbnail_close_encounters));

        // Adding Podcast - "What would a feminist do?".
        podcasts.add(new Podcast(R.string.podcast_title_what_would_a_feminist_do,
                R.string.podcast_path_what_would_a_feminist_do,
                R.drawable.thumbnail_what_would_a_feminist_do));

        // Adding Podcast - "Token".
        podcasts.add(new Podcast(R.string.podcast_title_token, R.string.podcast_path_token,
                R.drawable.thumbnail_token));

        // Adding Podcast - "The Reckoning".
        podcasts.add(new Podcast(R.string.podcast_title_the_reckoning,
                R.string.podcast_path_the_reckoning, R.drawable.thumbnail_the_reckoning));

        // Adding Podcast - "The Guardian Australia: Culture Podcast".
        podcasts.add(new Podcast(R.string.podcast_title_the_guardian_australia_culture_podcast,
                R.string.podcast_path_the_guardian_australia_culture_podcast,
                R.drawable.thumbnail_the_guardian_australia_culture_podcast));

        // Adding Podcast - "Beginner: The Guardian Guide to Running".
        podcasts.add(new Podcast(R.string.podcast_title_beginner_the_guardian_guide_to_running,
                R.string.podcast_path_beginner_the_guardian_guide_to_running,
                R.drawable.thumbnail_beginner_the_guardian_guide_to_running));

        // Adding Podcast - "Advanced: The Guardian Guide to Running".
        podcasts.add(new Podcast(R.string.podcast_title_advanced_the_guardian_guide_to_running,
                R.string.podcast_path_advanced_the_guardian_guide_to_running,
                R.drawable.thumbnail_advanced_the_guardian_guide_to_running));

        return podcasts;
    }
}