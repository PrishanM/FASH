package com.evensel.android.fash;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class MoreShopsActivity extends AppCompatActivity {

    ListView listView;
    Notifications notifications;
    AlertDialog alertDialog;
    MoreShopsAdapter shopsAdapter;
    View loadingSpinnerShops;
    List<ShopDetail> allShopList;

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
        getMenuInflater().inflate(R.menu.other_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
