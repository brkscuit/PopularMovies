package com.stitchycoder.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.PosterViewHolder>{

    private final ArrayList<PopularMovie> mMovies;
    private final OnPosterClickListener mListener;


    public interface OnPosterClickListener {
        void OnPosterClick(PopularMovie movie);
    }

    public MoviePosterAdapter(ArrayList<PopularMovie> movies, OnPosterClickListener listener  ) {
        mMovies = movies;
        mListener = listener;

    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        PosterViewHolder viewHolder = new PosterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        PopularMovie movie = mMovies.get(position);
        holder.bind(movie, mListener);
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder{

        public final ImageView mMoviePoster;

        public PosterViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
        }

        public void bind(final PopularMovie movie, final MoviePosterAdapter.OnPosterClickListener listener) {
            Picasso.with(itemView.getContext()).load(movie.getPosterPath()).into(mMoviePoster);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.OnPosterClick(movie);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (null == mMovies) {
            return 0;
        }
        else {
            return mMovies.size();
        }
    }

    public void setMovieData(ArrayList<PopularMovie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }
}
