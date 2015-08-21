package com.example.android.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mImageUrls;

    /**
     * Constructs a new ImageAdapter with Context context
     * @param context
     */
    public ImageAdapter(Context context) {
       this(context, new ArrayList<String>());
    }

    /**
     * Constructs a new ImageAdapter with Context context
     * and an ArrayList of type String with imageUrls
     * @param context
     * @param imageUrls an ArrayList of type String containing paths to image locations
     */
    public ImageAdapter(Context context, ArrayList<String> imageUrls) {
        mContext = context;
        mImageUrls = imageUrls;
    }

    /**
     * Clears the imageUrls ArrayList
     */
    public void clear() {
        mImageUrls.clear();
    }

    /**
     * Add an image URL to the adapter
     * @param imageUrl a string containing the full URL to the image location
     */
    public void add(String imageUrl) {
        mImageUrls.add(imageUrl);
        notifyDataSetChanged();
    }

    /**
     * @return how many image URls the adapter contains
     */
    public int getCount() {
        return mImageUrls.size();
    }

    /**
     * @param position
     * @return the image URL as a String
     */
    public String getItem(int position) {
        return mImageUrls.get(position);
    }

    /**
     * @param position
     * @return the image URLs position
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Find's the specific image URL with the following position
     * And loads the image into the specified view
     * @param position
     * @param convertView
     * @param parent
     * @return ImageView with image loaded into it
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        }

        String url = getItem(position);

        Picasso.with(mContext)
                .load(url)
                .into(imageView);

        return imageView;
    }
}
