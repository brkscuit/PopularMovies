package com.stitchycoder.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.stitchycoder.popularmovies.BuildConfig;
import com.stitchycoder.popularmovies.PopularMovie;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Brook Scott on 5/10/2018.
 *
 * Relied heavily on the Udacity example code available through the Android Developer Course
 * as well as Android developer docs and guides
 */

public class NetworkUtils {

    // You must supply your own API key and place it in gradle.properties file, then
    // reference it in the app build.gradle
    private static final String API_KEY = BuildConfig.ApiKey;
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p";
    private static final String IMAGE_SIZE = "w185";
    private static final String QUERY_PARAM = "api_key";
    public static final String POPULARITY = "popular";
    public static final String HIGHEST_RATING = "top_rated";

    //Build the URL to get the 20 most popular or 20 top rated movies depending on the
    //sortOrder parameter that was passed in.
    public static URL buildUrl(String sortOrder)  {

        //default sort order
        if (sortOrder.isEmpty()) {
            sortOrder = POPULARITY;
        }

        //build uri
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(sortOrder)
                .appendQueryParameter(QUERY_PARAM, API_KEY)

                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    //Build the movie poster image URL
    public static URL buildImagePath(PopularMovie movie) {

        Uri uri = Uri.parse(BASE_IMAGE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(movie.getPosterPath())
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

    // Check for a network connection
    // Referenced this article https://dzone.com/articles/checking-an-internet-connection-in-android-2
    // and developer docs to learn how to do this
    public static boolean hasNetworkConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

        return networkInfo!=null && networkInfo.isConnected();
    }

}
