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
import com.project.news_app.constants.NetworkUtilsConstants;
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
        super(R.layout.basic_recycler_view_dark);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Initializing RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_dark);

        // Linking LayoutManager to RecyclerView.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Optimizes RecyclerView.
        recyclerView.setHasFixedSize(true);

        // Linking Adapter to RecyclerView.
        mAdapter = new PodcastAdapter(null);
        recyclerView.setAdapter(mAdapter);

        // Downloads a list of episodes of the "Today in Focus" podcast from "The Guardian" api.
        LoaderManager.getInstance(this).initLoader(1, null, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Podcast>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<Podcast>>(requireContext()) {

            // Stores the downloaded episodes under "Today in Focus" podcast.
            private ArrayList<Podcast> podcasts;

            @Override
            protected void onStartLoading() {
                // Checks if cached (previously downloaded) podcast data is available.
                if (podcasts != null && podcasts.size() > 0) {
                    deliverResult(podcasts);
                } else {
                    // Starts a background thread to download fresh podcast episodes info.
                    forceLoad();
                }
            }

            @NonNull
            @Override
            public ArrayList<Podcast> loadInBackground() {
                // Downloads JSON response.
                String jsonResponse = NetworkUtils.downloadNewsData(NetworkUtils.
                        makeNewsUrl(requireContext(), NetworkUtilsConstants.PATH_FOCUS,
                                NetworkUtilsConstants.QP_VALUE_FOCUS_FIELDS,
                                NetworkUtilsConstants.SIZE_PODCAST));

                // Parses JSON response to a list of type Podcast.
                ArrayList<Podcast> podcasts = JsonUtils.parsePodcastList(jsonResponse);

                // Adding a head layout to "podcasts".
                Podcast head = new Podcast();
                head.setViewType(PodcastAdapter.PODCAST_ABOUT);
                podcasts.add(0, head);

                return podcasts;
            }

            @Override
            public void deliverResult(@Nullable ArrayList<Podcast> data) {
                // Caching downloaded podcast episodes info.
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
            // Updating the contents of PodcastAdapter.
            mAdapter.setPodcastData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Podcast>> loader) {
        // Clearing up the PodcastAdapter.
        mAdapter.setPodcastData(null);
    }
}