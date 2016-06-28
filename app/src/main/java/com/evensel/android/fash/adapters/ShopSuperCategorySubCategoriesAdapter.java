package com.evensel.android.fash.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.R;
import com.evensel.android.fash.util.HomeSuperCategory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 6/27/2016.
 */
public class ShopSuperCategorySubCategoriesAdapter extends BaseAdapter{

    HashMap<Integer,List<HomeSuperCategory>> categoryMap;
    Context con;

    public ShopSuperCategorySubCategoriesAdapter(HashMap<Integer,List<HomeSuperCategory>> map, Context context){
        this.categoryMap = map;
        this.con=context;
    }

    @Override
    public int getCount() {
        return categoryMap.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = null;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) con
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.super_category_sub_category_layout, null);
        }

        final ImageView img1 = (ImageView)convertView.findViewById(R.id.imgView1);
        final ImageView img2 = (ImageView)convertView.findViewById(R.id.imgView2);
        final ImageView img3 = (ImageView)convertView.findViewById(R.id.imgView3);

        TextView txtName1 = (TextView)convertView.findViewById(R.id.txtName1);
        TextView txtName2 = (TextView)convertView.findViewById(R.id.txtName2);
        TextView txtName3 = (TextView)convertView.findViewById(R.id.txtName3);

        LinearLayout layout1 = (LinearLayout)convertView.findViewById(R.id.layout1);
        LinearLayout layout2 = (LinearLayout)convertView.findViewById(R.id.layout2);
        LinearLayout layout3 = (LinearLayout)convertView.findViewById(R.id.layout3);

        int arraySize = categoryMap.get(position).size();

        if(arraySize>=1){
            layout1.setVisibility(View.VISIBLE);
            imageLoader.get(categoryMap.get(position).get(0).getImage(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERR", "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        img1.setImageBitmap(response.getBitmap());
                    }
                }
            });
            txtName1.setText(categoryMap.get(position).get(0).getName());
        }

        if(arraySize>=2){
            layout2.setVisibility(View.VISIBLE);
            imageLoader.get(categoryMap.get(position).get(1).getImage(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERR", "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        img2.setImageBitmap(response.getBitmap());
                    }
                }
            });
            txtName2.setText(categoryMap.get(position).get(1).getName());
        }

        if(arraySize==3){
            layout3.setVisibility(View.VISIBLE);
            imageLoader.get(categoryMap.get(position).get(2).getImage(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERR", "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        img3.setImageBitmap(response.getBitmap());
                    }
                }
            });
            txtName3.setText(categoryMap.get(position).get(2).getName());
        }

        return convertView;
    }
}