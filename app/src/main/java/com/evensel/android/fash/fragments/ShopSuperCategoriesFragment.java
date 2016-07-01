package com.evensel.android.fash.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.evensel.android.fash.R;
import com.evensel.android.fash.SearchResultActivity;
import com.evensel.android.fash.adapters.ShopSuperCategorySubCategoriesAdapter;
import com.evensel.android.fash.commons.AppConstants;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.network.DetectNetwork;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.Datum;
import com.evensel.android.fash.util.HomeSuperCategory;
import com.evensel.android.fash.util.SingleCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 6/26/2016.
 */
public class ShopSuperCategoriesFragment extends Fragment implements SearchView.OnQueryTextListener{

    ListView categoryListView;
    ShopSuperCategorySubCategoriesAdapter adapter;
    Notifications notifications;
    View loadingSpinnerCategories;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    int shopId =0,pageNoOther=1;
    String superCategory="",searchQuery="";
    List<Datum> searchList = new ArrayList<Datum>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shop_supercategory_main_layout, container, false);

        setHasOptionsMenu(true);
        notifications = new Notifications();

        categoryListView = (ListView)rootView.findViewById(R.id.categoryList);
        loadingSpinnerCategories = (View)rootView.findViewById(R.id.loadingSpinnerCategories);

        superCategory = getArguments().getString("SUPER_CATEGORY");
        shopId = getArguments().getInt("SHOP_ID");

        DetectNetwork.setmContext(getActivity());

        //Check network connectivity
        if(DetectNetwork.isConnected()){
            getCategoryList();
        }else{
            alertDialog = notifications.showNetworkNotification(getActivity());
            alertDialog.show();
        }

        return rootView;
    }

    private void getCategoryList() {
        loadingSpinnerCategories.setVisibility(View.VISIBLE);
        JsonRequestManager.getInstance(getActivity()).getShopCategoryList(getResources().getString(R.string.base_url) + getResources().getString(R.string.categories_url), superCategory, shopId, categoriesWithinSupercategoryCallback);
    }

    //Response callback for "Categories within super category"
    JsonRequestManager.getShopCategoryListRequest categoriesWithinSupercategoryCallback = new JsonRequestManager.getShopCategoryListRequest() {
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
        HashMap<Integer,List<HomeSuperCategory>> categoryMap = new HashMap<Integer,List<HomeSuperCategory>>();

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


        adapter = new ShopSuperCategorySubCategoriesAdapter(categoryMap,getActivity(),shopId);
        categoryListView.setAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
        JsonRequestManager.getInstance(getActivity()).getShopSuperCategorySearchList(getResources().getString(R.string.base_url) + getResources().getString(R.string.category_products_url), query, shopId, Integer.parseInt(superCategory), page, getShopSearchListRequestCallback);

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

    @Override
    public void onResume() {
        searchList.clear();
        pageNoOther=1;
        super.onResume();
    }

    //Set data list to search layout
    private void setData() {
        Intent intent = new Intent(getActivity(),SearchResultActivity.class);
        startActivity(intent);
    }
}
