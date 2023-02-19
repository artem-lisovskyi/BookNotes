package com.example.booknotes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }


    public static List<Books> extractFeatureFromJson(String jsonResponse) {

        List<Books> books = new ArrayList<>();

        try {
            String title;
            List<String> authors;
           // String description;
            String image;

            JSONObject root = new JSONObject(jsonResponse);
            JSONArray jsonItemsArray = root.getJSONArray("items");

            int count = jsonItemsArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject jsonFeatureObject = jsonItemsArray.getJSONObject(i);
                JSONObject jsonVolumeInfo = jsonFeatureObject.getJSONObject("volumeInfo");
                JSONObject jsonImage = jsonVolumeInfo.getJSONObject("imageLinks");

                authors = new ArrayList<>();
                if (jsonVolumeInfo.has("authors")) {
                    JSONArray jsonAuthor = jsonVolumeInfo.getJSONArray("authors");
                    for (int j = 0; j < jsonAuthor.length(); j++) {
                        String author = jsonAuthor.getString(j);
                        authors.add(author);
                    }
                } else {
                    authors.add("No authors");
                }
                if (jsonVolumeInfo.has("title")) {
                    title = jsonVolumeInfo.getString("title");
                } else {
                    title = "No title";
                }
                if (jsonImage.has("thumbnail")) {
                    image = jsonImage.getString("thumbnail");
                } else {
                    image = "No image";

                }
//                if (jsonVolumeInfo.has("description")) {
//                    description = jsonVolumeInfo.getString("description");
//                } else {
//                    description = "No description";
//                }
                Books book = new Books(title, authors, image);
                books.add(book);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error with parsing", e);
        }

        return books;
    }


    public static List<Books> fetchBooksData(String requestUrl) {

        // Create URL object
        URL url = createURL(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Books> books = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return books;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error wth create URL", e);
        }
        return url;
    }
}
