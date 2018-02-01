package com.example.android.booksearch;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class BookListAdapter extends ArrayAdapter<Book> {

    public BookListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.listitem, parent, false);
        }
        Book item = getItem(position);
        TextView bookName = (TextView) listItem.findViewById(R.id.bookName);
        TextView author = (TextView) listItem.findViewById(R.id.author);
        bookName.setText(item.getbookName());
        author.setText(item.getAuthor());
        return listItem;
    }
}
