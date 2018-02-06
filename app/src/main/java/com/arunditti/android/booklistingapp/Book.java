package com.arunditti.android.booklistingapp;

import android.support.v4.app.ShareCompat;
import java.io.Serializable;


/**
 * Created by arunditti on 12/13/17.
 */

public class Book {
    private String mBookTitle;
    private String mBookAuthor;
    private String mYearOfpublication;
    private String mSmallThumbnailLink;
    private String mUrl;

    public Book (String bookTitle, String bookAuthor, String yearOfPublication, String smallThumbnailLink, String url) {
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mYearOfpublication = yearOfPublication;
        mSmallThumbnailLink = smallThumbnailLink;
        mUrl = url;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public String getBookAuthor() {
        return mBookAuthor;
    }

    public String getYearOfPublication() {
        return mYearOfpublication;
    }

    public String getSmallThumbnailLink() {
        return mSmallThumbnailLink;
    }

    public String getUrl() {
        return mUrl;
    }
}
