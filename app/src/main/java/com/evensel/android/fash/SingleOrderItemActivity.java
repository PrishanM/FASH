package com.evensel.android.fash;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.evensel.android.fash.adapters.PagerAdapter;
import com.evensel.android.fash.fragments.TestFragment;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prishan Maduka on 7/15/2016.
 */
public class SingleOrderItemActivity extends AppCompatActivity {

    private ViewPager mViewPager = null;
    private CirclePageIndicator circlePageIndicator = null;
    private PagerAdapter pagerAdapter;
    List<Fragment> fList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_order_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("ORDER HISTORY");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
        setViewPagerFragments();
    }

    private void initialize() {
        mViewPager = (ViewPager) findViewById(R.id.productViewPager);

    }

    //Set view pager fragments - Product Images
    private void setViewPagerFragments() {
        TestFragment testFragment;
        fList = new ArrayList<Fragment>();
        /*ArrayList<String> allImages = new ArrayList<String>();
        allImages = (ArrayList)images;*/

        for (int i=0;i<7;i++){

            /*Bundle args = new Bundle();
            args.putString("URL", images.get(i));
            args.putStringArrayList("ALL",(ArrayList)images);
            args.putInt("POSITION", i);*/
            testFragment = new TestFragment();
            //singleProductsFragment.setArguments(args);
            fList.add(testFragment);

        }

        List<Fragment> fragments = fList;
        if (fragments != null) {
            pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
            setViewPager();
        }
    }

    private void setViewPager() {
        mViewPager.setAdapter(pagerAdapter);
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(mViewPager);
        circlePageIndicator.setFillColor(Color.rgb(240, 123, 110));
        circlePageIndicator.setPageColor(Color.rgb(130, 133, 133));
        circlePageIndicator.setStrokeWidth(0.0f);

        /**
         * on swiping the viewpager make respective indicator selected
         * */
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                circlePageIndicator.setCurrentItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
}
