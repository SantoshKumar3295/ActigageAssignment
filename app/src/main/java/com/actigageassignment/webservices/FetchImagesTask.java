package com.actigageassignment.webservices;

import android.os.AsyncTask;
import android.util.Log;

import com.actigageassignment.models.Gallery;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by santo on 30/12/17.
 */

public class FetchImagesTask extends AsyncTask<Void, Void, List<Gallery>> {

    private FetchImagesListener listListener;
    private HashMap<String, String> params_data;

    public FetchImagesTask(FetchImagesListener listListener, HashMap params_data) {
        this.listListener = listListener;
        this.params_data = params_data;
    }

    @Override
    protected List<Gallery> doInBackground(Void... params) {

        try {
            String response = WebUtils.getServiceData(WebServiceConstant.SERVICE_BASE_URL, params_data);
            Log.d("tag", response);
            if (response != null) {
                JSONObject jsonObject = new JSONObject(XML.toJSONObject(response).toString());
                if (jsonObject != null && jsonObject.has("rsp")) {
                    jsonObject = jsonObject.getJSONObject("rsp");
                    if (jsonObject.has("stat") && jsonObject.getString("stat").equals("ok")) {
                        if (jsonObject.has("photos")) {
                            jsonObject = jsonObject.getJSONObject("photos");
                            JSONArray photos = jsonObject.has("photo") ? jsonObject.getJSONArray("photo") : null;
                            if (photos != null && photos.length() > 0) {
                                List<Gallery> list = new ArrayList<>();
                                for (int i = 0; i < photos.length(); i++) {
                                    jsonObject = photos.getJSONObject(i);
                                    if (jsonObject.has("url_s")) {
                                        Gallery gallery = new Gallery();
                                        gallery.setImageUrl(jsonObject.getString("url_s"));
                                        list.add(gallery);
                                    }
                                }
                                return list;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d("tag", "Exception in parsing");
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Gallery> list) {
        super.onPostExecute(list);

        if (list != null && list.size() > 0) {
            listListener.onSuccess(list);
            Log.d("tag", list.toString());

        } else {
            listListener.onError();
        }
    }

    public interface FetchImagesListener {

        void onSuccess(List<Gallery> repo);

        void onError();
    }

}

