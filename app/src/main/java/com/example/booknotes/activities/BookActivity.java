package com.example.booknotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.booknotes.BookAdapter;
import com.example.booknotes.BookLoader;
import com.example.booknotes.Books;
import com.example.booknotes.R;

import java.util.ArrayList;
import java.util.List;

public class BookActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Books>> {

    private BookAdapter mAdapter;
    private ListView listOfBooks;
    public static EditText searchLine;
    private TextView mEmptyTextView;

    private static final String USGS_REQUEST_URL = "https://books.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_main);

        searchLine = findViewById(R.id.search_line);
        listOfBooks = findViewById(R.id.list_of_books);

        List<Books> data = new ArrayList<>();
        mAdapter = new BookAdapter(this, data);
        listOfBooks.setAdapter(mAdapter);

        ImageButton searchButton = findViewById(R.id.search_button);
        ProgressBar progressBar = findViewById(R.id.prg_bar);
        progressBar.setVisibility(View.GONE);

        searchButton.setOnClickListener(view -> {
            //  Log.i("MainActivity", "ButtonClick");

            mEmptyTextView = findViewById(R.id.txt);
            listOfBooks.setEmptyView(mEmptyTextView);

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Log.i("MainActivity", "initLoader");
                progressBar.setVisibility(View.VISIBLE);
                if (mAdapter != null) {
                    mAdapter.clear();
                    mEmptyTextView.setText("");
                    getLoaderManager().restartLoader(1, null, this);
                } else {
                    getLoaderManager().initLoader(1, null, this);
                    // If there is a network connection, fetch data
                }
            } else {
                mEmptyTextView.setText(R.string.no_internet_connection);
            }
        });

        listOfBooks.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent numbersIntent = new Intent(getApplicationContext(), InfoActivity.class);
            BookActivity.this.startActivity(numbersIntent);
        });

    }

    @Override
    public Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
        //  Log.i("MainActivity", "onCreateLoader");
        String key = searchLine.getText().toString();
        String searchRequest = USGS_REQUEST_URL + key;
        Log.i("MainActivity", searchRequest);
        Uri baseUri = Uri.parse(searchRequest);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new BookLoader(this, uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> books) {
        // Log.i("MainActivity", "onLoadFinished");
        ProgressBar progressBar = findViewById(R.id.prg_bar);
        progressBar.setVisibility(View.GONE);
        mEmptyTextView.setText(R.string.no_books);
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        //Log.i("MainActivity", "onLoaderReset");
        mAdapter.clear();
    }

}