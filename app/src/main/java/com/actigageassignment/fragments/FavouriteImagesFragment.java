package com.actigageassignment.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actigageassignment.ApplicationLoader;
import com.actigageassignment.R;
import com.actigageassignment.adapters.FavouriteImagesAdapter;

/**
 * Created by santo on 1/1/18.
 */

public class FavouriteImagesFragment extends Fragment {

    private FavouriteImagesAdapter imagesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite_images, container, false);

        RecyclerView listView = (RecyclerView) view.findViewById(R.id.gallery_list);
        listView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        imagesAdapter = new FavouriteImagesAdapter(getActivity());
        listView.setAdapter(imagesAdapter);

        // Load all the save image from db
        imagesAdapter.updateFeatures((ApplicationLoader.getInstance())
                .getDaoSession()
                .getFavouriteDao()
                .queryBuilder().list());

        return view;
    }
}
