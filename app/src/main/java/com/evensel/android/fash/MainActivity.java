package com.evensel.android.fash;

import android.graphics.Color;
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

import com.evensel.android.fash.fragments.HomeFragment;
import com.evensel.android.fash.fragments.SuperCategoryFragment;

/**
 * @author Prishanm
 * Used handle all the events in the HOME screen
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tabHome,tabWomen,tabMen,tabKids,tabAccessories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


}
