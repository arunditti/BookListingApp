package com.arunditti.android.booklistingapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;


import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private static final int BOOK_LOADER_ID = 1;

    private Button mSearchButton;
    private EditText mSearchText;
    private String mUrlRequestBooks = "";

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
//                BookAsyncTask task = new BookAsyncTask();
//                task.execute();

                updateQueryUrl(" ");
                restartLoader();
                Log.i(LOG_TAG, " Search value: " + mSearchText);

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

    private String updateQueryUrl(String searchValue) {

        EditText searchField = (EditText) findViewById(R.id.book_search_text_view);
        String searchInput = searchField.getText().toString();

            if(searchInput.length() == 0) {
                return null;
            }

        searchValue = searchValue.replace(" ", "+");
            String mUrlRequestBooks = BOOK_REQUEST_URL + searchValue;
            return mUrlRequestBooks;
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader is called");
        updateQueryUrl(mSearchText.getText().toString());
        return new BookLoader(this, mUrlRequestBooks);
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

//    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {
//
//        EditText searchField = (EditText) findViewById(R.id.book_search_text_view);
//        String searchInput = searchField.getText().toString();
//
//        @Override
//        protected List<Book> doInBackground(String... urls) {
//            //Dont perform the request if there are no urls, or the first url is null
//            if(searchInput.length() == 0) {
//                return null;
//            }
//
//            searchInput = searchInput.replace(" ", "+");
//            String searchString = BOOK_REQUEST_URL + searchInput;
//            List<Book> result = QueryUtils.fetchBookData(searchString);
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(List<Book> data) {
//            //Clear the adapter of previous book results
//            mAdapter.clear();
//
//            //If there is valid list of books then add them to the adapter's data set. This will trigger the listView to update
//            if(data != null && !data.isEmpty()) {
//                mAdapter.addAll(data);
//            }
//        }
//    }


}
