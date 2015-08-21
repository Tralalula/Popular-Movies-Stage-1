/*
 * Copyright (C) 2015 Tobias
 */

package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */

public class MainActivityFragment extends Fragment {
    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

    private final String API_KEY = new ApiKey().getKey();
    private final String API_BASE_URL = "https://api.themoviedb.org/3/";
    private final String API_KEY_PARAM = "api_key";
    private final String SORT_BY_PARAM = "sort_by";

    private final String MOST_POPULAR_PATH = "discover/movie";
    private final String TOP_RATED_PATH = "movie/top_rated";

    private ImageAdapter moviePosterAdapter;
    private ArrayList<Movie> mMoviesList;

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    /**
     * Executes the FetchMoviesTask, with the sort order and path specified in the settings.
     */
    private void updateMovies() {
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        String sortOrder = sharedPrefs.getString(
                getString(R.string.pref_sort_order_key),
                getString(R.string.pref_sort_order_most_popular));

        String path = null;
        if (sortOrder.equals(getString(R.string.pref_sort_order_most_popular))) {
            path = MOST_POPULAR_PATH;
        } else if (sortOrder.equals(getString(R.string.pref_sort_order_top_rated))) {
            path = TOP_RATED_PATH;
        }

        fetchMoviesTask.execute(path, sortOrder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        moviePosterAdapter = new ImageAdapter(getActivity());
        GridView gridview = (GridView) rootView.findViewById(R.id.grid_view);
        gridview.setAdapter(moviePosterAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(rootView.getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", mMoviesList.get(position));
                intent.putExtra("movie bundle", bundle);
                startActivity(intent);
            }
        });

        return rootView;
    }

    /**
     * Returns a new URL of type String, using TMDb API.
     * The URL contains the JSON data needed for the FetchMoviesTask
     * to populate the GridView, and the data needed for the DetailActivity.
     *
     * @param path TOP_RATED_PATH or MOST_POPULAR_PATH
     * @param sortByParam SORT_BY_PARAM
     * @param movieSortBy movie sort order
     * @return URL of type String
     * @throws MalformedURLException
     */
    private URL generateUrl(String path, String sortByParam, String movieSortBy)
            throws MalformedURLException {
        Uri builtUri = Uri.parse(API_BASE_URL + path);
        if (!path.equals(TOP_RATED_PATH)) {
            builtUri = builtUri.buildUpon()
                    .appendQueryParameter(sortByParam, movieSortBy)
                    .build();
        }
        builtUri = builtUri.buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        return new URL(builtUri.toString());
    }

    /**
     * Returns an ArrayList of type Movie, and it sets mMoviesList to the following ArrayList.
     * It is used to populate the GridView layout with poster images, using the moviePosterAdapter.
     * It is also used to pass data to another activity.
     *
     * Each Movie object will be set with a:
     * backdropPath
     * posterPath
     * originalTitle
     * overview
     * releaseDate
     * voteAverage
     * voteCount
     *
     * @param moviesJsonStr a TMDb JSON string with information about the desired movies
     * @return ArrayList of type Movie with all the movies added from moviesJsonStr
     * @throws JSONException
     */
    private ArrayList<Movie> getMovies(String moviesJsonStr) throws JSONException {
        final String TMDB_RESULTS = "results";
        final String TMDB_backdrop_path = "backdrop_path";
        final String TMDB_ID = "id";
        final String TMDB_ORIGINAL_TITLE = "original_title";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_RELEASE_DATE = "release_date";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_VOTE_AVERAGE = "vote_average";
        final String TMDB_VOTE_COUNT = "vote_count";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(TMDB_RESULTS);

        int moviesArrayLength = moviesArray.length();

        mMoviesList = new ArrayList<Movie>();
        for (int i = 0; i < moviesArrayLength; i++) {
            JSONObject movieInfo = moviesArray.getJSONObject(i);

            int id = movieInfo.getInt(TMDB_ID);

            String backdropPath = movieInfo.getString(TMDB_backdrop_path);
            String posterPath = movieInfo.getString(TMDB_POSTER_PATH);

            String originalTitle = movieInfo.getString(TMDB_ORIGINAL_TITLE);
            String overview = movieInfo.getString(TMDB_OVERVIEW);
            String releaseDate = movieInfo.getString(TMDB_RELEASE_DATE);

            String voteAverage = movieInfo.getString(TMDB_VOTE_AVERAGE);
            String voteCount = movieInfo.getString(TMDB_VOTE_COUNT);

            Movie movie = new Movie(id);
            movie.setBackdropPath(backdropPath);
            movie.setPosterPath(posterPath);
            movie.setOriginalTitle(originalTitle);
            movie.setOverview(overview);
            movie.setReleaseDate(releaseDate);
            movie.setVoteAverage(voteAverage);
            movie.setVoteCount(voteCount);
            mMoviesList.add(movie);
        }

        return mMoviesList;
    }

    // The following code is taken from the following gist: https://gist.github.com/anonymous/1c04bf2423579e9d2dcd
    // provided by Udacity in the Developing Android Apps course: https://www.udacity.com/course/developing-android-apps--ud853
    private class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr = null;

            try {
                URL url = generateUrl(params[0], SORT_BY_PARAM, params[1]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                moviesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovies(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> result) {
            if (result != null) {
                moviePosterAdapter.clear();
                for (Movie movie : result) {
                    moviePosterAdapter.add(movie.getPosterPath(movie.SIZE_W342));
                }
            }
        }
    }
}
