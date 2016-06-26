package com.evensel.android.fash.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.ImageViewerActivity;
import com.evensel.android.fash.R;
import com.evensel.android.fash.commons.Notifications;

/**
 * @author Prishanm
 * Fragment used for view pager in single product
 */
public class SingleProductsFragment extends Fragment{

    ImageView imgView1;
    String imageURL;
    Notifications notifications;
    int deviceWidth,deviceHeight;
    ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.single_product_fragment, container, false);
        notifications = new Notifications();
        imageLoader = AppController.getInstance().getImageLoader();
        imgView1 = (ImageView) rootView.findViewById(R.id.imgView1);

        imageURL = getArguments().getString("URL");



        // getting devices' width and height
        Display mDisplay= getActivity().getWindowManager().getDefaultDisplay();
        deviceWidth= mDisplay.getWidth();
        deviceHeight= mDisplay.getHeight();

        setData();

        imgView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newActivity = new Intent(getActivity(), ImageViewerActivity.class);
                newActivity.putExtra("URL",getArguments().getString("URL"));
                newActivity.putExtra("POSITION",getArguments().getInt("POSITION"));
                newActivity.putStringArrayListExtra("ALL", getArguments().getStringArrayList("ALL"));
                getActivity().startActivity(newActivity);

            }
        });

        return rootView;
    }

    private void setData() {

        imageLoader.get(imageURL, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERR", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    imgView1.setImageBitmap(response.getBitmap());
                }
            }
        });
    }

}
