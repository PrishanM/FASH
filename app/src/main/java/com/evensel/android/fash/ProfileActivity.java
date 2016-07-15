package com.evensel.android.fash;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.evensel.android.fash.fragments.MyProfileFragment;
import com.evensel.android.fash.fragments.OrderHistoryFragment;

/**
 * Created by Prishan Maduka on 7/13/2016.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tabProfile,tabOrder,tabNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("PROFILE");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        
        initialize();
    }

    private void initialize() {
        tabProfile = (TextView)findViewById(R.id.tabMyProfile);
        tabOrder = (TextView)findViewById(R.id.tabOrderHistory);
        tabNotifications = (TextView)findViewById(R.id.tabMessages);

        tabProfile.setOnClickListener(this);
        tabOrder.setOnClickListener(this);
        tabNotifications.setOnClickListener(this);

        //set initial tab layout
        setFragment(new MyProfileFragment(),"0");

        tabProfile.setBackgroundResource(R.drawable.tab_selected);
        tabProfile.setTextColor(Color.parseColor("#FFFFFF"));
    }

    protected void setFragment(Fragment fragment, String tabId) {
        Bundle args = new Bundle();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.profileMainTab, fragment);
        fragmentTransaction.commit();
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tabMyProfile){

            setFragment(new MyProfileFragment(),"0");
            tabProfile.setBackgroundResource(R.drawable.tab_selected);
            tabProfile.setTextColor(Color.parseColor("#FFFFFF"));

            tabOrder.setBackgroundResource(android.R.color.transparent);
            tabOrder.setTextColor(getResources().getColor(R.color.heading_text_color));

            tabNotifications.setBackgroundResource(android.R.color.transparent);
            tabNotifications.setTextColor(getResources().getColor(R.color.heading_text_color));

        }else if(v.getId()==R.id.tabOrderHistory){

            setFragment(new OrderHistoryFragment(),"1");

            tabOrder.setBackgroundResource(R.drawable.tab_selected);
            tabOrder.setTextColor(Color.parseColor("#FFFFFF"));

            tabProfile.setBackgroundResource(android.R.color.transparent);
            tabProfile.setTextColor(getResources().getColor(R.color.heading_text_color));

            tabNotifications.setBackgroundResource(android.R.color.transparent);
            tabNotifications.setTextColor(getResources().getColor(R.color.heading_text_color));

        }else if(v.getId()==R.id.tabMessages){
            tabNotifications.setBackgroundResource(R.drawable.tab_selected);
            tabNotifications.setTextColor(Color.parseColor("#FFFFFF"));

            tabOrder.setBackgroundResource(android.R.color.transparent);
            tabOrder.setTextColor(getResources().getColor(R.color.heading_text_color));

            tabProfile.setBackgroundResource(android.R.color.transparent);
            tabProfile.setTextColor(getResources().getColor(R.color.heading_text_color));

        }


    }
}
