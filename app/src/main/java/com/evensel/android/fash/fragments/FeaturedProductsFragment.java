package com.evensel.android.fash.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.R;
import com.evensel.android.fash.SingleProductActivity;

import java.util.ArrayList;

/**
 * @author Prishanm
 * Fragment used for view pager in featured products
 */
public class FeaturedProductsFragment extends Fragment implements View.OnClickListener{

    ImageView imgView1,imgView2,imgView3;
    TextView title1,title2,title3,price1,price2,price3;
    LinearLayout layout1,layout2,layout3;
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> image = new ArrayList<String>();
    ArrayList<Integer> ids = new ArrayList<Integer>();
    ImageLoader imageLoader ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_fragment, container, false);

        imgView1 = (ImageView) rootView.findViewById(R.id.imgView1);
        imgView2 = (ImageView) rootView.findViewById(R.id.imgView2);
        imgView3 = (ImageView) rootView.findViewById(R.id.imgView3);

        title1 = (TextView)rootView.findViewById(R.id.txtTitle1);
        title2 = (TextView)rootView.findViewById(R.id.txtTitle2);
        title3 = (TextView)rootView.findViewById(R.id.txtTitle3);

        price1 = (TextView)rootView.findViewById(R.id.txtPrice1);
        price2 = (TextView)rootView.findViewById(R.id.txtPrice2);
        price3 = (TextView)rootView.findViewById(R.id.txtPrice3);

        layout1 = (LinearLayout)rootView.findViewById(R.id.layout1);
        layout2 = (LinearLayout)rootView.findViewById(R.id.layout2);
        layout3 = (LinearLayout)rootView.findViewById(R.id.layout3);


        /*imgView1.setOnClickListener(this);
        imgView2.setOnClickListener(this);
        imgView3.setOnClickListener(this);

        title = getArguments().getStringArrayList("TITLE");
        price = getArguments().getStringArrayList("PRICE");
        image = getArguments().getStringArrayList("IMAGE");
        ids = getArguments().getIntegerArrayList("ID");*/

        imageLoader = AppController.getInstance().getImageLoader();

        setData();
        return rootView;
    }

    private void setData() {
        for(int i=0;i<3;i++){
            if(i==0){
                /*setImageView(image.get(i).toString(), imgView1);
                title1.setText(title.get(i).toString());
                price1.setText(price.get(i).toString());*/
                imgView1.setImageResource(R.drawable.a);
                layout1.setVisibility(View.VISIBLE);
            }else if(i==1){
                /*setImageView(image.get(i).toString(), imgView2);
                title2.setText(title.get(i).toString());
                price2.setText(price.get(i).toString());*/
                imgView2.setImageResource(R.drawable.a);
                layout2.setVisibility(View.VISIBLE);
            }else if(i==2){
                /*setImageView(image.get(i).toString(), imgView3);
                title3.setText(title.get(i).toString());
                price3.setText(price.get(i).toString());*/
                imgView3.setImageResource(R.drawable.a);
                layout3.setVisibility(View.VISIBLE);
            }
        }

    }

    public void setImageView(String url, final ImageView imageView){
        imageLoader.get(url, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERR", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    imageView.setImageBitmap(response.getBitmap());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imgView1:
                Intent intent = new Intent(getActivity(), SingleProductActivity.class);
                intent.putExtra("ID",ids.get(0));
                startActivity(intent);
                break;
            case R.id.imgView2:
                Intent intent2 = new Intent(getActivity(), SingleProductActivity.class);
                intent2.putExtra("ID",ids.get(1));
                startActivity(intent2);
                break;
            case R.id.imgView3:
                Intent intent3 = new Intent(getActivity(), SingleProductActivity.class);
                intent3.putExtra("ID",ids.get(2));
                startActivity(intent3);
                break;
        }
    }
}
