package com.evensel.android.fash.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.LoginActivity;
import com.evensel.android.fash.R;
import com.evensel.android.fash.SearchResultActivity;
import com.evensel.android.fash.adapters.PagerAdapter;
import com.evensel.android.fash.commons.AppConstants;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.network.DetectNetwork;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.Datum;
import com.evensel.android.fash.util.SingleCategory;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prishanm on 6/21/2016.
 */
public class AboutShopFragment extends Fragment implements SearchView.OnQueryTextListener{

    private ViewPager mViewPager = null;
    private CirclePageIndicator circlePageIndicator = null;
    private PagerAdapter pagerAdapter;
    ProgressDialog progressDialog;
    Notifications notifications;
    AlertDialog alertDialog;
    ImageView shopLogo;
    TextView openHours,description,address;
    View loadingSpinnerImages;
    List<Fragment> fList = null;
    private Handler sliderHandler = new Handler();
    public static final int DELAY = 5000;
    int sliderPosition=0,pageNoOther=1;
    String searchQuery="";
    List<Datum> searchList = new ArrayList<Datum>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shop_layout, container, false);
        setHasOptionsMenu(true);

        notifications = new Notifications();

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
        searchList.clear();
        pageNoOther=1;
        super.onResume();
        sliderHandler.postDelayed(ViewPagerVisibleScroll, DELAY);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        progressDialog = ProgressDialog.show(getActivity(), null,
                "Searching Data...", true);
        progressDialog.setCancelable(true);
        searchQuery = query;
        getSearchResult(searchQuery, pageNoOther);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    //Requesting SEARCH DATA
    private void getSearchResult(String query,int page) {
        JsonRequestManager.getInstance(getActivity()).getShopSuperCategorySearchList(getResources().getString(R.string.base_url) + getResources().getString(R.string.category_products_url), query, AppConstants.getShopDetail().getId(), 0, page, getShopSearchListRequestCallback);

    }

    //Response callback for "MASTER SEARCH RESULT"
    JsonRequestManager.getShopSuperCategorySearchListRequest getShopSearchListRequestCallback = new JsonRequestManager.getShopSuperCategorySearchListRequest() {
        @Override
        public void onSuccess(SingleCategory category) {

            if(category.getData().size()>0)
                checkMoreNew(category);
            else{
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }

                alertDialog = notifications.showHttpErrorDialog(getActivity(),"No data found");
                alertDialog.show();
            }

        }

        @Override
        public void onError(String status) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            alertDialog = notifications.showHttpErrorDialog(getActivity(),"No data found");
            alertDialog.show();
        }
    };

    //Check for more data to be downloaded
    private void checkMoreNew(SingleCategory category) {
        for(int i=0;i<category.getData().size();i++){
            searchList.add(category.getData().get(i));
        }

        if(category.getNextPageUrl()!=null){
            pageNoOther = pageNoOther+1;
            getSearchResult(searchQuery,pageNoOther);
        } else {
            if(progressDialog!=null)
                progressDialog.dismiss();
            pageNoOther=1;
            AppConstants.setMasterSearchList(searchList);
            if(AppConstants.getMasterSearchList().size()>0)
                setData();
        }
    }

    //Set data list to search layout
    private void setData() {
        Intent intent = new Intent(getActivity(),SearchResultActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
