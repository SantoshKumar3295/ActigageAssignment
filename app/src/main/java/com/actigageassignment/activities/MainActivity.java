package com.actigageassignment.activities;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.actigageassignment.R;
import com.actigageassignment.fragments.FavouriteImagesFragment;
import com.actigageassignment.fragments.SearchImagesFragment;
import com.actigageassignment.utilities.CustomTextView;

import static com.actigageassignment.R.id.fav_txt;

/*
* Base Container Activity of all fragments
*/


public class MainActivity extends AppCompatActivity {

    private GradientDrawable fav_image_background;
    private GradientDrawable search_image_background;
    private CustomTextView fav_txt_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


    // Initialize all fragments and views
    private void initViews() {
        try {
            final LinearLayout fav_btn = (LinearLayout) findViewById(R.id.fav_btn);
            final LinearLayout search_btn = (LinearLayout) findViewById(R.id.search_btn);
            ImageView fav_image = (ImageView) findViewById(R.id.fav_image);
            fav_txt_view = (CustomTextView) findViewById(fav_txt);
            final ImageView search_image = (ImageView) findViewById(R.id.search_image);
            final CustomTextView search_txt = (CustomTextView) findViewById(R.id.search_txt);
            fav_image_background = (GradientDrawable) fav_image.getBackground();
            search_image_background = (GradientDrawable) search_image.getBackground();
            search_image_background.setColor(getResources().getColor(R.color.blue));
            fav_image_background.setColor(getResources().getColor(R.color.colorPrimaryDark));
            fav_txt_view.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            // Initiate SearchFragment when activity loads
            SearchImagesFragment fragment = new SearchImagesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.main_container, fragment).commit();

            fav_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fav_image_background.setColor(getResources().getColor(R.color.blue));
                    fav_txt_view.setTextColor(getResources().getColor(R.color.blue));
                    search_image_background.setColor(getResources().getColor(R.color.colorPrimaryDark));
                    search_txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    FavouriteImagesFragment fragment = new FavouriteImagesFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.main_container, fragment).commit();
                }
            });

            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fav_image_background.setColor(getResources().getColor(R.color.colorPrimaryDark));
                    fav_txt_view.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    search_image_background.setColor(getResources().getColor(R.color.blue));
                    search_txt.setTextColor(getResources().getColor(R.color.blue));
                    SearchImagesFragment fragment = new SearchImagesFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.main_container, fragment).commit();
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
