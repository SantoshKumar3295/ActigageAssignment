package com.actigageassignment.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.actigageassignment.ApplicationLoader;
import com.actigageassignment.R;
import com.actigageassignment.database.Favourite;
import com.actigageassignment.models.Gallery;
import com.actigageassignment.utilities.DoubleClickListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santo on 30/12/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private List<Gallery> galleryList;
    private LayoutInflater inflater;
    private boolean isGrid;

    public GalleryAdapter(Context context, boolean type) {
        this.context = context;
        this.isGrid = type;
        galleryList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (!isGrid)
            return new GalleryAdapter.ViewHolder(inflater.inflate(R.layout.gallery_list_row, parent, false));
        else
            return new GalleryAdapter.ViewHolder(inflater.inflate(R.layout.gallery_grid_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            final Gallery gallery = galleryList.get(position);
            if (gallery != null) {
                Picasso instance = Picasso.with(context);
                instance.setIndicatorsEnabled(true);
                instance.load(gallery.getImageUrl())
                        .into(holder.imageView);

                //Start storing selected image to db
                holder.imageView.setOnClickListener(new DoubleClickListener() {
                    @Override
                    public void onDoubleClick(View v) {
                        downloadImage(gallery.getImageUrl());
                        Toast.makeText(context, "Image saved in your Favourite list", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } catch (Exception e) {
        }
    }

    // ### To store image in db

    // #Step -1 Download the image to store in db
    private void downloadImage(final String url) {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Bitmap logoBitmap;
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.connect();
                    final InputStream input = connection.getInputStream();
                    logoBitmap = BitmapFactory.decodeStream(input);

                    if (logoBitmap != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        logoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imageInByte = stream.toByteArray();
                        if (imageInByte != null) {
                        }
                        storeImage(imageInByte);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
        task.execute();
    }

    // Step - 2 : Store the downloaded image
    public void storeImage(byte[] imageByte) {
        Favourite favourite = new Favourite();
        favourite.setLogoBytes(imageByte);
        ((ApplicationLoader) context.getApplicationContext())
                .getDaoSession()
                .getFavouriteDao()
                .insert(favourite);
    }

    public void updateFeatures(List<Gallery> galleryList) {
        this.galleryList.clear();
        this.galleryList.addAll(galleryList);
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

