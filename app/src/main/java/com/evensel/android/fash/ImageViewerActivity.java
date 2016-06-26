package com.evensel.android.fash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.evensel.android.fash.adapters.PagerAdapter;
import com.evensel.android.fash.fragments.SingleImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Prishanm
 * Used handle Single Products - Zoomed Images
 */
public class ImageViewerActivity extends FragmentActivity {

    private ViewPager mViewPager = null;
    private PagerAdapter pagerAdapter;
    TextView textView;
    List<Fragment> fList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_view_pager);

        textView = (TextView)findViewById(R.id.imgTxtView);
        textView.setText((getIntent().getIntExtra("POSITION",0)+1)+" of "+getIntent().getStringArrayListExtra("ALL").size());

        mViewPager = (ViewPager)findViewById(R.id.imageViewer);
        setViewPagerFragments(getIntent().getStringArrayListExtra("ALL"));
        mViewPager.setCurrentItem(getIntent().getIntExtra("POSITION",0));

    }

    private void setViewPagerFragments(ArrayList<String> images) {
        SingleImageFragment singleImageFragment;
        fList = new ArrayList<Fragment>();

        for (int i=0;i<images.size();i++){

            Bundle args = new Bundle();
            args.putString("URL", images.get(i));
            args.putStringArrayList("ALL",images);
            args.putInt("POSITION",i);
            singleImageFragment = new SingleImageFragment();
            singleImageFragment.setArguments(args);
            fList.add(singleImageFragment);

        }

        List<Fragment> fragments = fList;
        if (fragments != null) {
            pagerAdapter = new PagerAdapter(getSupportFragmentManager(),fragments);
            setViewPager();
        }
    }

    private void setViewPager() {
        mViewPager.setAdapter(pagerAdapter);


        /**
         * on swiping the viewpager make respective indicator selected
         * */
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                textView.setText((position+1)+" of "+getIntent().getStringArrayListExtra("ALL").size());

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
