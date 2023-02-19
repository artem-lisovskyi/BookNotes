package com.example.booknotes;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Books>> {
    private final String mRequestUrl;

    public BookLoader(@NonNull Context context, String url) {
        super(context);
        mRequestUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Books> loadInBackground() {
        if (mRequestUrl == null) {
            return null;
        }

        return QueryUtils.fetchBooksData(mRequestUrl);

    }
}
