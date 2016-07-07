package com.evensel.android.fash.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.R;
import com.evensel.android.fash.ShopActivity;
import com.evensel.android.fash.commons.AppConstants;
import com.evensel.android.fash.util.ShopDetail;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 7/5/2016.
 */
public class HomeShopListAdapter extends BaseAdapter {

    HashMap<Integer,List<ShopDetail>> listHashMap;
    Context con;

    public HomeShopListAdapter(Context context,HashMap<Integer,List<ShopDetail>> listHashMap){

        this.listHashMap = listHashMap;
        this.con = context;
    }

    @Override
    public int getCount() {
        return listHashMap.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = null;
        final int posi = position;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) con
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.home_shop_row, null);
        }

        final ImageView imageView1 = (ImageView)convertView.findViewById(R.id.imgView1);
        final ImageView imageView2 = (ImageView)convertView.findViewById(R.id.imgView2);
        final ImageView imageView3 = (ImageView)convertView.findViewById(R.id.imgView3);
        final ImageView imageView4 = (ImageView)convertView.findViewById(R.id.imgView4);

        CardView layout1 = (CardView)convertView.findViewById(R.id.layout1);
        CardView layout2 = (CardView)convertView.findViewById(R.id.layout2);
        CardView layout3 = (CardView)convertView.findViewById(R.id.layout3);
        CardView layout4 = (CardView)convertView.findViewById(R.id.layout4);

        int arraySize = listHashMap.get(position).size();

        if(arraySize>=1){
            layout1.setVisibility(View.VISIBLE);
            imageLoader.get(listHashMap.get(position).get(0).getLogo(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERR", "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        imageView1.setImageBitmap(response.getBitmap());
                    }
                }
            });
        }
        if(arraySize>=2){
            layout2.setVisibility(View.VISIBLE);
            imageLoader.get(listHashMap.get(position).get(1).getLogo(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERR", "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        imageView2.setImageBitmap(response.getBitmap());
                    }
                }
            });
        }
        if(arraySize>=3){
            layout3.setVisibility(View.VISIBLE);
            imageLoader.get(listHashMap.get(position).get(2).getLogo(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERR", "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        imageView3.setImageBitmap(response.getBitmap());
                    }
                }
            });
        }
        if(arraySize==4){
            layout4.setVisibility(View.VISIBLE);
            imageLoader.get(listHashMap.get(position).get(3).getLogo(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERR", "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        imageView4.setImageBitmap(response.getBitmap());
                    }
                }
            });
        }

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.setShopDetail(listHashMap.get(position).get(0));
                Intent shopActivity1 = new Intent(con,ShopActivity.class);
                con.startActivity(shopActivity1);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.setShopDetail(listHashMap.get(position).get(1));
                Intent shopActivity2 = new Intent(con,ShopActivity.class);
                con.startActivity(shopActivity2);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.setShopDetail(listHashMap.get(position).get(2));
                Intent shopActivity3 = new Intent(con,ShopActivity.class);
                con.startActivity(shopActivity3);
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.setShopDetail(listHashMap.get(position).get(3));
                Intent shopActivity4 = new Intent(con,ShopActivity.class);
                con.startActivity(shopActivity4);
            }
        });

        return convertView;
    }
}
