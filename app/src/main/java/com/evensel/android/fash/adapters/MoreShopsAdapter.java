package com.evensel.android.fash.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.R;
import com.evensel.android.fash.util.ShopDetail;

import java.util.List;

/**
 * @author Prishanm
 * Used for Listview in MORE SHOPS
 */
public class MoreShopsAdapter extends BaseAdapter {

    List<ShopDetail> shopList;
    Context con;

    public MoreShopsAdapter(List<ShopDetail> shops,Context context){
        this.con = context;
        this.shopList = shops;
    }

    @Override
    public int getCount() {
        return shopList.size();
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
        final int posi = position;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) con
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.shops_row, null);
        }

        final ImageView img1 = (ImageView)convertView.findViewById(R.id.imgView1);
        final TextView txtShopName = (TextView)convertView.findViewById(R.id.txtShopName);
        final TextView txtShopSlogan = (TextView)convertView.findViewById(R.id.txtShopSlogan);

        if(!shopList.get(position).getLogo().isEmpty()){
            img1.setVisibility(View.VISIBLE);

            imageLoader.get(shopList.get(position).getLogo(), new ImageLoader.ImageListener() {

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
        }

        txtShopName.setText(shopList.get(position).getName());
        txtShopSlogan.setText(shopList.get(position).getSlogan());

        return convertView;
    }
}
