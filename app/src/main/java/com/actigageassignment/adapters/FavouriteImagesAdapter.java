package com.actigageassignment.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.actigageassignment.ApplicationLoader;
import com.actigageassignment.R;
import com.actigageassignment.database.Favourite;
import com.actigageassignment.database.FavouriteDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santo on 1/1/18.
 */

public class FavouriteImagesAdapter extends RecyclerView.Adapter<FavouriteImagesAdapter.ViewHolder> {

    private Context context;
    private List<Favourite> favouriteList;
    private LayoutInflater inflater;

    public FavouriteImagesAdapter(Context context) {
        this.context = context;
        favouriteList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    @Override
    public FavouriteImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavouriteImagesAdapter.ViewHolder(inflater.inflate(R.layout.gallery_grid_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            final Favourite favourite = favouriteList.get(position);
            if (favourite != null) {
                Drawable image = new BitmapDrawable(context.getResources()
                        , BitmapFactory.decodeByteArray(favourite.getLogoBytes(), 0, favourite.getLogoBytes().length));
                holder.imageView.setImageDrawable(image);

                // Delete selected image from db and refresh list
                holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        FavouriteDao favouriteDao = ((ApplicationLoader) context.getApplicationContext())
                                .getDaoSession()
                                .getFavouriteDao();
                        favouriteDao.delete(favourite);
                        updateFeatures(favouriteDao.queryBuilder().list());
                        Toast.makeText(context, "Image deleted", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    public void updateFeatures(List<Favourite> favouriteList) {
        this.favouriteList.clear();
        this.favouriteList.addAll(favouriteList);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }
}
