package com.project.news_app.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.snackbar.Snackbar;
import com.project.news_app.R;
import com.project.news_app.activities.CategoryActivity;

/**
 * Contains methods that are used across the app.
 */
public class CommonUtils {
    /**
     * Notifies the unavailability of Browser in user's app.
     */
    private static Toast toast;

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
     * Sets custom colors to SwipeRefreshLayout.
     */
    public static void setRefreshLayoutColors(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorLightest);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorRedDark);
    }

    /**
     * Checks whether app has internet connectivity.
     *
     * @param connectivityManager Accesses network status.
     */
    public static boolean checkNetworkAvailability(ConnectivityManager connectivityManager) {
        // Get network info.
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(
                connectivityManager.getActiveNetwork());

        // Checks if Network is available.
        if (networkCapabilities == null) {
            return false;
        }
        // Checks whether Network is able to reach the internet.
        else return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    /**
     * Notifies internet is unavailable via {@link Snackbar}.
     */
    public static void showNetworkUnavailable(Context context, View view) {
        CommonUtils.showSnackbar(context, view, R.string.connection_unavailable,
                Snackbar.LENGTH_LONG, R.color.colorRedDark, R.color.colorLightest);
    }

    /**
     * Shows a {@link Snackbar} containing custom messages.
     *
     * @param context         Context to use.
     * @param view            Parent view.
     * @param message         Custom message contained in String resource ID.
     * @param duration        Sets how long the message is shown.
     * @param textColor       Sets the text color of Snackbar.
     * @param backgroundColor Sets the background color of Snackbar.
     */
    public static void showSnackbar(Context context, View view, int message, int duration,
                                    int textColor, int backgroundColor) {
        Snackbar.make(view, context.getString(message), duration)
                .setTextColor(ContextCompat.getColor(context, textColor))
                .setBackgroundTint(ContextCompat.getColor(context, backgroundColor))
                .show();
    }

    /**
     * Initializes Callbacks which notifies network changes whether app has gained or lost
     * network connectivity via {@link Snackbar}.
     *
     * @param context   Context to use.
     * @param view      Parent view.
     * @param lifecycle Prevents Snackbar appearing when Activity is NOT visible.
     * @return Callback for ConnectivityManager.
     */
    public static ConnectivityManager.NetworkCallback getNetworkCallback(Context context, View view,
                                                                         Lifecycle lifecycle) {
        return new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    // Shows "Connection Available".
                    showSnackbar(context, view, R.string.connection_available,
                            Snackbar.LENGTH_LONG, R.color.colorLightest,
                            R.color.colorRedDark);
                }
            }

            @Override
            public void onLost(@NonNull Network network) {
                if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    // Shows "Connection Unavailable".
                    showNetworkUnavailable(context, view);
                }
            }
        };
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
     * @param messageID String resource ID contains the text.
     */
    public static void showToast(Context context, int messageID) {
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
     * @param messageID String resource ID contains the text.
     */
    public static void openBrowserOrApp(Context context, String url, int messageID) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (ActivityNotFoundException e) {
            showToast(context, messageID);
        }
    }
}