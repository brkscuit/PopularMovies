package com.stitchycoder.popularmovies.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.stitchycoder.popularmovies.Movie;
import com.stitchycoder.popularmovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.provider.Settings.Global.getString;

/**
 * Created by brook on 5/12/2018.
 */

public final class MovieDBJsonUtils {

    public static String getMovieDataFromJSON(Context context, String jsonResult) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonResult);
        JSONArray popularMovies = jsonObject.getJSONArray("results");

        return popularMovies.toString();

    }

    public static ArrayList<Movie> getMoviesArray(Context context, String jsonMovies) throws JSONException {
        ArrayList<Movie> movies = new ArrayList<>();
        JSONArray jsonArrayMovies = new JSONArray(jsonMovies);
        for (int i= 0; i < jsonArrayMovies.length(); i++) {

            movies.add(buildMovieObject(context, jsonArrayMovies.getJSONObject(i)));
        }

        return movies;
    }

    private static Movie buildMovieObject(Context context, JSONObject jsonMovieObject) throws JSONException {

        final String ID = context.getResources().getString(R.string.movie_id);
        final String POSTER_PATH = context.getResources().getString(R.string.poster_path);
        final String TITLE = context.getResources().getString(R.string.title);
        final String OVERVIEW = context.getResources().getString(R.string.overview);
        final String RELEASE_DATE = context.getResources().getString(R.string.release_date);
        final String VOTE_AVERAGE = context.getResources().getString(R.string.vote_average);

        int movieId = jsonMovieObject.getInt(ID);
        Movie movie = new Movie(movieId);
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
            movie.setReleaseDate(jsonMovieObject.getString(RELEASE_DATE));
        }
        if (jsonMovieObject.has(VOTE_AVERAGE)) {
            movie.setUserRating(jsonMovieObject.getDouble(VOTE_AVERAGE));
        }

        return movie;

    }



}
