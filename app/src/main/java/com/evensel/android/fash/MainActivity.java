package com.evensel.android.fash;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.evensel.android.fash.commons.AppConstants;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.fragments.HomeFragment;
import com.evensel.android.fash.fragments.SuperCategoryFragment;
import com.evensel.android.fash.network.DetectNetwork;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.Datum;
import com.evensel.android.fash.util.SingleCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Prishanm
 * Used handle all the events in the HOME screen
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener,SearchView.OnQueryTextListener{

    TextView tabHome,tabWomen,tabMen,tabKids,tabAccessories;
    ProgressDialog progressDialog;
    Notifications notifications;
    int pageNoOther=1;
    String searchQuery="";
    AlertDialog alertDialog;
    List<Datum> searchList = new ArrayList<Datum>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notifications = new Notifications();

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("FASH");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        //abar.setDisplayHomeAsUpEnabled(true);
        //abar.setHomeButtonEnabled(true);

        initUI();

        DetectNetwork.setmContext(this);
    }

    private void initUI() {

        tabHome = (TextView)findViewById(R.id.tabHome);
        tabWomen = (TextView)findViewById(R.id.tabWomen);
        tabMen = (TextView)findViewById(R.id.tabMen);
        tabKids = (TextView)findViewById(R.id.tabKids);
        tabAccessories = (TextView)findViewById(R.id.tabAccessories);

        tabHome.setOnClickListener(this);
        tabWomen.setOnClickListener(this);
        tabMen.setOnClickListener(this);
        tabKids.setOnClickListener(this);
        tabAccessories.setOnClickListener(this);

        //set initial tab layout
        setFragment(new HomeFragment(),"0");

        tabHome.setBackgroundResource(R.drawable.tab_selected);
        tabHome.setTextColor(Color.parseColor("#FFFFFF"));

    }

    protected void setFragment(Fragment fragment,String superCategory) {
        Bundle args = new Bundle();
        args.putString("SUPER_CATEGORY",superCategory);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.mainTabFrame, fragment);
        fragmentTransaction.commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tabHome:
                setFragment(new HomeFragment(),"0");
                tabHome.setBackgroundResource(R.drawable.tab_selected);
                tabHome.setTextColor(Color.parseColor("#FFFFFF"));

                tabWomen.setBackgroundResource(android.R.color.transparent);
                tabWomen.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabMen.setBackgroundResource(android.R.color.transparent);
                tabMen.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabKids.setBackgroundResource(android.R.color.transparent);
                tabKids.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabAccessories.setBackgroundResource(android.R.color.transparent);
                tabAccessories.setTextColor(getResources().getColor(R.color.heading_text_color));

                break;

            case R.id.tabWomen:
                setFragment(new SuperCategoryFragment(),"1");
                tabWomen.setBackgroundResource(R.drawable.tab_selected);
                tabWomen.setTextColor(Color.parseColor("#FFFFFF"));

                tabHome.setBackgroundResource(android.R.color.transparent);
                tabHome.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabMen.setBackgroundResource(android.R.color.transparent);
                tabMen.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabKids.setBackgroundResource(android.R.color.transparent);
                tabKids.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabAccessories.setBackgroundResource(android.R.color.transparent);
                tabAccessories.setTextColor(getResources().getColor(R.color.heading_text_color));
                break;

            case R.id.tabMen:
                setFragment(new SuperCategoryFragment(),"2");
                tabMen.setBackgroundResource(R.drawable.tab_selected);
                tabMen.setTextColor(Color.parseColor("#FFFFFF"));

                tabWomen.setBackgroundResource(android.R.color.transparent);
                tabWomen.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabHome.setBackgroundResource(android.R.color.transparent);
                tabHome.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabKids.setBackgroundResource(android.R.color.transparent);
                tabKids.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabAccessories.setBackgroundResource(android.R.color.transparent);
                tabAccessories.setTextColor(getResources().getColor(R.color.heading_text_color));
                break;

            case R.id.tabKids:
                setFragment(new SuperCategoryFragment(),"3");
                tabKids.setBackgroundResource(R.drawable.tab_selected);
                tabKids.setTextColor(Color.parseColor("#FFFFFF"));

                tabWomen.setBackgroundResource(android.R.color.transparent);
                tabWomen.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabMen.setBackgroundResource(android.R.color.transparent);
                tabMen.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabHome.setBackgroundResource(android.R.color.transparent);
                tabHome.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabAccessories.setBackgroundResource(android.R.color.transparent);
                tabAccessories.setTextColor(getResources().getColor(R.color.heading_text_color));
                break;

            case R.id.tabAccessories:
                setFragment(new SuperCategoryFragment(),"4");
                tabAccessories.setBackgroundResource(R.drawable.tab_selected);
                tabAccessories.setTextColor(Color.parseColor("#FFFFFF"));

                tabWomen.setBackgroundResource(android.R.color.transparent);
                tabWomen.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabMen.setBackgroundResource(android.R.color.transparent);
                tabMen.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabKids.setBackgroundResource(android.R.color.transparent);
                tabKids.setTextColor(getResources().getColor(R.color.heading_text_color));

                tabHome.setBackgroundResource(android.R.color.transparent);
                tabHome.setTextColor(getResources().getColor(R.color.heading_text_color));
                break;
        }

    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        //Check network connectivity
        if(DetectNetwork.isConnected()){
            progressDialog = ProgressDialog.show(MainActivity.this, null,
                    "Searching Data...", true);
            progressDialog.setCancelable(true);
            searchQuery = query;
            getSearchResult(searchQuery, pageNoOther);
        }else{
            alertDialog = notifications.showNetworkNotification(MainActivity.this);
            alertDialog.show();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    //Requesting SEARCH DATA
    private void getSearchResult(String query,int page) {
        JsonRequestManager.getInstance(MainActivity.this).getMasterSearchList(getResources().getString(R.string.base_url) + getResources().getString(R.string.category_products_url), query, page, getShopSearchListRequestCallback);

    }

    //Response callback for "MASTER SEARCH RESULT"
    JsonRequestManager.getMasterSearchListRequest getShopSearchListRequestCallback = new JsonRequestManager.getMasterSearchListRequest() {
        @Override
        public void onSuccess(SingleCategory category) {

            if(category.getData().size()>0)
                checkMoreNew(category);
            else{
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }

                alertDialog = notifications.showHttpErrorDialog(MainActivity.this,"No data found");
                alertDialog.show();
            }

        }

        @Override
        public void onError(String status) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            alertDialog = notifications.showHttpErrorDialog(MainActivity.this,"No data found");
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
        Intent intent = new Intent(MainActivity.this,SearchResultActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        searchList.clear();
        pageNoOther=1;
        super.onResume();
    }
}
