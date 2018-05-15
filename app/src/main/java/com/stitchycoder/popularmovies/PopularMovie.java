package com.stitchycoder.popularmovies;

import android.media.Image;

import java.util.Date;

/**
 * Created by brook on 5/9/2018.
 */

public class PopularMovie {

    private int mMovieId;
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private double mUserRating;
    private String mReleaseDate;

    public PopularMovie(int movieId) {
        this.mMovieId = movieId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public int getMovieId() {
        return mMovieId;
    }


    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public double getUserRating() {
        return mUserRating;
    }

    public void setUserRating(double userRating) {
        mUserRating = userRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }
}
