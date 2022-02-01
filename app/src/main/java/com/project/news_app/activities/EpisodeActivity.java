package com.project.news_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.project.news_app.R;
import com.project.news_app.adapters.EpisodeAdapter;
import com.project.news_app.constants.NetworkUtilsConstants;
import com.project.news_app.data.Episode;
import com.project.news_app.data.Podcast;
import com.project.news_app.databinding.BasicRecyclerViewBinding;
import com.project.news_app.fragments.PodcastFragment;
import com.project.news_app.utils.CommonUtils;
import com.project.news_app.utils.JsonUtils;
import com.project.news_app.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Shows a list of {@link Episode} in RecyclerView in a VERTICAL orientation.
 */
public class EpisodeActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<Episode>> {
    /**
     * Stores basic title, path and thumbnail of the clicked Podcast in {@link PodcastFragment}.
     */
    private Podcast clickedPodcast;

    /**
     * Adapter {@link Episode} items to RecyclerView.
     */
    private EpisodeAdapter adapter;

    /**
     * Performs View Binding.
     */
    private BasicRecyclerViewBinding binding;

    /**
     * Provides access to the clicked {@link Podcast}.
     */
    public static final String EXTRA_PODCAST = "com.project.news_app.Podcast";

    /**
     * Used to access the clicked {@link Podcast} across configuration changes.
     */
    private static final String KEY_PODCAST = "podcast";

    /**
     * Checks for connection availability.
     */
    private ConnectivityManager connectivityManager;

    /**
     * Notifies user when app loses/gains internet connectivity using a {@link Snackbar}.
     */
    private ConnectivityManager.NetworkCallback networkCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Perform operations when EpisodeActivity is started for the first time.
        if (savedInstanceState == null) {
            // Get Intent that started this Activity to get the clicked Podcast.
            Intent intent = getIntent();

            if (!(intent != null && intent.hasExtra(EXTRA_PODCAST))) {
                // Control goes back to AllPodcastFragment (in MainActivity).
                return;
            } else {
                clickedPodcast = intent.getParcelableExtra(EXTRA_PODCAST);
            }
        } else {
            // Restoring the clicked Podcast.
            clickedPodcast = savedInstanceState.getParcelable(KEY_PODCAST);
        }

        // Setting content view.
        binding = BasicRecyclerViewBinding.inflate((LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        // Replace Toolbar as ActionBar.
        setSupportActionBar(binding.toolbar);

        // Set podcast title as app bar title.
        setTitle(clickedPodcast.getTitle());
        ActionBar actionBar = getSupportActionBar();

        // Show the "Up" Button on Toolbar.
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initializing SwipeRefreshLayout.
        CommonUtils.setRefreshLayoutColors(binding.swipeToRefresh);

        // Initializing LoaderManager.
        LoaderManager loaderManager = LoaderManager.getInstance(this);

        // Attaching listener.
        binding.swipeToRefresh.setOnRefreshListener(() ->
                loaderManager.restartLoader(1, null, this));

        // Initializing ConnectivityManager.
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Checks if app is NOT connected/connecting to internet.
        if (!CommonUtils.checkNetworkAvailability(connectivityManager)) {
            // Hide ProgressBar.
            hideProgressBar();

            // No episodes available.
            showEmptyView();
        }

        // Initializing NetworkCallback.
        networkCallback = CommonUtils.getNetworkCallback(this,
                binding.detailCoordinatorLayout, getLifecycle());

        // Registering NetworkCallback.
        connectivityManager.registerDefaultNetworkCallback(networkCallback);

        // Linking Adapter to RecyclerView.
        adapter = new EpisodeAdapter(this, null);

        // Setting up RecyclerView.
        CommonUtils.setupRecyclerView(this, binding.recyclerViewDark, adapter,
                LinearLayoutManager.VERTICAL);

        // Downloads a list of episodes of the clicked Podcast from "The Guardian" API.
        loaderManager.initLoader(1, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregistering NetworkCallback.
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    /**
     * Hides indeterminate {@link R.id#progressBar}.
     */
    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    /**
     * Indicates no {@link Episode} items are available.
     */
    private void showEmptyView() {
        // Change background.
        binding.constraintLayout.setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorDarkerII));

        // No episodes available in Podcast.
        CommonUtils.setText(binding.statusDataNotAvailable, getString(
                R.string.episodes_not_available));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up the clicked Podcast.
        outState.putParcelable(KEY_PODCAST, clickedPodcast);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Episode>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<Episode>>(this) {

            // Stores the downloaded episodes of the clicked Podcast.
            private ArrayList<Episode> episodes;

            @Override
            protected void onStartLoading() {
                // Checks if cached (previously downloaded) podcast episodes are available.
                if (episodes != null && episodes.size() > 0) {
                    deliverResult(episodes);
                } else {
                    // Starts a background thread to download fresh podcast episodes info.
                    forceLoad();
                }
            }

            @NonNull
            @Override
            public ArrayList<Episode> loadInBackground() {
                // Downloads JSON response.
                String jsonResponse = NetworkUtils.downloadNewsData(NetworkUtils.
                        makeNewsUrl(getContext(), getString(clickedPodcast.getPath()),
                                NetworkUtilsConstants.QP_VALUE_PODCAST,
                                NetworkUtilsConstants.SIZE_PODCAST));

                /*
                 * Parses the downloaded response to a list of type "Episode".
                 *
                 * It also adds info. of the clicked Podcast in order to setup the
                 * "PodcastAboutViewHolder" ViewHolder in "PodcastAdapter".
                 */
                return JsonUtils.parsePodcastList(jsonResponse, clickedPodcast);
            }

            @Override
            public void deliverResult(@Nullable ArrayList<Episode> data) {
                // Caching downloaded podcast episodes info.
                if (data != null) {
                    episodes = data;
                }
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Episode>> loader,
                               ArrayList<Episode> data) {
        // Hiding ProgressBar.
        hideProgressBar();

        // Hiding SwipeRefreshLayout.
        if(binding.swipeToRefresh.isRefreshing()) {
            binding.swipeToRefresh.setRefreshing(false);
        }

        if (data != null && data.size() > 0) {
            // Hide status TextView.
            binding.statusDataNotAvailable.setVisibility(View.GONE);

            // Change background.
            binding.constraintLayout.setBackground(AppCompatResources.getDrawable(this,
                    R.drawable.gradient_podcast));

            // Updating the contents of PodcastAdapter.
            adapter.setEpisodeData(data);
        } else {
            // No episodes available.
            showEmptyView();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Episode>> loader) {
        // Clearing up the PodcastAdapter.
        adapter.setEpisodeData(null);
    }
}