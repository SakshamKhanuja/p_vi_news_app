package com.project.news_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.project.news_app.R;
import com.project.news_app.constants.MainActivityConstants;
import com.project.news_app.databinding.ActivityMainBinding;
import com.project.news_app.fragments.PodcastFragment;
import com.project.news_app.fragments.CategoryFragment;
import com.project.news_app.fragments.HomeFragment;
import com.project.news_app.utils.CommonUtils;

/**
 * Stage VIII
 * <br/>
 * App uses {@link ConnectivityManager.NetworkCallback} to handle changes in network availability.
 * These changes are notified to the user via {@link Snackbar}.
 * <br/>
 * App also uses {@link SwipeRefreshLayout} to allow user's to swipe down to refresh
 * feed/news/episode items.
 */
public class MainActivity extends AppCompatActivity implements MainActivityConstants {
    // Adds, Removes and Replaces Fragments.
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    // Sets Content view.
    private ActivityMainBinding binding;

    /**
     * Stores the TAG of visible/active Fragment.
     */
    private String currentFragmentTag;

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
        setContentView(R.layout.activity_main);

        // Setting Content View.
        binding = ActivityMainBinding.inflate((LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        // Initializing ConnectivityManager.
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Checks if app is NOT connected/connecting to internet.
        if (!(CommonUtils.checkNetworkAvailability(connectivityManager))) {
            // Shows "Connection Unavailable".
            CommonUtils.showNetworkUnavailable(this, binding.homeCoordinatorLayout);
        }

        // Initializing NetworkCallback.
        networkCallback = CommonUtils.getNetworkCallback(this, binding.homeCoordinatorLayout,
                getLifecycle());

        // Registering NetworkCallback.
        connectivityManager.registerDefaultNetworkCallback(networkCallback);

        /*
         * Attaches OnItemSelectedListener to the BottomNavigationView in order to get a callback
         * when the user tries to select any category.
         */
        binding.bottomNav.setOnItemSelectedListener(bottomNavItemSelectedListener);

        // Following operations are performed when MainActivity is opened for the first time.
        if (savedInstanceState == null) {
            /*
             * Initializing and adding PodcastFragment to MainActivity which is then
             * simultaneously hidden.
             */
            PodcastFragment podcastFragment = new PodcastFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, podcastFragment,
                    TAG_PODCAST).hide(podcastFragment).commit();

            /*
             * Initializing and adding CategoryFragment to MainActivity which is then
             * simultaneously hidden.
             */
            CategoryFragment categoryFragment = new CategoryFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, categoryFragment,
                    TAG_CATEGORY).hide(categoryFragment).commit();

            // Initializing and adding HomeFragment to MainActivity.
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment,
                    TAG_HEADLINE).commit();

            // Sets HomeFragment to be active by default.
            currentFragmentTag = TAG_HEADLINE;
        } else {
            // Restoring what Fragment is currently active.
            currentFragmentTag = savedInstanceState.getString(KEY_TAG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregistering NetworkCallback.
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    /**
     * Shows a Fragment among the already added to the FragmentManager.
     *
     * @param tag    Tag name of the Fragment required to retrieve.
     * @param active Visible/Active Fragment.
     */
    private void showFragment(String tag, Fragment active) {
        // Get fragment by tag.
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        // Hides the current/active Fragment and shows the "fragment".
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .hide(active)
                    .show(fragment)
                    .commit();

            // Updating with the new active Fragment tag.
            currentFragmentTag = tag;
        }
    }

    @Override
    public void onBackPressed() {
        // Checks the current/active Fragment to be "HeadlineFragment".
        if (currentFragmentTag.equals(TAG_HEADLINE)) {
            // Exit the App.
            super.onBackPressed();
        } else {
            // Select the "Headlines" category in BottomNavigationView.
            binding.bottomNav.setSelectedItemId(R.id.bottom_home);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up the currently viewed Fragment.
        outState.putString(KEY_TAG, currentFragmentTag);
    }

    /**
     * Listener is attached to {@link R.id#bottom_nav} BottomNavigationView. Shows/hides the
     * Fragments that are added to the FragmentManager.
     */
    private final NavigationBarView.OnItemSelectedListener bottomNavItemSelectedListener = item -> {
        // Contains the ID of the selected MenuItem.
        int selectedItemId = item.getItemId();

        // Get the current/active Fragment.
        Fragment active = fragmentManager.findFragmentByTag(currentFragmentTag);

        if (active != null) {
            // Checks if the user selected "Home".
            if (selectedItemId == R.id.bottom_home) {
                showFragment(TAG_HEADLINE, active);
                return true;
            }

            // Checks if the user selected "Categories".
            else if (selectedItemId == R.id.bottom_categories) {
                showFragment(TAG_CATEGORY, active);
                return true;
            }

            // Checks if the user selected "Podcast".
            else if (selectedItemId == R.id.bottom_podcast) {
                showFragment(TAG_PODCAST, active);
                return true;
            }
        }
        return false;
    };
}