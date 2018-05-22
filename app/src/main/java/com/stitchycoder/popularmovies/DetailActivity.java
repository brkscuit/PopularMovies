package com.stitchycoder.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

@SuppressWarnings("RedundantCast")
public class DetailActivity extends AppCompatActivity {

    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private PopularMovie mMovie;
    private TextView mOverview;
    private TextView mRating;
    private TextView mReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieTitle = (TextView) findViewById(R.id.tv_title_detail);
        mMoviePoster = (ImageView) findViewById(R.id.iv_detail_poster);
        mOverview = (TextView) findViewById(R.id.tv_overview);
        mRating = (TextView) findViewById(R.id.tv_rating);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);


        Intent intent = getIntent();
        //check to make sure the movie data got stored in saveInstanceState
        if (intent.hasExtra(MainActivityFragment.MOVIE_DATA_EXTRA_KEY)) {
            mMovie = intent.getParcelableExtra(MainActivityFragment.MOVIE_DATA_EXTRA_KEY);
            buildUI();
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.movie_data_unavailable, Toast.LENGTH_LONG).show();
        }

    }

    //set the data on the views
    private void buildUI() {
        mMovieTitle.setText(mMovie.getTitle());
        Picasso.with(getApplicationContext()).load(mMovie.getPosterPath()).into(mMoviePoster);
        mReleaseDate.setText(mMovie.getReleaseDate());
        mOverview.setText(mMovie.getOverview());
        mRating.setText(String.valueOf(mMovie.getUserRating()));
    }
}
