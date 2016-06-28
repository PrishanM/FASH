package com.evensel.android.fash.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.evensel.android.fash.R;
import com.evensel.android.fash.adapters.HomeSuperCategorySubCategoriesAdapter;
import com.evensel.android.fash.adapters.PagerAdapter;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.network.DetectNetwork;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.FeaturedProduct;
import com.evensel.android.fash.util.FeaturedShop;
import com.evensel.android.fash.util.HomeSuperCategory;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 6/21/2016.
 */
public class SuperCategoryFragment extends Fragment {

    Notifications notifications;
    AlertDialog alertDialog;
    private ViewPager mViewPager = null;
    private CirclePageIndicator circlePageIndicator = null;
    private PagerAdapter pagerAdapter;
    List<Fragment> fList = null;
    View loadingSpinnerProducts,loadingSpinnerCategories;
    List<FeaturedShop> dataList = new ArrayList<FeaturedShop>();
    ListView categories;
    HomeSuperCategorySubCategoriesAdapter adapter;
    String superCategory = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.super_category_layout, container, false);

        initUI(rootView);

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

    private void getFeaturedProducts() {

        loadingSpinnerProducts.setVisibility(View.VISIBLE);
        JsonRequestManager.getInstance(getActivity()).getFeaturedProducts(getResources().getString(R.string.base_url) + getResources().getString(R.string.featured_products_url), superCategory,featuredProductsRequestCallback);

    }

    private void initUI(View rootView) {

        mViewPager = (ViewPager)rootView.findViewById(R.id.featuredViewPager);
        loadingSpinnerProducts = (View)rootView.findViewById(R.id.loadingSpinnerProducts);
        loadingSpinnerCategories = (View)rootView.findViewById(R.id.loadingSpinnerCategories);
        categories = (ListView)rootView.findViewById(R.id.categoryList);
        circlePageIndicator = (CirclePageIndicator)rootView.findViewById(R.id.indicator);
        superCategory = getArguments().getString("SUPER_CATEGORY");
    }

    //Response callback for "FEATURED PRODUCTS"
    JsonRequestManager.getFeaturedProductsRequest featuredProductsRequestCallback = new JsonRequestManager.getFeaturedProductsRequest() {
        @Override
        public void onSuccess(List<FeaturedProduct> list) {
            loadingSpinnerProducts.setVisibility(View.GONE);
            if(list.size()>0)
                setViewPagerFragments(list);

            getCategories();

        }

        @Override
        public void onError(String status) {
            loadingSpinnerProducts.setVisibility(View.GONE);
        }
    };

    private void setViewPagerFragments(List<FeaturedProduct> list) {

        FeaturedProductsFragment productsFragment;
        fList = new ArrayList<Fragment>();

        int arraySize = (int)Math.ceil(list.size()/3.0);

        for (int i=0;i<arraySize;i++){
            ArrayList<String> dataTitle = new ArrayList<String>();
            ArrayList<String> dataPrice = new ArrayList<String>();
            ArrayList<String> dataImage = new ArrayList<String>();
            ArrayList<Integer> dataID = new ArrayList<Integer>();

            int position = (i*3);

            try {
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

            }

            Bundle args = new Bundle();
            args.putStringArrayList("TITLE", dataTitle);
            args.putStringArrayList("IMAGE", dataImage);
            args.putStringArrayList("PRICE",dataPrice);
            args.putIntegerArrayList("ID", dataID);
            productsFragment = new FeaturedProductsFragment();
            productsFragment.setArguments(args);
            fList.add(productsFragment);
        }

        List<Fragment> fragments = getFragments();
        if (fragments != null) {
            pagerAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
            setViewPager();
        }
    }

    private void getCategories() {

        loadingSpinnerCategories.setVisibility(View.VISIBLE);
        JsonRequestManager.getInstance(getActivity()).getSuperCategoryCategoryList(getResources().getString(R.string.base_url) + getResources().getString(R.string.categories_url), superCategory, categoriesWithinSupercategoryCallback);
        //setCategoryList();
    }

    //Response callback for "Categories within super category"
    JsonRequestManager.getSuperCategoryCategoryListRequest categoriesWithinSupercategoryCallback = new JsonRequestManager.getSuperCategoryCategoryListRequest() {
        @Override
        public void onSuccess(List<HomeSuperCategory> list) {
            loadingSpinnerCategories.setVisibility(View.GONE);
            if(list.size()>0)
                setCategoryList(list);
        }

        @Override
        public void onError(String status) {
            loadingSpinnerCategories.setVisibility(View.GONE);
        }
    };

    private void setCategoryList(List<HomeSuperCategory> list) {

        int arraySize = (int)Math.ceil(list.size()/3.0);
        HashMap<Integer,List<HomeSuperCategory>>  categoryMap = new HashMap<Integer,List<HomeSuperCategory>>();

        for(int i=0;i<arraySize;i++){
            int position = (i*3);
            List<HomeSuperCategory> categoryList = new ArrayList<HomeSuperCategory>();

            if((position+0)<list.size()){
                categoryList.add(list.get(position+0));
            }

            if((position+1)<list.size()){
                categoryList.add(list.get(position+1));
            }

            if((position+2)<list.size()){
                categoryList.add(list.get(position+2));
            }

            categoryMap.put(i,categoryList);

        }


        adapter = new HomeSuperCategorySubCategoriesAdapter(categoryMap,getActivity());
        categories.setAdapter(adapter);

    }

    //Set view pager
    private void setViewPager() {
        mViewPager.setAdapter(pagerAdapter);
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
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    private List<Fragment> getFragments() {
        return fList;
    }
}
