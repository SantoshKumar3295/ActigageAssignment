package com.actigageassignment.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actigageassignment.R;
import com.actigageassignment.adapters.GalleryAdapter;
import com.actigageassignment.models.Gallery;

import java.util.List;

/**
 * Created by santo on 30/12/17.
 */

public class GalleryListFragment extends Fragment {

    private GalleryAdapter galleryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        SearchImagesFragment parentFragment = (SearchImagesFragment) getParentFragment();

        // Set listener to update list adapter, when Network request is complete
        parentFragment.setListener(taskListener);
        View view = inflater.inflate(R.layout.gallery_layout, container, false);
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.gallery_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        listView.setLayoutManager(mLayoutManager);
        galleryAdapter = new GalleryAdapter(getActivity(), false);
        listView.setAdapter(galleryAdapter);
        return view;
    }

    // process adapter after getting result from request
    SearchImagesFragment.TaskListener taskListener = new SearchImagesFragment.TaskListener() {
        @Override
        public void onSucces(List<Gallery> galleryList) {
            galleryAdapter.updateFeatures(galleryList);
        }
    };
}
