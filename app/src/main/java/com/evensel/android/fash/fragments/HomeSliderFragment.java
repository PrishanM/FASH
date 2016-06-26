package com.evensel.android.fash.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
 * Created by prishanm on 6/20/2016.
 */
public class HomeSliderFragment extends Fragment {

    ImageView fullImage;
    ImageLoader imageLoader ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.single_product_image_view, container, false);

        fullImage = (ImageView)rootView.findViewById(R.id.fullimage);

        imageLoader = AppController.getInstance().getImageLoader();
        //setImageView(getArguments().getString("IMAGE"), fullImage);

        //Testing only
        if(Integer.parseInt(getArguments().getString("IMG"))%3==0){
            fullImage.setImageResource(R.drawable.slider1);
        }else if(Integer.parseInt(getArguments().getString("IMG"))%3==1){
            fullImage.setImageResource(R.drawable.slider2);
        }else{
            fullImage.setImageResource(R.drawable.slider3);
        }


        fullImage.setScaleType(ImageView.ScaleType.FIT_XY);

        return rootView;
    }

    public void setImageView(String url, final ImageView imageView){
        imageLoader.get(url, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERR", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    imageView.setImageBitmap(response.getBitmap());
                }
            }
        });
    }
}
