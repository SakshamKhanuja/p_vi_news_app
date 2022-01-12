package com.project.news_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.news_app.activities.CategoryActivity;
import com.project.news_app.data.Category;
import com.project.news_app.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Stores news categories info.
    private final ArrayList<Category> categories;

    // Used for accessing String resources.
    private Context context;

    /**
     * View type inflated from {@link R.layout#category_title} layout.
     */
    public static final int CATEGORY_TITLE = 1;

    /**
     * View type inflated from {@link R.layout#category_section} layout.
     */
    public static final int CATEGORY_SECTION = 2;

    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sets context.
        context = parent.getContext();

        // Inflates views from layout.
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (viewType) {
            case CATEGORY_SECTION:
                // Shows the section under which the news title belongs.
                return new CategorySectionHolder(layoutInflater.inflate(R.layout.category_section,
                        parent, false));

            case CATEGORY_TITLE:
            default:
                // Shows the news title.
                return new CategoryTitleHolder(layoutInflater.inflate(R.layout.category_title,
                        parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case CATEGORY_TITLE:
                ((CategoryTitleHolder) holder).setData(categories.get(position));
                break;

            case CATEGORY_SECTION:
                ((CategorySectionHolder) holder).setData(categories.get(position), position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        // Number of news categories.
        return categories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return categories.get(position).getViewType();
    }

    /**
     * Sets news category info.
     */
    private void setText(TextView textView, String data) {
        textView.setText(data);
    }

    /**
     * ViewHolder shows the title of the news category.
     * <br/>
     * Layout resource - {@link R.layout#category_title}.
     */
    protected class CategoryTitleHolder extends RecyclerView.ViewHolder {
        // Shows the name of the news category.
        private final TextView title;

        public CategoryTitleHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text_title);

            // Attaching OnClickListener to entire iteView.
            itemView.setOnClickListener(view -> {
                // Get the clicked category.
                Category category = categories.get(getAdapterPosition());

                // Explicit Intent opens up CategoryActivity to show News feed.
                Intent explicit = new Intent(context, CategoryActivity.class);

                // Passing the path where the clicked category can be accessed in "The Guardian" API.
                explicit.putExtra(CategoryActivity.EXTRA_PATH, context.getString(
                        category.getPath()));

                // Passing the clicked news category's title.
                explicit.putExtra(CategoryActivity.EXTRA_TITLE, context.getString(
                        category.getTitle()));
                context.startActivity(explicit);
            });
        }

        /**
         * Sets news category title.
         */
        public void setData(Category category) {
            setText(title, context.getString(category.getTitle()));
        }
    }

    /**
     * ViewHolder shows the section name under which the news title belongs.
     * <br/>
     * Layout resource - {@link R.layout#category_section}.
     */
    protected class CategorySectionHolder extends RecyclerView.ViewHolder {
        // Shows the name of the section under which news categories belong.
        private final TextView section;

        // Shows a blank space that represents end of section.
        private final View divider;

        public CategorySectionHolder(View itemView) {
            super(itemView);

            section = itemView.findViewById(R.id.text_section);
            divider = itemView.findViewById(R.id.section_divider);
        }

        /**
         * Sets news section.
         */
        public void setData(Category category, int position) {
            if (position == 0) {
                divider.setVisibility(View.GONE);
            } else {
                divider.setVisibility(View.VISIBLE);
            }
            setText(section, context.getString(category.getTitle()));
        }
    }
}