package com.evensel.android.fash.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.evensel.android.fash.ProductsActivity;
import com.evensel.android.fash.R;
import com.evensel.android.fash.util.Category;

import java.util.HashMap;
import java.util.List;

/**
 * @author Prishanm
 * Used for Listview in categories of Single shop
 */
public class ShopCategoriesAdapter extends BaseAdapter {

    HashMap<Integer,List<Category>> listHashMap;
    Context con;
    int shopId;
    String shopName;

    public ShopCategoriesAdapter(HashMap<Integer,List<Category>> shops,Context context,int shopId,String shop){
        this.con = context;
        this.listHashMap = shops;
        this.shopId = shopId;
        this.shopName = shop;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = null;
        final int posi = position;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) con
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.shop_fragment, null);
        }

        final ImageView img1 = (ImageView)convertView.findViewById(R.id.imgView1);
        final ImageView img2 = (ImageView)convertView.findViewById(R.id.imgView2);
        final ImageView img3 = (ImageView)convertView.findViewById(R.id.imgView3);

        TextView txtCategory1 = (TextView)convertView.findViewById(R.id.txtCategory1);
        TextView txtCategory2 = (TextView)convertView.findViewById(R.id.txtCategory2);
        TextView txtCategory3 = (TextView)convertView.findViewById(R.id.txtCategory3);

        LinearLayout layout1 = (LinearLayout)convertView.findViewById(R.id.layout1);
        LinearLayout layout2 = (LinearLayout)convertView.findViewById(R.id.layout2);
        LinearLayout layout3 = (LinearLayout)convertView.findViewById(R.id.layout3);

        int arraySize = listHashMap.get(position).size();

        if(arraySize>=1){
            layout1.setVisibility(View.VISIBLE);
            imageLoader.get(listHashMap.get(position).get(0).getImage(), new ImageLoader.ImageListener() {

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
            txtCategory1.setText(listHashMap.get(position).get(0).getName());
        }
        if(arraySize>=2){
            layout2.setVisibility(View.VISIBLE);
            imageLoader.get(listHashMap.get(position).get(1).getImage(), new ImageLoader.ImageListener() {

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
            txtCategory2.setText(listHashMap.get(position).get(1).getName());
        }
        if(arraySize==3){
            layout3.setVisibility(View.VISIBLE);
            imageLoader.get(listHashMap.get(position).get(2).getImage(), new ImageLoader.ImageListener() {

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
            txtCategory3.setText(listHashMap.get(position).get(2).getName());
        }

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productsActivity1 = new Intent(con,ProductsActivity.class);
                productsActivity1.putExtra("ID", listHashMap.get(posi).get(0).getId());
                productsActivity1.putExtra("TITLE",listHashMap.get(posi).get(0).getName());
                productsActivity1.putExtra("SHOP_ID",shopId);
                productsActivity1.putExtra("SHOP_NAME",shopName);
                con.startActivity(productsActivity1);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productsActivity2 = new Intent(con,ProductsActivity.class);
                productsActivity2.putExtra("ID", listHashMap.get(posi).get(1).getId());
                productsActivity2.putExtra("TITLE",listHashMap.get(posi).get(1).getName());
                productsActivity2.putExtra("SHOP_ID",shopId);
                productsActivity2.putExtra("SHOP_NAME",shopName);
                con.startActivity(productsActivity2);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productsActivity3 = new Intent(con,ProductsActivity.class);
                productsActivity3.putExtra("ID", listHashMap.get(posi).get(2).getId());
                productsActivity3.putExtra("TITLE",listHashMap.get(posi).get(2).getName());
                productsActivity3.putExtra("SHOP_ID",shopId);
                productsActivity3.putExtra("SHOP_NAME",shopName);
                con.startActivity(productsActivity3);
            }
        });



        return convertView;
    }
}
