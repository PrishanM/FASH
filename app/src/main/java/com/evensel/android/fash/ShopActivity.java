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
    }

    protected void setFragment(Fragment fragment,String superCategory) {
        Bundle args = new Bundle();
        args.putString("SUPER_CATEGORY", superCategory);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.shopTabFrame, fragment);
        fragmentTransaction.commit();
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

    @Override
    public void onClick(View v) {

    }
}
