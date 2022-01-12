package com.project.news_app.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.project.news_app.adapters.CategoryAdapter;
import com.project.news_app.fragments.CategoryFragment;

/**
 * Provides title and path of news categories to {@link CategoryFragment} Fragment.
 */
public class Category implements Parcelable {
    /*
     * Stores the String resource ID containing the name of category acc. to "The Guardian"
     * homepage.
     */
    private final int title;

    // Stores the String resource ID containing the path to access feed from "The Guardian" API.
    private int path;

    // Stores the view type in which the category title is shown.
    private final int viewType;

    public Category(int title) {
        this.title = title;
        viewType = CategoryAdapter.CATEGORY_SECTION;
    }

    public Category(int title, int path) {
        this.title = title;
        this.path = path;
        viewType = CategoryAdapter.CATEGORY_TITLE;
    }

    protected Category(Parcel in) {
        title = in.readInt();
        path = in.readInt();
        viewType = in.readInt();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    /**
     * @return The tile of this Category.
     */
    public int getTitle() {
        return title;
    }

    /**
     * @return The path of this Category.
     */
    public int getPath() {
        return path;
    }

    /**
     * @return The view type of this Category.
     */
    public int getViewType() {
        return viewType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(title);
        dest.writeInt(path);
        dest.writeInt(viewType);
    }
}