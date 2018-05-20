package com.stitchycoder.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.stitchycoder.popularmovies.utilities.MovieDBJsonUtils;
import com.stitchycoder.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by brook on 5/16/2018.
 */

public class MainActivityFragment extends Fragment {

    private MoviePosterArrayAdapter mMovieArrayAdapter;
    private ImageView mMoviePosterView;
    private ArrayList<PopularMovie> mMovies = new ArrayList<>();
    private GridView mGridView;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mMovieArrayAdapter = new MoviePosterArrayAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.gv_movie_posters);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            loadMovieData(NetworkUtils.POPULARITY);
        }
        else {
            mMovies = savedInstanceState.getParcelableArrayList("movies");
            mMovieArrayAdapter.setMovieData(mMovies);
            mGridView.setAdapter(mMovieArrayAdapter);
        }


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                //How to pass our object to the detail activity
                //intent.putExtra();
                PopularMovie movie = (PopularMovie) mMovieArrayAdapter.getItem(position);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
        return rootView;

    }


    class DownloadMovieData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String sortOrder = params[0];
            URL url = NetworkUtils.buildUrl(sortOrder);
            String result = "";
            Context context = getContext();

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
                mMovies.clear();
                mMovies.addAll(MovieDBJsonUtils.getMoviesArray(getContext(), s));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mMovieArrayAdapter.setMovieData(mMovies);
            mGridView.setAdapter(mMovieArrayAdapter);

        }
    }

    public void loadMovieData(String sortOrder) {

        DownloadMovieData task = new DownloadMovieData();
        task.execute(sortOrder);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", mMovies);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getActivity();
        item.setChecked(true);
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.order_by_popularity:
                loadMovieData(NetworkUtils.POPULARITY);
                return true;
            case R.id.order_by_rating:
                loadMovieData(NetworkUtils.HIGHEST_RATING);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


}
