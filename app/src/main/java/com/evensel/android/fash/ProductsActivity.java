package com.evensel.android.fash;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.evensel.android.fash.adapters.ProductListAdapter;
import com.evensel.android.fash.commons.AppConstants;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.network.DetectNetwork;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.Datum;
import com.evensel.android.fash.util.SingleCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Prishanm
 * Used handle all the events in the Product List
 */
public class ProductsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    String categoryName;
    int shopId,categoryId;
    int pageNo=1;
    List<Datum> productList = new ArrayList<Datum>();
    ListView listProductList;
    Notifications notifications;
    AlertDialog alertDialog;
    View loadingSpinner;
    //SearchView searchView;
    ProgressDialog progressDialog;
    int pageNoOther=1;
    String searchQuery="";
    ProductListAdapter productListAdapter;
    List<Datum> searchList = new ArrayList<Datum>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_products_layout);

        categoryId = getIntent().getExtras().getInt("ID");
        categoryName = getIntent().getStringExtra("TITLE");
        shopId = getIntent().getExtras().getInt("SHOP_ID");

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(categoryName);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        notifications = new Notifications();
        initUI();

        DetectNetwork.setmContext(getApplicationContext());

        //Check network connectivity
        if(DetectNetwork.isConnected()){
            getProducts(pageNo);
        }else{
            alertDialog = notifications.showNetworkNotification(this);
            alertDialog.show();
        }


    }

    //Requesting product list of particular categories
    private void getProducts(int page) {
        if(loadingSpinner.getVisibility()!=View.VISIBLE){
            loadingSpinner.setVisibility(View.VISIBLE);
        }
        JsonRequestManager.getInstance(getApplicationContext()).getProductList(getResources().getString(R.string.base_url) + getResources().getString(R.string.category_products_url),shopId,categoryId,page, getProductListRequestCallback);

    }

    //Response callback for "CATEGORY PRODUCTS"
    JsonRequestManager.getProductListRequest getProductListRequestCallback = new JsonRequestManager.getProductListRequest() {
        @Override
        public void onSuccess(SingleCategory category) {

            if(category.getData().size()>0)
                checkMore(category);
            else{
                if(loadingSpinner.getVisibility()==View.VISIBLE){
                    loadingSpinner.setVisibility(View.GONE);
                }
            }

        }

        @Override
        public void onError(String status) {
            if(loadingSpinner.getVisibility()==View.VISIBLE){
                loadingSpinner.setVisibility(View.GONE);
            }
            alertDialog = notifications.showHttpErrorDialog(ProductsActivity.this,status);
            alertDialog.show();
        }
    };

    //Check for more data
    private void checkMore(SingleCategory category) {
        for(int i=0;i<category.getData().size();i++){
            productList.add(category.getData().get(i));
        }
        if(loadingSpinner.getVisibility()==View.VISIBLE){
            loadingSpinner.setVisibility(View.GONE);
        }
        if(category.getNextPageUrl()!=null){
            pageNo = pageNo+1;
            getProducts(pageNo);
        }else{
            setDataList(productList);
        }
    }

    //Set Data to List
    private void setDataList(List<Datum> data) {
        HashMap<Integer,List<Datum>> listHashMap = new HashMap<Integer,List<Datum>>();
        int arraySize = (int)Math.ceil(data.size()/3.0);
        for (int i=0;i<arraySize;i++){
            int position = (i*3);
            List<Datum> tmpList = new ArrayList<Datum>();

            if((position+0)<data.size()){
                tmpList.add(data.get(position+0));
            }
            if((position+1)<data.size()){
                tmpList.add(data.get(position+1));
            }
            if((position+2)<data.size()){
                tmpList.add(data.get(position+2));
            }
            listHashMap.put(i, tmpList);
        }

        productListAdapter = new ProductListAdapter(listHashMap,ProductsActivity.this);
        listProductList.setAdapter(productListAdapter);
    }

    //Initializing UI components
    private void initUI() {

        listProductList = (ListView)findViewById(R.id.listProducts);
        loadingSpinner = (View)findViewById(R.id.loadingSpinner);

    }

    //Requesting SEARCH DATA
    private void getSearchResult(String query,int page) {
        JsonRequestManager.getInstance(getApplicationContext()).getShopCategorySearchList(getResources().getString(R.string.base_url) + getResources().getString(R.string.category_products_url), query, shopId, categoryId, page, getShopSearchListRequestCallback);

    }

    //Response callback for "MASTER SEARCH RESULT"
    JsonRequestManager.getShopCategorySearchListRequest getShopSearchListRequestCallback = new JsonRequestManager.getShopCategorySearchListRequest() {
        @Override
        public void onSuccess(SingleCategory category) {

            if(category.getData().size()>0)
                checkMoreNew(category);
            else{
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }

                alertDialog = notifications.showHttpErrorDialog(ProductsActivity.this,"No data found");
                alertDialog.show();
            }

        }

        @Override
        public void onError(String status) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            alertDialog = notifications.showHttpErrorDialog(ProductsActivity.this,status);
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
        Intent intent = new Intent(ProductsActivity.this,SearchResultActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        searchList.clear();
        pageNoOther=1;
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        progressDialog = ProgressDialog.show(ProductsActivity.this, null,
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
}
