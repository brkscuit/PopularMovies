package com.stitchycoder.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Brook Scott on 5/12/2018.
 *
 * Thanks to Udacity for all the helpful examples on their Android Development courses
 * like the Android Flavor app
 *
 */

@SuppressWarnings("RedundantCast")
class MoviePosterArrayAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<PopularMovie> mMovies = new ArrayList<>();

    public MoviePosterArrayAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PopularMovie movie = mMovies.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.movie_item, parent, false);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.iv_movie_poster);

        Picasso.with(mContext).load(movie.getPosterPath()).into(imageView);
        return imageView;
    }


    public void setMovieData(ArrayList<PopularMovie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }
}
