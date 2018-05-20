package com.stitchycoder.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        mMovie = intent.getParcelableExtra("movie");

        mMovieTitle.setText(mMovie.getTitle());
        Picasso.with(getApplicationContext()).load(mMovie.getPosterPath()).into(mMoviePoster);
        mReleaseDate.setText(mMovie.getReleaseDate());
        mOverview.setText(mMovie.getOverview());
        mRating.setText(String.valueOf(mMovie.getUserRating()));


    }
}
