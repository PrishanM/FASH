package com.evensel.android.fash.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.MoreShopsActivity;
import com.evensel.android.fash.R;
import com.evensel.android.fash.adapters.HomeShopListAdapter;
import com.evensel.android.fash.adapters.PagerAdapter;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.network.DetectNetwork;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.FeaturedProduct;
import com.evensel.android.fash.util.ShopDetail;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 6/20/2016.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private ViewPager mViewPager = null;
    private CirclePageIndicator circlePageIndicator = null;
    private PagerAdapter pagerAdapter;
    HomeShopListAdapter homeShopListAdapter;
    List<Fragment> fList = null;
    ListView listView;
    FloatingActionButton btnMore;
    Notifications notifications;
    AlertDialog alertDialog;
    View loadingSpinnerProducts,loadingSpinnerShops,rootView;
    List<ShopDetail> dataList = new ArrayList<ShopDetail>();
    ImageLoader imageLoader ;
    private Handler mHandler = new Handler();
    int sliderPosition=0;
    public static final int DELAY = 3000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_layout, container, false);

        notifications = new Notifications();
        imageLoader = AppController.getInstance().getImageLoader();

        initUI();

        DetectNetwork.setmContext(getActivity());

        //Check network connectivity
        if(DetectNetwork.isConnected()){
            getFeaturedProducts();
        }else{
            alertDialog = notifications.showNetworkNotification(getActivity());
            alertDialog.show();
        }

        return rootView;
    }

    private void initUI() {
        mViewPager = (ViewPager)rootView.findViewById(R.id.featuredViewPager);
        loadingSpinnerProducts = (View)rootView.findViewById(R.id.loadingSpinnerProducts);
        loadingSpinnerShops = (View)rootView.findViewById(R.id.loadingSpinnerShops);
        listView = (ListView)rootView.findViewById(R.id.shopListView);

        /*imageView1 = (ImageView)rootView.findViewById(R.id.imgView1);
        imageView2 = (ImageView)rootView.findViewById(R.id.imgView2);
        imageView3 = (ImageView)rootView.findViewById(R.id.imgView3);
        imageView4 = (ImageView)rootView.findViewById(R.id.imgView4);
        imageView5 = (ImageView)rootView.findViewById(R.id.imgView5);
        imageView6 = (ImageView)rootView.findViewById(R.id.imgView6);
        imageView7 = (ImageView)rootView.findViewById(R.id.imgView7);
        imageView8 = (ImageView)rootView.findViewById(R.id.imgView8);

        layout1 = (CardView)rootView.findViewById(R.id.layout1);
        layout2 = (CardView)rootView.findViewById(R.id.layout2);
        layout3 = (CardView)rootView.findViewById(R.id.layout3);
        layout4 = (CardView)rootView.findViewById(R.id.layout4);
        layout5 = (CardView)rootView.findViewById(R.id.layout5);
        layout6 = (CardView)rootView.findViewById(R.id.layout6);
        layout7 = (CardView)rootView.findViewById(R.id.layout7);
        layout8 = (CardView)rootView.findViewById(R.id.layout8);*/

        btnMore = (FloatingActionButton)rootView.findViewById(R.id.fabMoreShops);

        /*imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);*/
        btnMore.setOnClickListener(this);
    }

    //Execute Http request call for "FEATURED PRODUCTS"
    private void getFeaturedProducts() {
        loadingSpinnerProducts.setVisibility(View.VISIBLE);

        JsonRequestManager.getInstance(getActivity()).getFeaturedProducts(getResources().getString(R.string.base_url) + getResources().getString(R.string.featured_products_url),"0", featuredProductsRequestCallback);
    }

    //Execute Http request call for "FEATURED SHOPS"
    private void getFeaturedShops() {
        loadingSpinnerShops.setVisibility(View.VISIBLE);
        /*
         * Commented in testing
         */
        //JsonRequestManager.getInstance(getActivity()).getFeaturedShops(getResources().getString(R.string.base_url) + getResources().getString(R.string.featured_shops_url), featuredShopsRequestCallback);
        JsonRequestManager.getInstance(getActivity()).getAllShops(getResources().getString(R.string.base_url) + getResources().getString(R.string.featured_shops_url), featuredShopsRequestCallback);
    }

    //Response callback for "FEATURED PRODUCTS"
    JsonRequestManager.getFeaturedProductsRequest featuredProductsRequestCallback = new JsonRequestManager.getFeaturedProductsRequest() {
        @Override
        public void onSuccess(List<FeaturedProduct> list) {
            loadingSpinnerProducts.setVisibility(View.GONE);
            //if(list.size()>0)
                setViewPagerFragments(list);

            getFeaturedShops();

        }

        @Override
        public void onError(String status) {
            loadingSpinnerProducts.setVisibility(View.GONE);
        }
    };

    //Response callback for "FEATURED SHOPS"
    JsonRequestManager.getAllShopsRequest featuredShopsRequestCallback = new JsonRequestManager.getAllShopsRequest() {
        @Override
        public void onSuccess(List<ShopDetail> featuredShops) {
            loadingSpinnerShops.setVisibility(View.GONE);
            if(featuredShops.size()>0) {
                dataList = featuredShops;
                setShopsData(featuredShops);
            }

        }

        @Override
        public void onError(String status) {
            loadingSpinnerShops.setVisibility(View.GONE);
        }
    };

    //Set featured shops data
    private void setShopsData(List<ShopDetail> data) {

        HashMap<Integer,List<ShopDetail>> listHashMap = new HashMap<Integer,List<ShopDetail>>();
        int arraySize = (int)Math.ceil(data.size()/4.0);

        for (int i=0;i<arraySize;i++){
            int position = (i*4);
            List<ShopDetail> tmpList = new ArrayList<ShopDetail>();

            if((position+0)<data.size()){
                tmpList.add(data.get(position+0));
            }
            if((position+1)<data.size()){
                tmpList.add(data.get(position+1));
            }
            if((position+2)<data.size()){
                tmpList.add(data.get(position+2));
            }
            if((position+3)<data.size()){
                tmpList.add(data.get(position+3));
            }
            listHashMap.put(i, tmpList);
        }

        homeShopListAdapter = new HomeShopListAdapter(getActivity(),listHashMap);
        listView.setAdapter(homeShopListAdapter);

        /*if(data.size()>0){
            for (int i=0;i<data.size();i++){
                switch (i){
                    case 0:
                        setImageView(data.get(i).getLogo(), imageView1);
                        layout1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setImageView(data.get(i).getLogo(),imageView2);
                        layout2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setImageView(data.get(i).getLogo(),imageView3);
                        layout3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        setImageView(data.get(i).getLogo(),imageView4);
                        layout4.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        setImageView(data.get(i).getLogo(),imageView5);
                        layout5.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        setImageView(data.get(i).getLogo(),imageView6);
                        layout6.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        setImageView(data.get(i).getLogo(), imageView7);
                        layout7.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        setImageView(data.get(i).getLogo(),imageView8);
                        layout8.setVisibility(View.VISIBLE);
                        break;
                }


            }
        }*/

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

    //Set view pager fragments - featured products
    private void setViewPagerFragments(List<FeaturedProduct> list) {

        HomeSliderFragment productsFragment;
        fList = new ArrayList<Fragment>();

        int arraySize = 7;//list.size();

        for (int i=0;i<arraySize;i++){
            ArrayList<String> dataTitle = new ArrayList<String>();
            ArrayList<String> dataPrice = new ArrayList<String>();
            ArrayList<String> dataImage = new ArrayList<String>();
            ArrayList<Integer> dataID = new ArrayList<Integer>();

            int position = (i*3);

            /*try {
                if(list.get(position+0)!=null) {

                    dataTitle.add(list.get(position + 0).getTitle());
                    dataPrice.add(list.get(position + 0).getPrice());
                    dataImage.add(list.get(position + 0).getImage());
                    dataID.add(list.get(position+0).getId());
                }
                if(list.get(position+1)!=null) {
                    dataTitle.add(list.get(position + 1).getTitle());
                    dataPrice.add(list.get(position + 1).getPrice());
                    dataImage.add(list.get(position + 1).getImage());
                    dataID.add(list.get(position+1).getId());
                }
                if(list.get(position+2)!=null) {
                    dataTitle.add(list.get(position + 2).getTitle());
                    dataPrice.add(list.get(position + 2).getPrice());
                    dataImage.add(list.get(position + 2).getImage());
                    dataID.add(list.get(position+2).getId());
                }
            }catch (IndexOutOfBoundsException e){

            }*/

            Bundle args = new Bundle();
            /*args.putStringArrayList("TITLE", dataTitle);
            args.putStringArrayList("IMAGE", dataImage);
            args.putStringArrayList("PRICE",dataPrice);
            args.putIntegerArrayList("ID", dataID);*/
            args.putString("IMG",(i+1)+"");
            productsFragment = new HomeSliderFragment();
            productsFragment.setArguments(args);
            fList.add(productsFragment);
        }

        List<Fragment> fragments = getFragments();
        if (fragments != null) {
            pagerAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
            setViewPager();
        }

    }

    //Set view pager
    private void setViewPager() {
        mViewPager.setAdapter(pagerAdapter);
        circlePageIndicator = (CirclePageIndicator)rootView.findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(mViewPager);
        circlePageIndicator.setFillColor(Color.rgb(130, 133, 133));
        circlePageIndicator.setPageColor(Color.WHITE);
        circlePageIndicator.setStrokeWidth(0.0f);

        /**
         * on swiping the viewpager make respective indicator selected
         * */
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                circlePageIndicator.setCurrentItem(position);
                sliderPosition = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        //mHandler.postDelayed(ViewPagerVisibleScroll, DELAY);
    }

    Runnable ViewPagerVisibleScroll= new Runnable() {
        @Override
        public void run() {
            try {
                if(sliderPosition <= 6){
                    mViewPager.setCurrentItem(sliderPosition, true);
                    mHandler.postDelayed(ViewPagerVisibleScroll, DELAY);
                    sliderPosition++;
                }else{
                    sliderPosition = 0;
                    mViewPager.setCurrentItem(sliderPosition, true);
                    mHandler.postDelayed(ViewPagerVisibleScroll, DELAY);
                }
            }catch (NullPointerException e){

            }

        }
    };


    private List<Fragment> getFragments() {
        return fList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabMoreShops:
                Intent moreShopsIntent = new Intent(getActivity(),MoreShopsActivity.class);
                startActivity(moreShopsIntent);
                break;
            /*case R.id.imgView1:
                AppConstants.setShopDetail(dataList.get(0));
                Intent shopActivity1 = new Intent(getActivity(),ShopActivity.class);
                startActivity(shopActivity1);
                break;
            case R.id.imgView2:
                AppConstants.setShopDetail(dataList.get(1));
                Intent shopActivity2 = new Intent(getActivity(),ShopActivity.class);
                startActivity(shopActivity2);
                break;
            case R.id.imgView3:
                AppConstants.setShopDetail(dataList.get(2));
                Intent shopActivity3 = new Intent(getActivity(),ShopActivity.class);
                startActivity(shopActivity3);
                break;
            case R.id.imgView4:
                AppConstants.setShopDetail(dataList.get(3));
                Intent shopActivity4 = new Intent(getActivity(),ShopActivity.class);
                startActivity(shopActivity4);
                break;
            case R.id.imgView5:
                AppConstants.setShopDetail(dataList.get(4));
                Intent shopActivity5 = new Intent(getActivity(),ShopActivity.class);
                startActivity(shopActivity5);
                break;
            case R.id.imgView6:
                AppConstants.setShopDetail(dataList.get(5));
                Intent shopActivity6 = new Intent(getActivity(),ShopActivity.class);
                startActivity(shopActivity6);
                break;
            case R.id.imgView7:
                AppConstants.setShopDetail(dataList.get(6));
                Intent shopActivity7 = new Intent(getActivity(),ShopActivity.class);
                startActivity(shopActivity7);
                break;
            case R.id.imgView8:
                AppConstants.setShopDetail(dataList.get(7));
                Intent shopActivity8 = new Intent(getActivity(),ShopActivity.class);
                startActivity(shopActivity8);
                break;*/
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mHandler!= null) {
            mHandler.removeCallbacks(ViewPagerVisibleScroll);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(ViewPagerVisibleScroll, DELAY);

    }
}
