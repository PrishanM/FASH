package com.evensel.android.fash.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.R;

/**
 * @author Prishanm
 * Used handle Single Products - Zoomed Images
 */
public class SingleImageFragment extends DialogFragment {

    ImageView imageView;
    ImageLoader imageLoader;
    String imageURL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.single_product_image_view, container, false);
        imageLoader = AppController.getInstance().getImageLoader();
        imageView = (ImageView) rootView.findViewById(R.id.fullimage);

        imageURL = getArguments().getString("URL");

        imageLoader.get(imageURL, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERR", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    imageView.setImageBitmap(response.getBitmap());
                }
            }
        });

        return rootView;
    }
}
