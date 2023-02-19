package com.example.booknotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.booknotes.R;



public class InfoActivity extends AppCompatActivity {
    private static final String USGS_REQUEST_URL = "https://books.googleapis.com/books/v1/volumes?q=" + BookActivity.searchLine.getText().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}