package com.evensel.android.fash.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.evensel.android.fash.R;

/**
 * Created by Prishan Maduka on 7/15/2016.
 */
public class TestFragment extends Fragment {

    ImageView imgView1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.single_product_fragment, container, false);
        imgView1 = (ImageView) rootView.findViewById(R.id.imgView1);

        imgView1.setImageResource(R.drawable.blouses);

        return rootView;
    }
}
