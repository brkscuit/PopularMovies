package com.stitchycoder.popularmovies;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by brook on 5/9/2018.
 */

public class PopularMovie implements Parcelable {

    private int mMovieId;
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private double mUserRating;
    private String mReleaseDate;

    public PopularMovie(int movieId) {
        this.mMovieId = movieId;
    }

    protected PopularMovie(Parcel in) {
        mMovieId = in.readInt();
        mTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mUserRating = in.readDouble();
        mReleaseDate = in.readString();
    }

    public static final Creator<PopularMovie> CREATOR = new Creator<PopularMovie>() {
        @Override
        public PopularMovie createFromParcel(Parcel in) {
            return new PopularMovie(in);
        }

        @Override
        public PopularMovie[] newArray(int size) {
            return new PopularMovie[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMovieId);
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeDouble(mUserRating);
        dest.writeString(mReleaseDate);
    }


}
