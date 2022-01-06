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
import com.project.news_app.adapters.PodcastAdapter;
import com.project.news_app.data.Podcast;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Shows a list of episodes under "Today in Focus" Podcast available by "The Guardian".
 */
public class PodcastFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<Podcast>> {

    // Adapter provides Podcast items to RecyclerView.
    private PodcastAdapter mAdapter;

    // Required Default Constructor.
    public PodcastFragment() {
        // Providing a layout to inflate.
        super(R.layout.basic_recycler_view_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Initializing RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        /*
         * Linking RecyclerView with a LayoutManager. LayoutManager is responsible for Recycling
         * ITEM views when they're scrolled OFF the screen. It also determines how the collection
         * of these ITEMS are displayed.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        /*
         * Further optimizing RecyclerView to NOT invalidate the whole layout, if any change
         * occurs in the contents of the adapter with which it is linked.
         *
         * Fixed size is set to "true", as the contents of the list of Categories remains the same.
         */
        recyclerView.setHasFixedSize(true);

        // Linking Adapter to RecyclerView.
        mAdapter = new PodcastAdapter(null);
        recyclerView.setAdapter(mAdapter);

        // Downloading clicked news category data in a background Thread.
        LoaderManager.getInstance(this).initLoader(1, null, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Podcast>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<Podcast>>(requireContext()) {

            // Stores the downloaded clicked news category.
            private ArrayList<Podcast> podcasts;

            @Override
            protected void onStartLoading() {
                // Using previous available result.
                if (podcasts != null && podcasts.size() > 0) {
                    // Use cached result.
                    deliverResult(podcasts);
                } else {
                    // Starts a background thread to download news info.
                    forceLoad();
                }
            }

            @NonNull
            @Override
            public ArrayList<Podcast> loadInBackground() {
                // Downloads JSON response.
                String jsonResponse = NetworkUtils.downloadNewsData(NetworkUtils.
                        makeTodayInFocusUrl(getContext()));

                // Parses JSON response to a list of type Podcast.
                return JsonUtils.parsePodcastList(jsonResponse);
            }

            @Override
            public void deliverResult(@Nullable ArrayList<Podcast> data) {
                // Caching downloaded podcast info.
                if (data != null) {
                    podcasts = data;
                }
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Podcast>> loader,
                               ArrayList<Podcast> data) {
        if (data != null && data.size() > 0) {
            // Updating the contents of NewsAdapter.
            mAdapter.setPodcastData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Podcast>> loader) {
        // Clearing up the PodcastAdapter.
        mAdapter.setPodcastData(null);
    }
}