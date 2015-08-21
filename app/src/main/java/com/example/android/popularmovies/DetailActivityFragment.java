/*
 * Copyright (C) 2015 Tobias
 */

package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    @Bind(R.id.backdrop) ImageView mMovieBackdropPath;
    @Bind(R.id.poster)   ImageView mMoviePosterPath;

    @Bind(R.id.original_title) TextView mMovieOriginalTitle;
    @Bind(R.id.overview)       TextView mMovieOverview;
    @Bind(R.id.release_date)   TextView mMovieReleaseDate;
    @Bind(R.id.rating)         TextView mMovieRating;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if (intent != null) {
            Movie movie = intent.getParcelableExtra("movie parcel");
            updateDetailActivityWithData(rootView, movie);
        }

        return rootView;
    }

    /**
     * Update the DetailActivity UI with the movie information.
     *
     * @param rootView
     * @param movie used to update DetailActivity UI
     */
    private void updateDetailActivityWithData(View rootView, Movie movie) {
        ButterKnife.bind(this, rootView);
        Activity activity = getActivity();

        mMovieBackdropPath.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(activity)
                .load(movie.getBackdropPath())
                .into(mMovieBackdropPath);

        Picasso.with(activity)
                .load(movie.getPosterPath())
                .into(mMoviePosterPath);

        mMovieOriginalTitle.setText(movie.getOriginalTitle());
        mMovieOverview.setText(movie.getOverview());
        mMovieReleaseDate.setText(movie.getReleaseDate());
        mMovieRating.setText(movie.getVoteAverage() + " by " + movie.getVoteCount() + " users");
    }
}
