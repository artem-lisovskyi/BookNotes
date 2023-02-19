package com.example.booknotes;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Books> {


    public BookAdapter(@NonNull Context context, @NonNull List<Books> listBooks) {
        super(context, 0, listBooks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);
        }

        Books currentBooks = getItem(position);


        ImageView imageView = listItemView.findViewById(R.id.poster);
        Picasso.get().load(currentBooks.getImage()).into(imageView);


        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(currentBooks.getTitle());

        TextView authorView = listItemView.findViewById(R.id.author);
        List<String> authors = currentBooks.getAuthor();
        StringBuilder authorsString = new StringBuilder();
        int count = authors.size();
        for (int i = 0; i < count; i++) {
            if (i != count - 1) {
                authorsString.append(authors.get(i)).append(", ");
            } else {
                authorsString.append(authors.get(i));
            }
            authorView.setText(authorsString);
        }

//        TextView descriptionView = listItemView.findViewById(R.id.description);
//        descriptionView.setText(currentBooks.getDescription());

        Log.i("Adapter", currentBooks.getTitle());

        return listItemView;
    }
}
