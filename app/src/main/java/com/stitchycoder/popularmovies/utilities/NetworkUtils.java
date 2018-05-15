package com.stitchycoder.popularmovies.utilities;

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
    private static final String SORT_BY_PARAM = "sort_by";
    private static final String POPULARITY = "popularity.desc";
    private static final String HIGHEST_RATING = "highest_rating";

    public static URL buildUrl()  {

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(POPULAR_MOVIES)
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

    public static URL buildImagePath(PopularMovie movie) {
        String movieIdString = Integer.toString(movie.getMovieId());
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
