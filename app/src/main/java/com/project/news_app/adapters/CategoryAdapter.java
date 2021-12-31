package com.project.news_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.R;
import com.project.news_app.data.NewsCategory;
import com.project.news_app.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Provides {@link CategoryViewHolder} ViewHolder inflated from {@link R.layout#category_item}
 * item view layout to {@link R.id#recycler_view} RecyclerView.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    // Stores news categories info.
    private final ArrayList<NewsCategory> newsCategory;

    // Used for accessing String resources.
    private Context mContext;

    /**
     * TODO: Add name of the Activity that loads up news info from CategoryFragment.
     * Provides click functionality to open up clicked news category in {} Activity.
     */
    private final CategoryNewsClickListener categoryNewsClickListener;

    // Provides click functionality to News category items.
    public interface CategoryNewsClickListener {
        /**
         * TODO: Add name of the Activity that loads up news info from CategoryFragment.
         * Opens {} Activity to show the clicked news category info.
         *
         * @param sectionUrl    Contains a URL that points to the clicked "The Guardian" Section
         *                      API Endpoint.
         * @param categoryTitle Clicked news category name.
         */
        void onNewsCategoryClick(URL sectionUrl, String categoryTitle);
    }

    /**
     * Initializes Adapter providing {@link CategoryViewHolder} ViewHolder to the linked
     * RecyclerView.
     *
     * @param newsCategory              Contains all news category info.
     * @param categoryNewsClickListener Provides click functionality for items in the adapter.
     */
    public CategoryAdapter(ArrayList<NewsCategory> newsCategory,
                           CategoryNewsClickListener categoryNewsClickListener) {
        this.newsCategory = newsCategory;
        this.categoryNewsClickListener = categoryNewsClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Setting Context.
        mContext = parent.getContext();

        // Initializing LayoutInflater to inflate "category_item" layout.
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        // Initialize ViewHolder provided to linked RecyclerView.
        return new CategoryViewHolder(layoutInflater.inflate(R.layout.category_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // Binding data to item views.
        holder.setNewsTitle(newsCategory.get(position));
    }

    @Override
    public int getItemCount() {
        // Total number of items in this Adapter.
        return newsCategory.size();
    }

    /**
     * Class describes {@link R.layout#category_item} item view and is responsible for caching
     * views. It also provides click functionality to the RecyclerView holding news categories.
     * <p>
     * TODO: Add name of the Activity that loads up news info from CategoryFragment.
     * Hence, when user clicks on any item, {} Activity is opened and the news of the clicked
     * news category is loaded.
     */
    protected class CategoryViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        // Stores the name of the news category.
        private final TextView textCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            // Initialize textCategory from "itemView".
            textCategory = itemView.findViewById(R.id.text_category);

            // Attach OnClickListener to itemView.
            itemView.setOnClickListener(this);
        }

        /**
         * Sets the news category title to {@link R.id#text_category} TextView.
         *
         * @param category News title at current position in RecyclerView.
         */
        public void setNewsTitle(NewsCategory category) {
            textCategory.setText(mContext.getString(category.getTitle()));
        }

        @Override
        public void onClick(View v) {
            // Get item view's Context.
            Context context = v.getContext();

            // Getting title of the clicked news category.
            NewsCategory category = newsCategory.get(getAdapterPosition());

            // Forming a Uri that points to "The Guardian" Section API endpoint.
            URL clickedNewsUrl = NetworkUtils.makeURL(v.getContext(), context.getString(
                    category.getSection()));

            // Set Uri and Title to CategoryNewsClickListener.
            categoryNewsClickListener.onNewsCategoryClick(clickedNewsUrl, context.getString(
                    category.getTitle()));
        }
    }
}