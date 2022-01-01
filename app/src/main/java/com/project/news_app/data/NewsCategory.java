package com.project.news_app.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.project.news_app.fragments.CategoryFragment;

/**
 * Provides names and section names of news categories to {@link CategoryFragment} Fragment.
 */
public class NewsCategory implements Parcelable {
    // Stores the String resource ID containing name of the news category.
    private final int title;

    /*
     * Stores the String resource ID containing section name of the news category per
     * "The Guardian" api.
     */
    private final int section;

    // Stores the String resource ID containing the category name in which the "title" belongs.
    private int category;

    // Stores the Color resource ID of the category in which the "title" belongs.
    private int categoryColor;

    // Notifies whether this NewsCategory is first of its "category".
    private boolean lastOfItsCategory;

    public NewsCategory(int title, int section) {
        this.title = title;
        this.section = section;
    }

    public NewsCategory(int title, int section, int category, int categoryColor) {
        this(title, section);
        this.category = category;
        this.categoryColor = categoryColor;
        lastOfItsCategory = true;
    }

    protected NewsCategory(Parcel in) {
        title = in.readInt();
        section = in.readInt();
        category = in.readInt();
        categoryColor = in.readInt();
        lastOfItsCategory = in.readByte() != 0;
    }

    public static final Creator<NewsCategory> CREATOR = new Creator<NewsCategory>() {
        @Override
        public NewsCategory createFromParcel(Parcel in) {
            return new NewsCategory(in);
        }

        @Override
        public NewsCategory[] newArray(int size) {
            return new NewsCategory[size];
        }
    };

    /**
     * @return String resource ID containing tile of the news category.
     */
    public int getTitle() {
        return title;
    }

    /**
     * @return String resource ID containing section name of the news category.
     */
    public int getSection() {
        return section;
    }

    /**
     * @return String resource ID containing category name under which this news title belongs.
     */
    public int getCategory() {
        return category;
    }

    /**
     * @return 'true', if news "title" is first of its category.
     */
    public boolean isNewsTitleFirstOfItsCategory() {
        return lastOfItsCategory;
    }

    /**
     * @return Color resource ID of the category under which this "title" belongs.
     */
    public int getCategoryColor() {
        return categoryColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(title);
        dest.writeInt(section);
        dest.writeInt(category);
        dest.writeInt(categoryColor);
        dest.writeByte((byte) (lastOfItsCategory ? 1 : 0));
    }
}