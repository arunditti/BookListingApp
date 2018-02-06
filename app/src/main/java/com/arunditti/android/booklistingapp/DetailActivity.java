package com.arunditti.android.booklistingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private String mBooks;
    private TextView mBookDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBookDetails = (TextView) findViewById(R.id.book_details);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity != null) {
            if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mBooks = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                mBookDetails.setText(mBooks);
            }
        }
    }
}
