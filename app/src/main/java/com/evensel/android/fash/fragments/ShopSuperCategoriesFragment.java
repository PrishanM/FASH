package com.evensel.android.fash.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evensel.android.fash.R;

/**
 * Created by Prishan Maduka on 6/26/2016.
 */
public class ShopSuperCategoriesFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shop_supercategory_main_layout, container, false);
        return rootView;
    }
}