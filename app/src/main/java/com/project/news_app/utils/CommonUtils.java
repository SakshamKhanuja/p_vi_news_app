package com.project.news_app.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.project.news_app.activities.CategoryActivity;

/**
 * Contains methods that are used across the app.
 */
public class CommonUtils {

    // Setting constructor to private.
    private CommonUtils() {
    }

    /**
     * Setups a {@link RecyclerView}.
     *
     * @param context      Context to use.
     * @param recyclerView Shows a list of items in a horizontal or vertical orientation.
     * @param adapter      Provides contents to the RecyclerView.
     * @param orientation  Sets the orientation of the RecyclerView to be either
     *                     {@link LinearLayoutManager#HORIZONTAL} or
     *                     {@link LinearLayoutManager#VERTICAL} or vertical.
     */
    public static void setupRecyclerView(Context context, RecyclerView recyclerView,
                                         RecyclerView.Adapter<?> adapter, int orientation) {
        /*
         * Linking RecyclerView with a LayoutManager.
         *
         * LayoutManager is responsible for Recycling ITEM views when they're scrolled OFF the
         * screen. It also determines how the collection of these ITEMS are displayed.
         */
        recyclerView.setLayoutManager(new LinearLayoutManager(context, orientation,
                false));

        /*
         * Further optimizing RecyclerView to NOT invalidate the whole layout, if any change
         * occurs in the contents of the adapter with which it is linked.
         *
         * Fixed size is set to "true", as the contents of the list of Categories remains the same.
         */
        recyclerView.setHasFixedSize(true);

        // Linking Adapter to RecyclerView.
        recyclerView.setAdapter(adapter);
    }

    /**
     * Shows the clicked news category/title list in {@link CategoryActivity}.
     *
     * @param context Context to use.
     * @param path    Clicked category/title is accessed in the "sections" endpoint of
     *                "The Guardian" api.
     * @param title   Used as the title of activity.
     */
    public static void openCategoryActivity(Context context, String path, String title) {
        Intent explicit = new Intent(context, CategoryActivity.class);
        explicit.putExtra(CategoryActivity.EXTRA_PATH, path);
        explicit.putExtra(CategoryActivity.EXTRA_TITLE, title);
        context.startActivity(explicit);
    }

    /**
     * Downloads and sets thumbnail.
     *
     * @param context      Context to use.
     * @param imageView    Downloaded image is set to this View.
     * @param thumbnailUrl Link points to the image.
     */
    public static void setThumbnail(Context context, ImageView imageView, String thumbnailUrl) {
        Glide.with(context)
                .load(thumbnailUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * Shows a {@link Toast} containing custom messages. Removes the currently showing Toast.
     *
     * @param context   Context to use.
     * @param toast     Toast in Activity/Fragment.
     * @param messageID String resource ID contains the text.
     */
    public static void showToast(Context context, Toast toast, int messageID) {
        // Cancels the current showing Toast.
        if (toast != null) {
            toast.cancel();
        }

        // Sets new message and displays the Toast.
        toast = Toast.makeText(context, messageID, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Sets text to a {@link TextView}. If text is EMPTY, visibility is set to GONE.
     */
    public static void setText(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(text);
            textView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Opens the link in a supported app (if available) or in the device's browser. If the browser
     * is not available, user is notified via {@link Toast}.
     *
     * @param context   Context to use.
     * @param url       Link (String format) to open.
     * @param toast     Toast in Activity/Fragment.
     * @param messageID String resource ID contains the text.
     */
    public static void openBrowserOrApp(Context context, String url, Toast toast, int messageID) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (ActivityNotFoundException e) {
            showToast(context, toast, messageID);
        }
    }
}