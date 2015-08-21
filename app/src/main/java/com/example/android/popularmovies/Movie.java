package com.example.android.popularmovies;

import java.util.Arrays;

/**
 * Created by Tobias on 21-08-2015.
 */
public class Movie {
    private final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private final String[] SIZES = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};

    public final String SIZE_W92 = "w92";
    public final String SIZE_W154 = "w154";
    public final String SIZE_W185 = "w185";
    public final String SIZE_W342 = "w342";
    public final String SIZE_W500 = "w500";
    public final String SIZE_W780 = "w780";
    public final String SIZE_ORIGINAL = "original";

    private int mId;

    private int[] mGenres;

    private String mBackdropPath;
    private String mPosterPath;

    private String mOriginalTitle;
    private String mOriginalLanguage;
    private String mOverview;
    private String mReleaseDate;
    private String mVoteAverage;
    private String mVoteCount;

    private double mPopularity;

    public Movie(int id) {
        mId = id;
    }

    public void setGenres(int[] genres) {
        mGenres = genres;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }

    public void setVoteCount(String voteCount) {
        mVoteCount = voteCount;
    }

    public void setPopularity(double popularity) {
        mPopularity = popularity;
    }


    public int getId() {
        return mId;
    }

    public int[] getGenres() {
        return mGenres;
    }

    private String getImageUrl(String size, String path) {
        if (!Arrays.asList(SIZES).contains(size)) {
            throw new NullPointerException();
        }
        return IMAGE_URL + size + "/" + path;
    }

    public String getBackdropPath(String size) {
        return getImageUrl(size, mBackdropPath);
    }

    public String getPosterPath(String size) {
        return getImageUrl(size, mPosterPath);
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public String getVoteCount() {
        return mVoteCount;
    }

    public double getPopularity() {
        return mPopularity;
    }
}
