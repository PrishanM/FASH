package com.evensel.android.fash.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.evensel.android.fash.R;
import com.evensel.android.fash.adapters.ShopSuperCategorySubCategoriesAdapter;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.network.DetectNetwork;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.HomeSuperCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 6/26/2016.
 */
public class ShopSuperCategoriesFragment extends Fragment{

    ListView categoryListView;
    ShopSuperCategorySubCategoriesAdapter adapter;
    Notifications notifications;
    View loadingSpinnerCategories;
    AlertDialog alertDialog;
    int shopId =0;
    String superCategory="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shop_supercategory_main_layout, container, false);

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
        JsonRequestManager.getInstance(getActivity()).getShopCategoryList(getResources().getString(R.string.base_url) + getResources().getString(R.string.categories_url), superCategory,shopId, categoriesWithinSupercategoryCallback);
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


        adapter = new ShopSuperCategorySubCategoriesAdapter(categoryMap,getActivity());
        categoryListView.setAdapter(adapter);

    }
}
