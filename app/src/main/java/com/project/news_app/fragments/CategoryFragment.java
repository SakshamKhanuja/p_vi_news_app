package com.project.news_app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.project.news_app.adapters.CategoryAdapter;
import com.project.news_app.R;
import com.project.news_app.data.Category;
import com.project.news_app.activities.MainActivity;
import com.project.news_app.utils.CommonUtils;

import java.util.ArrayList;

/**
 * {@link RecyclerView} displays a list of {@link Category} in a VERTICAL orientation.
 */
public class CategoryFragment extends Fragment {
    /**
     * Stores all news category titles.
     */
    private ArrayList<Category> newsCategories;

    /**
     * Used to access all news category titles across orientation changes.
     */
    private static final String KEY_CATEGORY = "category";

    /**
     * Sets context when this fragment is attached to {@link MainActivity}.
     */
    private Context context;

    // Required Default Constructor.
    public CategoryFragment() {
        // Providing a layout to inflate.
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
            // Restoring List containing news category titles.
            newsCategories = savedInstanceState.getParcelableArrayList(KEY_CATEGORY);
        } else {
            // Initializing list containing all news categories.
            newsCategories = getNewsCategories();
        }

        // Hide ProgressBar.
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        // Disable SwipeRefreshLayout.
        SwipeRefreshLayout layout = view.findViewById(R.id.swipe_to_refresh);
        layout.setEnabled(false);

        // Setting title.
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bottom_categories);

        // Hide Logo.
        TextView textLogo = view.findViewById(R.id.text_logo);
        textLogo.setVisibility(View.GONE);

        // Initializing RecyclerView.
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_dark);

        // Setting up RecyclerView.
        CommonUtils.setupRecyclerView(context, recyclerView, new CategoryAdapter(newsCategories),
                LinearLayoutManager.VERTICAL);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Backing up List containing news category titles.
        outState.putParcelableArrayList(KEY_CATEGORY, newsCategories);
    }

    /**
     * @return All available news categories in "The Guardian" API.
     */
    private ArrayList<Category> getNewsCategories() {
        ArrayList<Category> categories = new ArrayList<>();

        // Adding Section - News.
        categories.add(new Category(R.string.title_news));

        // Adding News - Coronavirus.
        categories.add(new Category(R.string.category_title_coronavirus,
                R.string.category_path_coronavirus));

        // Adding News - Climate Crisis.
        categories.add(new Category(R.string.category_title_climate_crisis,
                R.string.category_path_climate_crisis));

        // Adding News - Environment.
        categories.add(new Category(R.string.category_title_environment,
                R.string.category_path_environment));

        // Adding News - Science.
        categories.add(new Category(R.string.category_title_science,
                R.string.category_path_science));

        // Adding News - Global Development.
        categories.add(new Category(R.string.category_title_global_development,
                R.string.category_path_global_development));

        // Adding News - Tech.
        categories.add(new Category(R.string.category_title_technology,
                R.string.category_path_technology));

        // Adding News - Business.
        categories.add(new Category(R.string.category_title_business,
                R.string.category_path_business));

        // Adding News - Obituaries.
        categories.add(new Category(R.string.category_title_obituaries,
                R.string.category_path_obituaries));

        // Adding Section - Sports.
        categories.add(new Category(R.string.title_sport));

        // Adding Sport - Football.
        categories.add(new Category(R.string.category_title_football,
                R.string.category_path_football));

        // Adding Sport - Cricket.
        categories.add(new Category(R.string.category_title_cricket,
                R.string.category_path_cricket));

        // Adding Sport - Rugby union.
        categories.add(new Category(R.string.category_title_rugby, R.string.category_path_rugby));

        // Adding Sport - Tennis.
        categories.add(new Category(R.string.category_title_tennis, R.string.category_path_tennis));

        // Adding Sport - Cycling.
        categories.add(new Category(R.string.category_title_cycling,
                R.string.category_path_cycling));

        // Adding Sport - F1.
        categories.add(new Category(R.string.category_title_f1, R.string.category_path_f1));

        // Adding Sport - Golf.
        categories.add(new Category(R.string.category_title_golf, R.string.category_path_golf));

        // Adding Sport - US Sports.
        categories.add(new Category(R.string.category_title_us_sports,
                R.string.category_path_us_sports));

        // Adding Section - Culture.
        categories.add(new Category(R.string.title_culture));

        // Adding Culture - Books.
        categories.add(new Category(R.string.category_title_books, R.string.category_path_books));

        // Adding Culture - Music.
        categories.add(new Category(R.string.category_title_music,
                R.string.category_path_music));

        // Adding Culture - Art and Design.
        categories.add(new Category(R.string.category_title_art_and_design,
                R.string.category_path_art_and_design));

        // Adding Culture - Film.
        categories.add(new Category(R.string.category_title_film, R.string.category_path_film));

        // Adding Culture - Games.
        categories.add(new Category(R.string.category_title_games, R.string.category_path_games));

        // Adding Culture - Classical.
        categories.add(new Category(R.string.category_title_classical_music_and_opera,
                R.string.category_path_classical_music_and_opera));

        // Adding Culture - Stage.
        categories.add(new Category(R.string.category_title_stage, R.string.category_path_stage));

        // Adding Section - Lifestyle.
        categories.add(new Category(R.string.title_lifestyle));

        // Adding Lifestyle - Fashion.
        categories.add(new Category(R.string.category_title_fashion,
                R.string.category_path_fashion));

        // Adding Lifestyle - Culture.
        categories.add(new Category(R.string.category_title_culture,
                R.string.category_path_culture));

        // Adding Lifestyle - Education.
        categories.add(new Category(R.string.category_title_education,
                R.string.category_path_education));

        // Adding Lifestyle - Food.
        categories.add(new Category(R.string.category_title_food, R.string.category_path_food));

        // Adding Lifestyle - Recipes.
        categories.add(new Category(R.string.category_title_recipes,
                R.string.category_path_recipes));

        // Adding Lifestyle - Health and Fitness.
        categories.add(new Category(R.string.category_title_health_and_fitness,
                R.string.category_path_health_and_fitness));

        // Adding Lifestyle - Women.
        categories.add(new Category(R.string.category_title_women, R.string.category_path_women));

        // Adding Lifestyle - Men.
        categories.add(new Category(R.string.category_title_men, R.string.category_path_men));

        // Adding Lifestyle - Family.
        categories.add(new Category(R.string.category_title_family, R.string.category_path_family));

        // Adding Lifestyle - Travel.
        categories.add(new Category(R.string.category_title_travel, R.string.category_path_travel));

        // Adding Lifestyle - Money.
        categories.add(new Category(R.string.category_title_money, R.string.category_path_money));

        // Adding Section - More.
        categories.add(new Category(R.string.title_more));

        // Adding More - Opinion.
        categories.add(new Category(R.string.category_title_opinion,
                R.string.category_path_opinion));

        // Adding More - Politics.
        categories.add(new Category(R.string.category_title_politics,
                R.string.category_path_politics));

        // Adding More - Society.
        categories.add(new Category(R.string.category_title_society,
                R.string.category_path_society));

        return categories;
    }
}