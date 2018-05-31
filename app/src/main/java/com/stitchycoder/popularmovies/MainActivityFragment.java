package com.stitchycoder.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.stitchycoder.popularmovies.utilities.MovieDBJsonUtils;
import com.stitchycoder.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Brook Scott on 5/16/2018.
 *
 * Used the Android Flavor app example from Udacity to learn how to create this fragment
 */

@SuppressWarnings("RedundantCast")
public class MainActivityFragment extends Fragment {

    private MoviePosterAdapter mPosterAdapter;
    private static ArrayList<PopularMovie> mMovies = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Context mContext;

    public static final String MOVIE_DATA_EXTRA_KEY = "movies";

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mContext = getActivity().getApplicationContext();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_main, container, false);
        //mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_movie_posters);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2 ));
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE_DATA_EXTRA_KEY)) {
            loadMovieData(NetworkUtils.POPULARITY);
        }
        else {
            mMovies = savedInstanceState.getParcelableArrayList(MOVIE_DATA_EXTRA_KEY);
            //mPosterAdapter.setMovieData(mMovies);
            setPosterAdapter();
        }

//         mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), DetailActivity.class);
//                //PopularMovie movie = (PopularMovie) mPosterAdapter.getItem(position);
//                //fix this later to get the right
//                PopularMovie movie = (PopularMovie) mMovies.get(0);
//                intent.putExtra(MOVIE_DATA_EXTRA_KEY, movie);
//                startActivity(intent);
//            }
//        });

         return mRecyclerView;
    }


    class DownloadMovieData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String sortOrder = params[0];
            URL url = NetworkUtils.buildUrl(sortOrder);
            String result = "";

            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String movieData = "";

            try {
                movieData = MovieDBJsonUtils.getMovieDataFromJSON(result);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return movieData;
        }


        @Override
        protected void onPostExecute(String s) {
            Context context = getContext();

            try {
                mMovies.clear();
                mMovies.addAll(MovieDBJsonUtils.getMoviesArray(context, s));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //mPosterAdapter.setMovieData(mMovies);
            //mRecyclerView.setAdapter(mPosterAdapter);
            setPosterAdapter();

        }
    }

    private void loadMovieData(String sortOrder) {
        Context context = getActivity().getApplicationContext();
        if (NetworkUtils.hasNetworkConnection(context)) {
            DownloadMovieData task = new DownloadMovieData();
            task.execute(sortOrder);
        }
        else {
            Toast.makeText(context, R.string.internet_connectivity_message, Toast.LENGTH_LONG).show();
        }
    }

    public void setPosterAdapter() {
        mPosterAdapter = new MoviePosterAdapter(mMovies, new MoviePosterAdapter.OnPosterClickListener() {
            @Override
            public void OnPosterClick(PopularMovie movie) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                intent.putExtra(MOVIE_DATA_EXTRA_KEY, movie);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mPosterAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_DATA_EXTRA_KEY, mMovies);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
