package com.evensel.android.fash.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evensel.android.fash.R;

/**
 * Created by Prishan Maduka on 7/13/2016.
 */
public class MyProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_profile_layout, container, false);

        return rootView;
    }
}
