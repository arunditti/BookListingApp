package com.arunditti.android.booklistingapp;

import android.app.LoaderManager;

import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String API_URL = "https://www.googleapis.com/books/v1/volumes?";

    private static final int BOOK_LOADER_ID = 1;

    private Button mSearchButton;
    private EditText mSearchText;

    //Adapter for the list of books
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create a fake list of books
        ArrayList<Book> books = new ArrayList<Book>();

        //Find the reference tpo the listview in the layout
        ListView booksListView = (ListView) findViewById(R.id.listview);

        //Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, books);

        booksListView.setAdapter(mAdapter);

        mSearchText = (EditText) findViewById(R.id.book_search_text_view);

        mSearchButton = (Button) findViewById(R.id.search_button);

        LoaderManager loaderManager = getLoaderManager();
        Log.i(LOG_TAG, "Calling initLoader");
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restartLoader();
                Log.i(LOG_TAG, "Search value: " + mSearchText.getText().toString());
            }
        });

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Book currentBook = mAdapter.getItem(position);
                Uri bookUri = Uri.parse(currentBook.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader is called");

        Uri baseUri = Uri.parse(API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", mSearchText.getText().toString());
        Log.d("uri", "onCreateLoader: " + uriBuilder.toString());
        return new BookLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.i(LOG_TAG, "onLoadFinished is called");
        mAdapter.clear();
        if(books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.i(LOG_TAG, "onResetLoader is called");
        mAdapter.clear();
    }

    public void restartLoader() {
        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
    }
}
