package com.evensel.android.fash;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.evensel.android.fash.adapters.MoreShopsAdapter;
import com.evensel.android.fash.commons.AppConstants;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.network.DetectNetwork;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.ShopDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Prishanm
 * Used handle all the events in the MORE SHOPS
 */

public class MoreShopsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView listView;
    Notifications notifications;
    AlertDialog alertDialog;
    MoreShopsAdapter shopsAdapter;
    View loadingSpinnerShops;
    List<ShopDetail> allShopList,mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_shops_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("ALL SHOPS");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();

        if(DetectNetwork.isConnected()){
            getShopsList();
        }else{
            alertDialog = notifications.showNetworkNotification(this);
            alertDialog.show();
        }
    }

    //Requesting all shops
    private void getShopsList() {
        loadingSpinnerShops.setVisibility(View.VISIBLE);
        JsonRequestManager.getInstance(getApplicationContext()).getAllShops(getResources().getString(R.string.base_url) + "shops", moreShopsRequestCallback);
    }

    //Response callback for "MORE SHOPS"
    JsonRequestManager.getAllShopsRequest moreShopsRequestCallback = new JsonRequestManager.getAllShopsRequest() {
        @Override
        public void onSuccess(List<ShopDetail> allShops) {
            loadingSpinnerShops.setVisibility(View.GONE);
            if(allShops.size()>0) {
                allShopList = allShops;
                mainList = allShops;
                shopsAdapter = new MoreShopsAdapter(allShops, MoreShopsActivity.this);
                listView.setAdapter(shopsAdapter);
            }

        }

        @Override
        public void onError(String status) {
            loadingSpinnerShops.setVisibility(View.GONE);
            alertDialog = notifications.showHttpErrorDialog(MoreShopsActivity.this,status);
            alertDialog.show();
        }
    };

    //Initialize
    private void initialize() {
        listView = (ListView)findViewById(R.id.listShops);
        loadingSpinnerShops = (View)findViewById(R.id.loadingSpinnerShops);
        notifications = new Notifications();
        DetectNetwork.setmContext(getApplicationContext());
        allShopList = new ArrayList<ShopDetail>();
        mainList = new ArrayList<ShopDetail>();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AppConstants.setShopDetail(allShopList.get(position));

                Intent shopIntent = new Intent(MoreShopsActivity.this,ShopActivity.class);
                startActivity(shopIntent);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.search_src_text);
                et.setText("");

                shopsAdapter = new MoreShopsAdapter(mainList, MoreShopsActivity.this);
                allShopList = mainList;
                listView.setAdapter(shopsAdapter);
            }
        });

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intent = new Intent(MoreShopsActivity.this,LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //shopsAdapter.getFilter().filter(query);
        testMethod(query);
        return false;
    }

    private void testMethod(String query) {
        List<ShopDetail> list = new ArrayList<ShopDetail>();
        for(int i=0;i<mainList.size();i++){
            if(mainList.get(i).getName().contains(query)){
                list.add(mainList.get(i));
            }
        }
        allShopList = list;
        shopsAdapter = new MoreShopsAdapter(list, MoreShopsActivity.this);
        listView.setAdapter(shopsAdapter);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<ShopDetail> list = new ArrayList<ShopDetail>();
        for(int i=0;i<mainList.size();i++){
            if(mainList.get(i).getName().contains(newText)){
                list.add(mainList.get(i));
            }
        }
        allShopList = list;
        shopsAdapter = new MoreShopsAdapter(list, MoreShopsActivity.this);
        listView.setAdapter(shopsAdapter);
        return false;
    }


}
