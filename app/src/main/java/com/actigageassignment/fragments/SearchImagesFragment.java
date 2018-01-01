package com.actigageassignment.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.actigageassignment.R;
import com.actigageassignment.models.Gallery;
import com.actigageassignment.utilities.CustomEditText;
import com.actigageassignment.utilities.CustomTextView;
import com.actigageassignment.utilities.Utility;
import com.actigageassignment.webservices.FetchImagesTask;
import com.actigageassignment.webservices.WebServiceConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by santo on 1/1/18.
 */

public class SearchImagesFragment extends Fragment {

    private ViewPager viewPager;
    private GallaeyPagerAdapter pagerAdapter;
    private List<TaskListener> listeners;
    private CustomTextView grid_tab, list_tab;
    private CustomEditText search_box;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_layout, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        search_box = (CustomEditText) view.findViewById(R.id.search_box);
        pagerAdapter = new GallaeyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        grid_tab = (CustomTextView) view.findViewById(R.id.grid_view);
        list_tab = (CustomTextView) view.findViewById(R.id.list_view);
        final GradientDrawable grid_tab_background = (GradientDrawable) grid_tab.getBackground();
        final GradientDrawable list_tab_background = (GradientDrawable) list_tab.getBackground();
        grid_tab_background.setColor(getResources().getColor(R.color.blue));
        list_tab_background.setColor(Color.WHITE);
        list_tab.setTextColor(getResources().getColor(R.color.blue));

        search_box.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (search_box.getText().toString().isEmpty())
                        Toast.makeText(getActivity(), "Search field can't be empty", Toast.LENGTH_SHORT).show();
                    else
                        getFeatures(search_box.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
        grid_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        list_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        grid_tab_background.setColor(getResources().getColor(R.color.blue));
                        grid_tab.setTextColor(Color.WHITE);
                        list_tab_background.setColor(Color.WHITE);
                        list_tab.setTextColor(getResources().getColor(R.color.blue));
                        break;
                    case 1:
                        grid_tab_background.setColor(Color.WHITE);
                        grid_tab.setTextColor(getResources().getColor(R.color.blue));
                        list_tab.setTextColor(Color.WHITE);
                        list_tab_background.setColor(getResources().getColor(R.color.blue));
                        break;
                    default:
                        grid_tab_background.setColor(Color.BLUE);
                        list_tab_background.setColor(Color.WHITE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        listeners = new ArrayList<>();
        getFeatures("Android");
        return view;
    }

    public void setListener(TaskListener taskListener) {
        listeners.add(taskListener);
    }

    private void getFeatures(String query) {
        try {
            if (Utility.isNetwork(getActivity())) {
                HashMap<String, String> paramsData = new HashMap<>();
                paramsData.put("method", WebServiceConstant.SEARCH_IMAGE);
                paramsData.put("api_key", this.getResources().getString(R.string.flickr_api));
                paramsData.put("text", query);
                paramsData.put("extras", "url_s");
                FetchImagesTask imagesTask = new FetchImagesTask(fetchImagesListener, paramsData);
                imagesTask.execute();
            } else {
                Toast.makeText(getActivity(), "No network connection!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }

    public interface TaskListener {
        void onSucces(List<Gallery> galleryList);
    }

    FetchImagesTask.FetchImagesListener fetchImagesListener = new FetchImagesTask.FetchImagesListener() {

        @Override
        public void onSuccess(List<Gallery> repo) {
            for (TaskListener taskListener : listeners)
                taskListener.onSucces(repo);
        }

        @Override
        public void onError() {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    };


    private class GallaeyPagerAdapter extends FragmentStatePagerAdapter {

        public GallaeyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new GalleryGridFragment();
                case 1:
                    return new GalleryListFragment();
                default:
                    return new GalleryGridFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
