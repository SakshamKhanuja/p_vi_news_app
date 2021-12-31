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

    public NewsCategory(int title, int section) {
        this.title = title;
        this.section = section;
    }

    protected NewsCategory(Parcel in) {
        title = in.readInt();
        section = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(title);
        dest.writeInt(section);
    }
}