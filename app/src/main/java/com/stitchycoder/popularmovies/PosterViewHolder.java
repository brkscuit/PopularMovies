package com.stitchycoder.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by brook on 5/30/2018.
 */


public class PosterViewHolder extends RecyclerView.ViewHolder{

    public final ImageView mMoviePoster;


    public PosterViewHolder(View itemView) {
        super(itemView);
        mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
    }

    public void bind(final PopularMovie movie, final MoviePosterAdapter.OnPosterClickListener listener) {
        //Picasso.with(mContext).load(movie.getPosterPath()).into(holder.mMoviePoster);
    }


}
