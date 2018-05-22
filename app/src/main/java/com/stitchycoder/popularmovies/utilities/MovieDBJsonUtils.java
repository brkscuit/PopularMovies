package com.stitchycoder.popularmovies.utilities;

import android.content.Context;

import com.stitchycoder.popularmovies.PopularMovie;
import com.stitchycoder.popularmovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Brook Scott on 5/12/2018.
 *
 * Used the Udacity examples available through the Android courses to develop this class for parsing
 * the JSON returned from www.themoviedb.org API
 */

public final class MovieDBJsonUtils {

    private static final String DELIMITER = "-";
    private static final String RESULTS_KEY = "results";

    public static String getMovieDataFromJSON(String jsonResult) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonResult);
        JSONArray popularMovies = jsonObject.getJSONArray(RESULTS_KEY);
        return popularMovies.toString();

    }

    public static ArrayList<PopularMovie> getMoviesArray(Context context, String jsonMovies) throws JSONException {
        ArrayList<PopularMovie> movies = new ArrayList<>();
        JSONArray jsonArrayMovies = new JSONArray(jsonMovies);
        for (int i= 0; i < jsonArrayMovies.length(); i++) {

            movies.add(buildMovieObject(context, jsonArrayMovies.getJSONObject(i)));
        }

        return movies;
    }

    private static PopularMovie buildMovieObject(Context context, JSONObject jsonMovieObject) throws JSONException {

        final String ID = context.getResources().getString(R.string.movie_id);
        final String POSTER_PATH = context.getResources().getString(R.string.poster_path);
        final String TITLE = context.getResources().getString(R.string.title);
        final String OVERVIEW = context.getResources().getString(R.string.overview);
        final String RELEASE_DATE = context.getResources().getString(R.string.release_date);
        final String VOTE_AVERAGE = context.getResources().getString(R.string.vote_average);

        int movieId = jsonMovieObject.getInt(ID);
        PopularMovie movie = new PopularMovie(movieId);
        if (jsonMovieObject.has(TITLE)) {
            movie.setTitle(jsonMovieObject.getString(TITLE));
        }
        if (jsonMovieObject.has(POSTER_PATH)) {
            movie.setPosterPath(jsonMovieObject.getString(POSTER_PATH));
        }
        if (jsonMovieObject.has(OVERVIEW)) {
            movie.setOverview(jsonMovieObject.getString(OVERVIEW));
        }
        if (jsonMovieObject.has(RELEASE_DATE)) {
            String formatDate = jsonMovieObject.getString(RELEASE_DATE);
            String date[] = formatDate.split(DELIMITER, 3);
            String year = date[0];
            movie.setReleaseDate(year);
        }
        if (jsonMovieObject.has(VOTE_AVERAGE)) {
            movie.setUserRating(jsonMovieObject.getDouble(VOTE_AVERAGE));
        }
        URL imagePath = NetworkUtils.buildImagePath(movie);
        movie.setPosterPath(imagePath.toString());

        return movie;

    }

}
