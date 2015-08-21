package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ImageAdapter mMoviesAdapter;

    public MainActivityFragment() {
    }

    private String[] mImageUrls = {
        "https://i.imgur.com/7gnUdfU.jpg",
        "https://i.imgur.com/NAjPBsa.jpg",
        "http://i.imgur.com/WHe0Yfw.png",
        "https://i.imgur.com/zpCdbSo.jpg",
        "https://i.imgur.com/ZpfAWXh.jpg",
        "https://i.imgur.com/x6nWQ5x.jpg",
        "https://i.imgur.com/QxzTjP1.jpg",
        "https://i.imgur.com/gG7zP4p.jpg",
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mMoviesAdapter = new ImageAdapter(getActivity(), new ArrayList<String>(Arrays.asList(mImageUrls)));
        GridView gridview = (GridView) rootView.findViewById(R.id.grid_view);
        gridview.setAdapter(mMoviesAdapter);

        return rootView;
    }
}
