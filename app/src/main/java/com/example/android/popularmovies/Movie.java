/*
 * Copyright (C) 2015 Tobias
 */

package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * The following class is meant to be used with TMDb (the Movie Database)
 * The following setters/getters correspond to the movies JSON data
 * Familiarity with TMDb's API is expected.
 * https://www.themoviedb.org/
 * https://www.themoviedb.org/documentation/api
 */

public class Movie implements Parcelable {
    private final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private final String[] AVAILABLE_SIZES = {
            "w92",
            "w154",
            "w185",
            "w342",
            "w500",
            "w780",
            "original"
    };

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

    /**
     * Constructs a new Movie with the specificed TMDB movie id
     *
     * @param id TMDB movie id
     */
    public Movie(int id) {
        mId = id;
    }

    /**
     * Sets the genres of the movie to the following genres stored in the array
     *
     * @param genres an array of type int with TMDB movie genre ids
     */
    public void setGenres(int[] genres) {
        mGenres = genres;
    }

    /**
     * Sets the path for the backdrop image to the following string
     *
     * @param backdropPath
     */
    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    /**
     * Sets the path for the poster image to the following string
     *
     * @param posterPath
     */
    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    /**
     * Sets the original title for the movie to be the following string
     *
     * @param originalTitle
     */
    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    /**
     * Sets the original language of the movie to be the following string
     *
     * @param originalLanguage
     */
    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    /**
     * Sets the overview of the movie to be the following string
     *
     * @param overview
     */
    public void setOverview(String overview) {
        mOverview = overview;
    }

    /**
     * Sets the release date of the movie to be the following string
     *
     * @param releaseDate
     */
    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    /**
     * Sets the vote average of the movie to be the following string
     *
     * @param voteAverage
     */
    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }

    /**
     * Sets the vote count of the movie to be the following string
     *
     * @param voteCount
     */
    public void setVoteCount(String voteCount) {
        mVoteCount = voteCount;
    }

    /**
     * Sets the popularity of the movie to be the following string
     *
     * @param popularity
     */
    public void setPopularity(double popularity) {
        mPopularity = popularity;
    }

    /**
     * @return the movies ID
     */
    public int getId() {
        return mId;
    }

    /**
     * @return an array of type int containing the movies genres
     */
    public int[] getGenres() {
        return mGenres;
    }

    /**
     * @param size one of the specified size's above e.g. SIZE_W185
     * @param path backdropPath or posterPath
     * @return complete url where the desired image can be found
     */
    private String getImageUrl(String size, String path) {
        if (!Arrays.asList(AVAILABLE_SIZES).contains(size)) {
            throw new NullPointerException();
        }
        return IMAGE_URL + size + "/" + path;
    }

    /**
     * @return complete url where the movies backdrop image can be found with the original size
     */
    public String getBackdropPath() {
        return getBackdropPath(SIZE_ORIGINAL);
    }

    /**
     * @param size one of the specified sizes at the top of the file e.g. SIZE_W185
     * @return complete url where the movies backdrop image can be be found
     */
    public String getBackdropPath(String size) {
        return getImageUrl(size, mBackdropPath);
    }

    /**
     * @return complete url where the movies poster image can be found with the original size
     */
    public String getPosterPath() {
        return getPosterPath(SIZE_ORIGINAL);
    }

    /**
     * @param size one of the specified  at the top of the file above e.g. SIZE_W185
     * @return complete url where the movies poster image can be found
     */
    public String getPosterPath(String size) {
        return getImageUrl(size, mPosterPath);
    }

    /**
     * @return original title of the movie
     */
    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    /**
     * @return original language of the movie
     */
    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    /**
     * @return overview of the movie
     */
    public String getOverview() {
        return mOverview;
    }

    /**
     * @return release date of the movie
     */
    public String getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * @return vote average of the movie
     */
    public String getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * @return vote count of the movie
     */
    public String getVoteCount() {
        return mVoteCount;
    }

    /**
     * @return popularity of the movie
     */
    public double getPopularity() {
        return mPopularity;
    }

    private Movie(Parcel in) {
        mId = in.readInt();
        mGenres = in.createIntArray();
        mBackdropPath = in.readString();
        mPosterPath = in.readString();
        mOriginalTitle = in.readString();
        mOriginalLanguage = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mVoteAverage = in.readString();
        mVoteCount = in.readString();
        mPopularity = in.readDouble();
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeIntArray(getGenres());
        parcel.writeString(getBackdropPath(SIZE_W780));
        parcel.writeString(getPosterPath(SIZE_W342));
        parcel.writeString(getOriginalTitle());
        parcel.writeString(getOriginalLanguage());
        parcel.writeString(getOverview());
        parcel.writeString(getReleaseDate());
        parcel.writeString(getVoteAverage());
        parcel.writeString(getVoteCount());
        parcel.writeDouble(getPopularity());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
