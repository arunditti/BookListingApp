package com.arunditti.android.booklistingapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

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

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookAsyncTask task = new BookAsyncTask();
                task.execute();
            }
        });

    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        EditText searchField = (EditText) findViewById(R.id.book_search_text_view);
        String searchInput = searchField.getText().toString();

        @Override
        protected List<Book> doInBackground(String... urls) {
            //Dont perform the request if there are no urls, or the first url is null
            if(searchInput.length() == 0) {
                return null;
            }

            searchInput = searchInput.replace(" ", "+");
            String searchString = BOOK_REQUEST_URL + searchInput;
            List<Book> result = QueryUtils.fetchBookData(searchString);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> data) {
            //Clear the adapter of previous book results
            mAdapter.clear();

            //If there is valid list of books then add them to the adapter's data set. This will trigger the listView to update
            if(data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
