package com.arunditti.android.booklistingapp;

/**
 * Created by arunditti on 12/13/17.
 */

public class Book {
    private String mBookTitle;
    private String mBookAuthor;
    private String mYearOfpublication;
    private String openLibraryId;

    public Book (String bookTitle, String bookAuthor, String yearOfPublication) {
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mYearOfpublication = yearOfPublication;
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

}
