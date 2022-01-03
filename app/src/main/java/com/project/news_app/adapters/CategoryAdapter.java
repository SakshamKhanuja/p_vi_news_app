package com.project.news_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.R;
import com.project.news_app.data.NewsCategory;
import com.project.news_app.utils.NetworkUtils;
import com.project.news_app.activities.CategoryActivity;

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
     * Provides click functionality to open up clicked news category in
     * {@link CategoryActivity} Activity.
     */
    private final CategoryNewsClickListener categoryNewsClickListener;

    // Provides click functionality to News category items.
    public interface CategoryNewsClickListener {
        /**
         * Opens {@link CategoryActivity} Activity to show the clicked news category info.
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
        // Get the category at specified position.
        NewsCategory currentCategory = newsCategory.get(position);

        // Binding data to item views.
        holder.setNewsCategory(currentCategory);
        holder.setNewsTitle(currentCategory);
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
     * Hence, when user clicks on any item, {@link CategoryActivity} Activity is opened and the
     * news of the clicked news category is loaded.
     */
    protected class CategoryViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        // Shows the name of the news category.
        private final TextView textCategory;

        // Shows the name of the news category title.
        private final TextView textCategoryTile;

        // Functions as a divider in between the news category.
        private final View viewGap;

        // Labels the news category.
        private final View viewCategoryBar;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            // Initialize Views from "category_item" layout.
            textCategory = itemView.findViewById(R.id.text_category);
            textCategoryTile = itemView.findViewById(R.id.text_category_title);
            viewGap = itemView.findViewById(R.id.category_gap);
            viewCategoryBar = itemView.findViewById(R.id.view_category_color);

            // Attach OnClickListener to news category.
            textCategory.setOnClickListener(this);
        }

        /**
         * Sets the news category title to {@link R.id#text_category_title} TextView.
         *
         * @param category Contains all info. of the news category.
         */
        public void setNewsCategory(NewsCategory category) {
            // Checks if News title is first of its category.
            if (category.isNewsTitleFirstOfItsCategory()) {
                // Sets the category color to Bar view.
                viewCategoryBar.setBackgroundColor(ContextCompat.getColor(mContext,
                        category.getCategoryColor()));

                // Sets the category title.
                String titleCategory = mContext.getString(category.getCategory());
                textCategoryTile.setText(titleCategory);

                // Shows the category bar containing category color.
                viewCategoryBar.setVisibility(View.VISIBLE);

                // Shows the category title.
                textCategoryTile.setVisibility(View.VISIBLE);

                // Shows the divider in between news categories other than the "News" category.
                if (titleCategory.equals("News")) {
                    viewGap.setVisibility(View.GONE);
                } else {
                    viewGap.setVisibility(View.VISIBLE);
                }
            } else {
                // Hides the category bar.
                viewCategoryBar.setVisibility(View.GONE);

                // Hides the category divider.
                viewGap.setVisibility(View.GONE);

                // Hides the category title.
                textCategoryTile.setVisibility(View.GONE);
            }
        }

        /**
         * Sets the news category title to {@link R.id#text_category} TextView.
         *
         * @param category Contains title of the news category.
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