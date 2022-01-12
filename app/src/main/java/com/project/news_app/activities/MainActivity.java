package com.project.news_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.google.android.material.navigation.NavigationBarView;
import com.project.news_app.R;
import com.project.news_app.databinding.ActivityMainBinding;
import com.project.news_app.fragments.CategoryFragment;
import com.project.news_app.fragments.HeadlineFragment;
import com.project.news_app.fragments.PodcastFragment;

/**
 * Revamping UI and all Fragments.
 */
public class MainActivity extends AppCompatActivity {

    // Adds, Removes and Replaces Fragments.
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    /**
     * Listener is attached to {@link R.id#bottom_nav} BottomNavigationView.
     */
    private final NavigationBarView.OnItemSelectedListener bottomNavItemSelectedListener = item -> {
        // Contains the ID of the selected MenuItem.
        int selectedItemId = item.getItemId();

        // Shows the user selected Fragment.
        if (selectedItemId == R.id.bottom_categories) {
            // Replacing the currently viewed Fragment with CategoryFragment.
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new CategoryFragment())
                    .commit();
        } else if (selectedItemId == R.id.bottom_headlines) {
            // Replacing the currently viewed Fragment with HeadlineFragment.
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new HeadlineFragment())
                    .commit();
        } else if (selectedItemId == R.id.bottom_podcast) {
            // Replacing the currently viewed Fragment with PodcastFragment.
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new PodcastFragment())
                    .commit();
        }

        // Displays the selected item.
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets Content View.
        ActivityMainBinding binding = ActivityMainBinding.inflate((LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        /*
         * Attaches OnItemSelectedListener to the BottomNavigationView in order to get a callback
         * when the user tries to select any category.
         */
        binding.bottomNav.setOnItemSelectedListener(bottomNavItemSelectedListener);

        // Following operations are performed when MainActivity is opened for the first time.
        if (savedInstanceState == null) {
            // Shows headlines by default.
            binding.bottomNav.setSelectedItemId(R.id.bottom_headlines);
        }
    }
}