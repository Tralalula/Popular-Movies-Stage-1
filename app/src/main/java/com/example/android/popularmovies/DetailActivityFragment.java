package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if (intent != null && intent.hasExtra("movie bundle")) {
            Bundle bundle = intent.getBundleExtra("movie bundle");
            Movie movie = (Movie) bundle.getSerializable("movie");

            String movieBackdropPath = movie.getBackdropPath(movie.SIZE_W780);
            String moviePosterPath = movie.getPosterPath(movie.SIZE_W342);

            String movieOriginalTitle = movie.getOriginalTitle();
            String movieOverview = movie.getOverview();
            String movieReleaseDate = movie.getReleaseDate();
            String movieVoteAverage = movie.getVoteAverage();
            String movieVoteCount = movie.getVoteCount();

            ImageView backdropPathView = (ImageView) rootView.findViewById(R.id.backdrop);
            backdropPathView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getActivity())
                    .load(movieBackdropPath)
                    .into(backdropPathView);

            ImageView posterPathView = (ImageView) rootView.findViewById(R.id.poster);
            Picasso.with(getActivity())
                    .load(moviePosterPath)
                    .into(posterPathView);

            ((TextView) rootView.findViewById(R.id.original_title)).setText(movieOriginalTitle);
            ((TextView) rootView.findViewById(R.id.overview)).setText(movieOverview);
            ((TextView) rootView.findViewById(R.id.release_date)).setText(movieReleaseDate);
            ((TextView) rootView.findViewById(R.id.rating)).setText(movieVoteAverage + " by " + movieVoteCount + " users");
        }

        return rootView;
    }
}
