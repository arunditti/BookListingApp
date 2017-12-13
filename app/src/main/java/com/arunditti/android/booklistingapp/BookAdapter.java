package com.arunditti.android.booklistingapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arunditti on 12/13/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentBook.getBookTitle());

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_text_view);
        authorTextView.setText(currentBook.getBookAuthor());

        TextView yearOfPublicationTextView = (TextView) listItemView.findViewById(R.id.year_of_publication_text_view);
        yearOfPublicationTextView.setText(currentBook.getYearOfPublication());

        return listItemView;
    }
}
