package com.stitchycoder.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import com.stitchycoder.popularmovies.utilities.MovieDBJsonUtils;
import com.stitchycoder.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView mMoviePosterView;
    private MoviePosterArrayAdapter posterAdapter;
    private ArrayList<PopularMovie> mMovies = new ArrayList<>();
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviePosterView = (ImageView) findViewById(R.id.iv_movie_poster);
        mGridView = (GridView) findViewById(R.id.gv_movie_posters);

        DownloadMovieData task = new DownloadMovieData();
        task.execute();



    }



    class DownloadMovieData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            URL url = NetworkUtils.buildUrl();
            String result = "";
            Context context = getApplicationContext();

            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //create movie objects here
            String movieData = "";

            try {
                movieData = MovieDBJsonUtils.getMovieDataFromJSON(context, result);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return movieData;
        }


        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            try {
                mMovies.addAll(MovieDBJsonUtils.getMoviesArray(getApplicationContext(), s));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            posterAdapter = new MoviePosterArrayAdapter(getApplicationContext(), mMovies);
            mGridView.setAdapter(posterAdapter);

        }
    }
}
