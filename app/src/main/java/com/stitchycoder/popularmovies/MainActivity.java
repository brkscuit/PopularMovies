package com.stitchycoder.popularmovies;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stitchycoder.popularmovies.utilities.MovieDBJsonUtils;
import com.stitchycoder.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.stitchycoder.popularmovies.utilities.NetworkUtils.buildImagePath;

public class MainActivity extends AppCompatActivity {

    private ImageView mMoviePosterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviePosterView = (ImageView) findViewById(R.id.iv_movie_poster);

        new DownloadMovieData().execute();

    }

    class DownloadMovieData extends AsyncTask<String, Void, String> {

        ArrayList<Movie> popularMovies = new ArrayList<>();

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
            super.onPostExecute(s);
            try {
                popularMovies = MovieDBJsonUtils.getMoviesArray(getApplicationContext(), s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.v("Popular Movies array", "id: " + popularMovies.get(0).getMovieId());
            URL imagePath = NetworkUtils.buildImagePath(popularMovies.get(1).getMovieId(), popularMovies.get(1).getPosterPath());
            Log.v("Image path", imagePath.toString());
            Picasso.with(getApplicationContext()).load(imagePath.toString()).into(mMoviePosterView);

        }
    }
}
