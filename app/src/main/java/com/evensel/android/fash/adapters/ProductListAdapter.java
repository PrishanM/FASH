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
import com.evensel.android.fash.R;
import com.evensel.android.fash.SingleProductActivity;
import com.evensel.android.fash.util.Datum;

import java.util.HashMap;
import java.util.List;

/**
 * @author Prishanm
 * Used for Listview in Products in single category
 */
public class ProductListAdapter extends BaseAdapter {

    HashMap<Integer,List<Datum>> listHashMap;
    Context con;

    public ProductListAdapter(HashMap<Integer,List<Datum>> shops,Context context){
        this.con = context;
        this.listHashMap = shops;
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
            convertView = mInflater.inflate(R.layout.product_list_row, null);
        }

        final ImageView img1 = (ImageView)convertView.findViewById(R.id.imgView1);
        final ImageView img2 = (ImageView)convertView.findViewById(R.id.imgView2);
        final ImageView img3 = (ImageView)convertView.findViewById(R.id.imgView3);

        TextView txtName1 = (TextView)convertView.findViewById(R.id.txtName1);
        TextView txtName2 = (TextView)convertView.findViewById(R.id.txtName2);
        TextView txtName3 = (TextView)convertView.findViewById(R.id.txtName3);

        TextView txtPrice1 = (TextView)convertView.findViewById(R.id.txtPrice1);
        TextView txtPrice2 = (TextView)convertView.findViewById(R.id.txtPrice2);
        TextView txtPrice3 = (TextView)convertView.findViewById(R.id.txtPrice3);

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
            txtName1.setText(listHashMap.get(position).get(0).getTitle());
            txtPrice1.setText("$ "+listHashMap.get(position).get(0).getPrice());
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
            txtName2.setText(listHashMap.get(position).get(1).getTitle());
            txtPrice2.setText("$ "+listHashMap.get(position).get(1).getPrice());
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
            txtName3.setText(listHashMap.get(position).get(2).getTitle());
            txtPrice3.setText("$ "+listHashMap.get(position).get(2).getPrice());
        }

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, SingleProductActivity.class);
                intent.putExtra("ID",listHashMap.get(posi).get(0).getId());
                con.startActivity(intent);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(con, SingleProductActivity.class);
                intent2.putExtra("ID",listHashMap.get(posi).get(1).getId());
                con.startActivity(intent2);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(con, SingleProductActivity.class);
                intent3.putExtra("ID",listHashMap.get(posi).get(2).getId());
                con.startActivity(intent3);
            }
        });

        return convertView;
    }
}
