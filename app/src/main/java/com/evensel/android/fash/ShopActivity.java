package com.evensel.android.fash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.evensel.android.fash.commons.AppConstants;
import com.evensel.android.fash.fragments.AboutShopFragment;
import com.evensel.android.fash.fragments.ShopSuperCategoriesFragment;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.SuperCategory;

import java.util.List;

/**
 * @author Prishanm
 * Used handle all the events in the Single Shop
 */

public class ShopActivity extends AppCompatActivity implements View.OnClickListener{

    int shopId = 0;
    String shopTitle;
    TextView tabHome,tabWomen,tabMen,tabKids,tabAccessories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_shop_layout);

        shopId = AppConstants.getShopDetail().getId();
        shopTitle = AppConstants.getShopDetail().getName();

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(shopTitle.toUpperCase());
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initUI();

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
        setFragment(new AboutShopFragment(), "0");

        tabHome.setBackgroundResource(R.drawable.shop_tab_selected);

        getShopSuperCategories();
    }

    private void getShopSuperCategories() {
        JsonRequestManager.getInstance(this).getShopSuperCategories(getResources().getString(R.string.base_url) + getResources().getString(R.string.shop_super_categories_url),shopId, shopSuperCategoriesCallback);
    }

    //Response callback for "Super categories within shop"
    JsonRequestManager.getShopSuperCategoriesRequest shopSuperCategoriesCallback = new JsonRequestManager.getShopSuperCategoriesRequest() {
        @Override
        public void onSuccess(List<SuperCategory> list) {
            if(list.size()>0)
                setSuperCategories(list);
        }

        @Override
        public void onError(String status) {

        }
    };

    private void setSuperCategories(List<SuperCategory> list) {

        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equalsIgnoreCase("Women")){
                tabWomen.setVisibility(View.VISIBLE);
            }else if(list.get(i).getName().equalsIgnoreCase("Men")){
                tabMen.setVisibility(View.VISIBLE);
            }else if(list.get(i).getName().equalsIgnoreCase("Kids")){
                tabKids.setVisibility(View.VISIBLE);
            }else if(list.get(i).getName().equalsIgnoreCase("Accessories")){
                tabAccessories.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void setFragment(Fragment fragment,String superCategory) {
        Bundle args = new Bundle();
        args.putString("SUPER_CATEGORY", superCategory);
        args.putInt("SHOP_ID", shopId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.shopTabFrame, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                setFragment(new AboutShopFragment(),"0");
                tabHome.setBackgroundResource(R.drawable.shop_tab_selected);
                tabWomen.setBackgroundResource(android.R.color.transparent);
                tabMen.setBackgroundResource(android.R.color.transparent);
                tabKids.setBackgroundResource(android.R.color.transparent);
                tabAccessories.setBackgroundResource(android.R.color.transparent);

                break;

            case R.id.tabWomen:
                setFragment(new ShopSuperCategoriesFragment(),"1");
                tabWomen.setBackgroundResource(R.drawable.shop_tab_selected);
                tabHome.setBackgroundResource(android.R.color.transparent);
                tabMen.setBackgroundResource(android.R.color.transparent);
                tabKids.setBackgroundResource(android.R.color.transparent);
                tabAccessories.setBackgroundResource(android.R.color.transparent);
                break;

            case R.id.tabMen:
                setFragment(new ShopSuperCategoriesFragment(),"2");
                tabMen.setBackgroundResource(R.drawable.shop_tab_selected);
                tabWomen.setBackgroundResource(android.R.color.transparent);
                tabHome.setBackgroundResource(android.R.color.transparent);
                tabKids.setBackgroundResource(android.R.color.transparent);
                tabAccessories.setBackgroundResource(android.R.color.transparent);
                break;

            case R.id.tabKids:
                setFragment(new ShopSuperCategoriesFragment(),"3");
                tabKids.setBackgroundResource(R.drawable.shop_tab_selected);
                tabWomen.setBackgroundResource(android.R.color.transparent);
                tabMen.setBackgroundResource(android.R.color.transparent);
                tabHome.setBackgroundResource(android.R.color.transparent);
                tabAccessories.setBackgroundResource(android.R.color.transparent);
                break;

            case R.id.tabAccessories:
                setFragment(new ShopSuperCategoriesFragment(),"4");
                tabAccessories.setBackgroundResource(R.drawable.shop_tab_selected);
                tabWomen.setBackgroundResource(android.R.color.transparent);
                tabMen.setBackgroundResource(android.R.color.transparent);
                tabKids.setBackgroundResource(android.R.color.transparent);
                tabHome.setBackgroundResource(android.R.color.transparent);
                break;
        }

    }
}
