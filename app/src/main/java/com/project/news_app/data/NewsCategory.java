package com.project.news_app.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.project.news_app.fragments.CategoryFragment;

/**
 * Provides names and section names of news categories to {@link CategoryFragment} Fragment.
 */
public class NewsCategory implements Parcelable {
    // Stores the name of the news category.
    private final String title;

    // Stores the section name of the news category per "The Guardian" api.
    private final String section;

    public NewsCategory(String title, String section) {
        this.title = title;
        this.section = section;
    }

    protected NewsCategory(Parcel in) {
        title = in.readString();
        section = in.readString();
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
     * @return The tile of news category.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The section name of news category.
     */
    public String getSection() {
        return section;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(section);
    }
}