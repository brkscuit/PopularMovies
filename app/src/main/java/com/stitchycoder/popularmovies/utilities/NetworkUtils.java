package com.stitchycoder.popularmovies.utilities;

import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.util.Log;

import com.squareup.picasso.OkHttpDownloader;
import com.stitchycoder.popularmovies.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by brook on 5/10/2018.
 *
 */

public class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String POPULAR_MOVIES = "popular";
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p";
    //private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    private static final String IMAGE_SIZE = "w185";
    private static final String QUERY_PARAM = "api_key";
    private static final String API_KEY = BuildConfig.ApiKey;

    public static URL buildUrl()  {

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(POPULAR_MOVIES)
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
            Log.v("Url: ", url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildImagePath(int movieId, String posterPath) {
        String movieIdString = Integer.toString(movieId);
        Uri uri = Uri.parse(BASE_IMAGE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(posterPath)
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
            if (scanner.hasNext()) {
                return scanner.next();
            }
            else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

}
