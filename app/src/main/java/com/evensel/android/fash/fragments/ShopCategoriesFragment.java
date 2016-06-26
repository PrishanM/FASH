package com.evensel.android.fash.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.evensel.android.fash.R;

/**
 * Created by Administrator on 5/24/2016.
 */
public class ShopCategoriesFragment extends Fragment {
    ImageView imgView1,imgView2,imgView3,imgView4,imgView5,imgView6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shop_fragment, container, false);
        return rootView;
    }
}
