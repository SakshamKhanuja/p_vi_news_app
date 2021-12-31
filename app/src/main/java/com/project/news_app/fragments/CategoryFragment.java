package com.project.news_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.adapters.CategoryAdapter;
import com.project.news_app.R;
import com.project.news_app.activities.CategoryActivity;
import com.project.news_app.data.NewsCategory;

import java.net.URL;
import java.util.ArrayList;

/**
 * Shows a list of available news categories.
 */
public class CategoryFragment extends Fragment implements
        CategoryAdapter.CategoryNewsClickListener {

    // Stores all news category titles.
    private ArrayList<NewsCategory> mNewsCategories;

    // Used to access all news category titles across orientation changes.
    private static final String KEY_CATEGORY = "category";

    // Required Default Constructor.
    public CategoryFragment() {
        // Providing a layout to inflate.
        super(R.layout.basic_recycler_view_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            // Restoring List containing news category titles.
            mNewsCategories = savedInstanceState.getParcelableArrayList(KEY_CATEGORY);
        } else {
            mNewsCategories = new ArrayList<>();
            // Adding news category title.
            mNewsCategories.add(new NewsCategory(R.string.category_title_art_and_design, R.string.category_path_art_and_design));
            mNewsCategories.add(new NewsCategory(R.string.category_title_books, R.string.category_path_books));
            mNewsCategories.add(new NewsCategory(R.string.category_title_business, R.string.category_path_business));
            mNewsCategories.add(new NewsCategory(R.string.category_title_cartoons, R.string.category_path_cartoons));
            mNewsCategories.add(new NewsCategory(R.string.category_title_climate_crisis, R.string.category_path_climate_crisis));
            mNewsCategories.add(new NewsCategory(R.string.category_title_coronavirus, R.string.category_path_coronavirus));
            mNewsCategories.add(new NewsCategory(R.string.category_title_cricket, R.string.category_path_cricket));
            mNewsCategories.add(new NewsCategory(R.string.category_title_culture, R.string.category_path_culture));
            mNewsCategories.add(new NewsCategory(R.string.category_title_cycling, R.string.category_path_cycling));
            mNewsCategories.add(new NewsCategory(R.string.category_title_editorial, R.string.category_path_editorial));
            mNewsCategories.add(new NewsCategory(R.string.category_title_education, R.string.category_path_education));
            mNewsCategories.add(new NewsCategory(R.string.category_title_environment, R.string.category_path_environment));
            mNewsCategories.add(new NewsCategory(R.string.category_title_f1, R.string.category_path_f1));
            mNewsCategories.add(new NewsCategory(R.string.category_title_family, R.string.category_path_family));
            mNewsCategories.add(new NewsCategory(R.string.category_title_fashion, R.string.category_path_fashion));
            mNewsCategories.add(new NewsCategory(R.string.category_title_film, R.string.category_path_film));
            mNewsCategories.add(new NewsCategory(R.string.category_title_food, R.string.category_path_food));
            mNewsCategories.add(new NewsCategory(R.string.category_title_football, R.string.category_path_football));
            mNewsCategories.add(new NewsCategory(R.string.category_title_games, R.string.category_path_games));
            mNewsCategories.add(new NewsCategory(R.string.category_title_global_development, R.string.category_path_global_development));
            mNewsCategories.add(new NewsCategory(R.string.category_title_golf, R.string.category_path_golf));
            mNewsCategories.add(new NewsCategory(R.string.category_title_health_and_fitness, R.string.category_path_health_and_fitness));
            mNewsCategories.add(new NewsCategory(R.string.category_title_letters, R.string.category_path_letters));
            mNewsCategories.add(new NewsCategory(R.string.category_title_men, R.string.category_path_men));
            mNewsCategories.add(new NewsCategory(R.string.category_title_money, R.string.category_path_money));
            mNewsCategories.add(new NewsCategory(R.string.category_title_Music, R.string.category_path_music));
            mNewsCategories.add(new NewsCategory(R.string.category_title_obituaries, R.string.category_path_obituaries));
            mNewsCategories.add(new NewsCategory(R.string.category_title_opinion, R.string.category_path_opinion));
            mNewsCategories.add(new NewsCategory(R.string.category_title_politics, R.string.category_path_politics));
            mNewsCategories.add(new NewsCategory(R.string.category_title_recipes, R.string.category_path_recipes));
            mNewsCategories.add(new NewsCategory(R.string.category_title_rugby, R.string.category_path_rugby));
            mNewsCategories.add(new NewsCategory(R.string.category_title_science, R.string.category_path_science));
            mNewsCategories.add(new NewsCategory(R.string.category_title_society, R.string.category_path_society));
            mNewsCategories.add(new NewsCategory(R.string.category_title_stage, R.string.category_path_stage));
            mNewsCategories.add(new NewsCategory(R.string.category_title_technology, R.string.category_path_technology));
            mNewsCategories.add(new NewsCategory(R.string.category_title_tennis, R.string.category_path_tennis));
            mNewsCategories.add(new NewsCategory(R.string.category_title_uk_news, R.string.category_path_uk_news));
            mNewsCategories.add(new NewsCategory(R.string.category_title_us_sports, R.string.category_path_us_sports));
            mNewsCategories.add(new NewsCategory(R.string.category_title_women, R.string.category_path_women));
            mNewsCategories.add(new NewsCategory(R.string.category_title_world_news, R.string.category_path_world_news));
        }

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

        // Add ItemDecoration to add dividers.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /*
         * Further optimizing RecyclerView to NOT invalidate the whole layout, if any change
         * occurs in the contents of the adapter with which it is linked.
         *
         * Fixed size is set to "true", as the contents of the list of Categories remains the same.
         */
        recyclerView.setHasFixedSize(true);

        // Linking RecyclerView to Adapter.
        recyclerView.setAdapter(new CategoryAdapter(mNewsCategories, this));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up List containing news category titles.
        outState.putParcelableArrayList(KEY_CATEGORY, mNewsCategories);
    }

    @Override
    public void onNewsCategoryClick(URL sectionUrl, String categoryTitle) {
        // Starting CategoryActivity to load up news on the clicked news category.
        Intent explicit = new Intent(getContext(), CategoryActivity.class);

        // Passing the clicked news category's section API endpoint URL in String format.
        explicit.putExtra(CategoryActivity.EXTRA_STRING_URL, sectionUrl.toString());

        // Passing the clicked news category's title.
        explicit.putExtra(CategoryActivity.EXTRA_TITLE, categoryTitle);
        startActivity(explicit);
    }
}