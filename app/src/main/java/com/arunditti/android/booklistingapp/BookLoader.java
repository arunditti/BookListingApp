package com.arunditti.android.booklistingapp;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by arunditti on 2/2/18.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.i(LOG_TAG, "Book Loaded" +url);

    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading is called");
       // super.onStartLoading();
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground is called");
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Book> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}
