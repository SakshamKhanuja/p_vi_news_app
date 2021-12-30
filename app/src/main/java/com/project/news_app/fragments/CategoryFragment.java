package com.project.news_app.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.CategoryAdapter;
import com.project.news_app.R;
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

    // Shows the clicked news category Uri in RecyclerView.
    private Toast mToast;

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
            mNewsCategories.add(new NewsCategory("Art and Design", "artanddesign"));
            mNewsCategories.add(new NewsCategory("Business", "business"));
            mNewsCategories.add(new NewsCategory("Cars", "cars"));
            mNewsCategories.add(new NewsCategory("Culture", "culture"));
            mNewsCategories.add(new NewsCategory("Economy", "economy"));
            mNewsCategories.add(new NewsCategory("Education", "education"));
            mNewsCategories.add(new NewsCategory("Entertainment", "entertainment"));
            mNewsCategories.add(new NewsCategory("Environment", "environment"));
            mNewsCategories.add(new NewsCategory("Fashion", "fashion"));
            mNewsCategories.add(new NewsCategory("Health", "health"));
            mNewsCategories.add(new NewsCategory("Jobs", "jobs"));
            mNewsCategories.add(new NewsCategory("Lifestyle", "lifestyle"));
            mNewsCategories.add(new NewsCategory("Money", "money"));
            mNewsCategories.add(new NewsCategory("Opinion", "opinion"));
            mNewsCategories.add(new NewsCategory("Politics", "politics"));
            mNewsCategories.add(new NewsCategory("Science", "science"));
            mNewsCategories.add(new NewsCategory("Society", "society"));
            mNewsCategories.add(new NewsCategory("Sport", "sport"));
            mNewsCategories.add(new NewsCategory("Technology", "technology"));
            mNewsCategories.add(new NewsCategory("Tourism", "tourism"));
            mNewsCategories.add(new NewsCategory("World", "world"));
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
    public void onNewsCategoryClick(URL sectionUrl) {
        // Cancels the Toasts if showing.
        if (mToast != null) {
            mToast.cancel();
        }

        // Shows the clicked news title's Uri.
        mToast = Toast.makeText(getContext(), sectionUrl.toString(), Toast.LENGTH_SHORT);
        mToast.show();
    }
}