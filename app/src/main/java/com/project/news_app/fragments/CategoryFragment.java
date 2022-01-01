package com.project.news_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
            // Initializing list containing all news categories.
            mNewsCategories = getNewsCategories();
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

    /**
     * Creates and returns an ArrayList of type {@link NewsCategory} containing all news categories.
     */
    private ArrayList<NewsCategory> getNewsCategories() {
        ArrayList<NewsCategory> categories = new ArrayList<>();

        // Adding News.

        // Adding News - World News.
        categories.add(new NewsCategory(R.string.category_title_world_news,
                R.string.category_path_world_news, R.string.title_news,
                R.color.colorNewsCategoryNews));

        // Adding News - UK News.
        categories.add(new NewsCategory(R.string.category_title_uk_news,
                R.string.category_path_uk_news));

        // Adding News - Coronavirus.
        categories.add(new NewsCategory(R.string.category_title_coronavirus,
                R.string.category_path_coronavirus));

        // Adding News - Climate Crisis.
        categories.add(new NewsCategory(R.string.category_title_climate_crisis,
                R.string.category_path_climate_crisis));

        // Adding News - Environment.
        categories.add(new NewsCategory(R.string.category_title_environment,
                R.string.category_path_environment));

        // Adding News - Science.
        categories.add(new NewsCategory(R.string.category_title_science,
                R.string.category_path_science));

        // Adding News - Global Development.
        categories.add(new NewsCategory(R.string.category_title_global_development,
                R.string.category_path_global_development));

        // Adding News - Tech.
        categories.add(new NewsCategory(R.string.category_title_technology,
                R.string.category_path_technology));

        // Adding News - Business.
        categories.add(new NewsCategory(R.string.category_title_business,
                R.string.category_path_business));

        // Adding News - Obituaries.
        categories.add(new NewsCategory(R.string.category_title_obituaries,
                R.string.category_path_obituaries));

        // Adding Opinion.

        // Adding Opinion - The Guardian View.
        categories.add(new NewsCategory(R.string.category_title_editorial,
                R.string.category_path_editorial, R.string.title_opinion,
                R.color.colorNewsCategoryOpinion));

        // Adding Opinion - Cartoons.
        categories.add(new NewsCategory(R.string.category_title_cartoons,
                R.string.category_path_cartoons));

        // Adding Opinion - Letters.
        categories.add(new NewsCategory(R.string.category_title_letters,
                R.string.category_path_letters));

        // Adding Sport.

        // Adding Sport - Football.
        categories.add(new NewsCategory(R.string.category_title_football,
                R.string.category_path_football, R.string.title_sport,
                R.color.colorNewsCategorySport));

        // Adding Sport - Cricket.
        categories.add(new NewsCategory(R.string.category_title_cricket,
                R.string.category_path_cricket));

        // Adding Sport - Rugby union.
        categories.add(new NewsCategory(R.string.category_title_rugby,
                R.string.category_path_rugby));

        // Adding Sport - Tennis.
        categories.add(new NewsCategory(R.string.category_title_tennis,
                R.string.category_path_tennis));

        // Adding Sport - Cycling.
        categories.add(new NewsCategory(R.string.category_title_cycling,
                R.string.category_path_cycling));

        // Adding Sport - F1.
        categories.add(new NewsCategory(R.string.category_title_f1,
                R.string.category_path_f1));

        // Adding Sport - Golf.
        categories.add(new NewsCategory(R.string.category_title_golf,
                R.string.category_path_golf));

        // Adding Sport - US Sports.
        categories.add(new NewsCategory(R.string.category_title_us_sports,
                R.string.category_path_us_sports));

        // Adding Culture.

        // Adding Culture - Books.
        categories.add(new NewsCategory(R.string.category_title_books, R.string.category_path_books,
                R.string.title_culture, R.color.colorNewsCategoryCulture));

        // Adding Culture - Music.
        categories.add(new NewsCategory(R.string.category_title_Music,
                R.string.category_path_music));

        // Adding Culture - Art and Design.
        categories.add(new NewsCategory(R.string.category_title_art_and_design,
                R.string.category_path_art_and_design));

        // Adding Culture - Film.
        categories.add(new NewsCategory(R.string.category_title_film, R.string.category_path_film));

        // Adding Culture - Games.
        categories.add(new NewsCategory(R.string.category_title_games,
                R.string.category_path_games));

        // Adding Culture - Classical
        categories.add(new NewsCategory(R.string.category_title_classical_music_and_opera,
                R.string.category_path_classical_music_and_opera));

        // Adding Culture - Stage.
        categories.add(new NewsCategory(R.string.category_title_stage,
                R.string.category_path_stage));

        // Adding Lifestyle.

        // Adding Lifestyle - Fashion.
        categories.add(new NewsCategory(R.string.category_title_fashion,
                R.string.category_path_fashion, R.string.title_lifestyle,
                R.color.colorNewsCategoryLifestyle));

        // Adding Lifestyle - Culture.
        categories.add(new NewsCategory(R.string.category_title_culture,
                R.string.category_path_culture));

        // Adding Lifestyle - Education.
        categories.add(new NewsCategory(R.string.category_title_education,
                R.string.category_path_education));

        // Adding Lifestyle - Food.
        categories.add(new NewsCategory(R.string.category_title_food, R.string.category_path_food));

        // Adding Lifestyle - Recipes.
        categories.add(new NewsCategory(R.string.category_title_recipes,
                R.string.category_path_recipes));

        // Adding Lifestyle - Health and Fitness.
        categories.add(new NewsCategory(R.string.category_title_health_and_fitness,
                R.string.category_path_health_and_fitness));

        // Adding Lifestyle - Women.
        categories.add(new NewsCategory(R.string.category_title_women,
                R.string.category_path_women));

        // Adding Lifestyle - Men.
        categories.add(new NewsCategory(R.string.category_title_men, R.string.category_path_men));

        // Adding Lifestyle - Family.
        categories.add(new NewsCategory(R.string.category_title_family,
                R.string.category_path_family));

        // Adding Lifestyle - Travel.
        categories.add(new NewsCategory(R.string.category_title_travel,
                R.string.category_path_travel));

        // Adding Lifestyle - Money.
        categories.add(new NewsCategory(R.string.category_title_money,
                R.string.category_path_money));

        // Adding More.

        // Adding More - Podcasts.
        categories.add(new NewsCategory(R.string.category_title_podcasts,
                R.string.category_path_podcasts, R.string.title_more, R.color.colorBottomInactive));

        // Adding More - Pictures.
        categories.add(new NewsCategory(R.string.category_title_pictures,
                R.string.category_path_pictures));

        // Adding More - Opinion.
        categories.add(new NewsCategory(R.string.category_title_opinion,
                R.string.category_path_opinion));

        // Adding More - Politics.
        categories.add(new NewsCategory(R.string.category_title_politics,
                R.string.category_path_politics));

        // Adding More - Society.
        categories.add(new NewsCategory(R.string.category_title_society,
                R.string.category_path_society));

        return categories;
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