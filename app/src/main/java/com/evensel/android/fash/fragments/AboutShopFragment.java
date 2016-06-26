package com.evensel.android.fash.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.R;
import com.evensel.android.fash.adapters.PagerAdapter;
import com.evensel.android.fash.commons.AppConstants;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.network.DetectNetwork;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prishanm on 6/21/2016.
 */
public class AboutShopFragment extends Fragment {

    private ViewPager mViewPager = null;
    private CirclePageIndicator circlePageIndicator = null;
    private PagerAdapter pagerAdapter;
    Notifications notifications;
    AlertDialog alertDialog;
    ImageView shopLogo;
    TextView openHours,description,address;
    View loadingSpinnerImages;
    List<Fragment> fList = null;
    private Handler sliderHandler = new Handler();
    public static final int DELAY = 5000;
    int sliderPosition=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shop_layout, container, false);

        initUI(rootView);

        DetectNetwork.setmContext(getActivity());

        //Check network connectivity
        if(DetectNetwork.isConnected()){
            setViewPagerFragments();
        }else{
            alertDialog = notifications.showNetworkNotification(getActivity());
            alertDialog.show();
        }

        return rootView;
    }

    private void setViewPagerFragments() {

        ShopViewFragment shopViewFragment;
        fList = new ArrayList<Fragment>();

        int arraySize = AppConstants.getShopDetail().getImageURLs().size();

        for (int i=0;i<arraySize;i++){
            Bundle args = new Bundle();
            args.putString("IMG",AppConstants.getShopDetail().getImageURLs().get(i));
            shopViewFragment = new ShopViewFragment();
            shopViewFragment.setArguments(args);
            fList.add(shopViewFragment);
        }

        List<Fragment> fragments = fList;
        if (fragments != null) {
            pagerAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
            setViewPager();
        }

    }

    private void setViewPager() {
        mViewPager.setAdapter(pagerAdapter);
        circlePageIndicator.setViewPager(mViewPager);
        circlePageIndicator.setFillColor(Color.rgb(240, 123, 110));
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
    }

    Runnable ViewPagerVisibleScroll= new Runnable() {
        @Override
        public void run() {
            try {
                if(sliderPosition <= AppConstants.getShopDetail().getImageURLs().size()-1){
                    mViewPager.setCurrentItem(sliderPosition, true);
                    sliderPosition++;
                    sliderHandler.postDelayed(ViewPagerVisibleScroll, DELAY);

                }else{
                    sliderPosition = 0;
                    mViewPager.setCurrentItem(sliderPosition, true);
                    sliderHandler.postDelayed(ViewPagerVisibleScroll, DELAY);
                }
            }catch (NullPointerException e){

            }

        }
    };

    private void setShopImages() {

    }

    private void initUI(View rootView) {
        mViewPager = (ViewPager)rootView.findViewById(R.id.featuredViewPager);
        loadingSpinnerImages = (View)rootView.findViewById(R.id.loadingSpinnerProducts);
        circlePageIndicator = (CirclePageIndicator)rootView.findViewById(R.id.indicator);
        openHours = (TextView)rootView.findViewById(R.id.txtOpenHours);
        description = (TextView)rootView.findViewById(R.id.txtShopDescription);
        address = (TextView)rootView.findViewById(R.id.headOfficeLocation);

        shopLogo = (ImageView)rootView.findViewById(R.id.imgShopLogo);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if(!AppConstants.getShopDetail().getLogo().isEmpty()){

            imageLoader.get(AppConstants.getShopDetail().getLogo(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERR", "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        shopLogo.setImageBitmap(response.getBitmap());
                    }
                }
            });
        }

        openHours.setText("All stores are open from "+AppConstants.getShopDetail().getOpenHours());
        description.setText(AppConstants.getShopDetail().getDescription());
        address.setText(AppConstants.getShopDetail().getContacts());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sliderHandler!= null) {
            sliderHandler.removeCallbacks(ViewPagerVisibleScroll);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(ViewPagerVisibleScroll, DELAY);

    }
}
