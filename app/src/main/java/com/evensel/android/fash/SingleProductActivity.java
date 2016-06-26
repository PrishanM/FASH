package com.evensel.android.fash;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.evensel.android.fash.adapters.PagerAdapter;
import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.fragments.SingleProductsFragment;
import com.evensel.android.fash.network.DetectNetwork;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.SingleProduct;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Prishanm
 * Used handle Single Products
 */
public class SingleProductActivity extends AppCompatActivity {

    private ViewPager mViewPager = null;
    private CirclePageIndicator circlePageIndicator = null;
    private PagerAdapter pagerAdapter;
    TextView txtName,txtPrice,txtStock,txtDescription,txtLocations,txtSize1,txtSize2,txtSize3,txtSize4,txtSize5,txtSize6,txtSize7,txtCode;
    List<Fragment> fList = null;
    ArrayList<String> locationNames = new ArrayList<String>();
    ArrayList<String> locationLatitudes = new ArrayList<String>();
    ArrayList<String> locationLongitudes = new ArrayList<String>();

    Notifications notifications;
    FloatingActionButton btnMap;
    AlertDialog alertDialog;
    View loadingSpinner;
    int productId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product);

        notifications = new Notifications();

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("PRODUCT");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);


        initUI();

        productId = getIntent().getExtras().getInt("ID");

        DetectNetwork.setmContext(getApplicationContext());

        //Check network connectivity
        if (DetectNetwork.isConnected()) {
            getProductDetails();
        } else {
            alertDialog = notifications.showNetworkNotification(this);
            alertDialog.show();
        }
    }

    //Initialize UI components
    private void initUI() {
        mViewPager = (ViewPager) findViewById(R.id.productViewPager);
        loadingSpinner = (View) findViewById(R.id.loadingSpinner);
        txtName = (TextView)findViewById(R.id.txtName);
        txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtCode = (TextView)findViewById(R.id.txtCode);
        txtStock = (TextView)findViewById(R.id.txtStock);
        txtDescription = (TextView)findViewById(R.id.txtDescription);
        txtLocations = (TextView)findViewById(R.id.txtLocation);
        txtSize1 = (TextView)findViewById(R.id.size1);
        txtSize2 = (TextView)findViewById(R.id.size2);
        txtSize3 = (TextView)findViewById(R.id.size3);
        txtSize4 = (TextView)findViewById(R.id.size4);
        txtSize5 = (TextView)findViewById(R.id.size5);
        txtSize6 = (TextView)findViewById(R.id.size6);
        txtSize7 = (TextView)findViewById(R.id.size7);
        btnMap = (FloatingActionButton)findViewById(R.id.fabLocation);



        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SingleProductActivity.this, LocationActivity.class);
                intent.putStringArrayListExtra("NAME",locationNames);
                intent.putStringArrayListExtra("LONGITUDE",locationLongitudes);
                intent.putStringArrayListExtra("LATITUDE",locationLatitudes);
                startActivity(intent);
            }
        });

    }

    //Requesting single product details
    private void getProductDetails() {
        loadingSpinner.setVisibility(View.VISIBLE);
        JsonRequestManager.getInstance(getApplicationContext()).getSingleProducts(getResources().getString(R.string.base_url) + getResources().getString(R.string.single_product_url), productId, getSingleProductRequest);

    }

    //Response callback for SINGLE PRODUCT
    JsonRequestManager.getSingleProductRequest getSingleProductRequest = new JsonRequestManager.getSingleProductRequest() {
        @Override
        public void onSuccess(SingleProduct singleProduct) {
            loadingSpinner.setVisibility(View.GONE);
            try {
                if (singleProduct.getImages().size() > 0)
                    setViewPagerFragments(singleProduct.getImages());

                setProductDetails(singleProduct);
            }catch (Exception e){
                alertDialog = notifications.showHttpErrorDialog(SingleProductActivity.this,"Error occured1");
                alertDialog.show();
            }


        }

        @Override
        public void onError(String status) {
            loadingSpinner.setVisibility(View.GONE);
            alertDialog = notifications.showHttpErrorDialog(SingleProductActivity.this,"Error occured");
            alertDialog.show();
        }
    };

    //Set other product details
    private void setProductDetails(SingleProduct singleProduct) {
        if(singleProduct.getTitle()!=null){
            txtName.setText(singleProduct.getTitle());
        }

        if(singleProduct.getPrice()!=null){
            txtPrice.setText(singleProduct.getPrice());
        }

        if(singleProduct.getCode()!=null){
            txtCode.setText(singleProduct.getCode());
        }

        if(singleProduct.getInStock()){
            txtStock.setText("In Stock");
            txtStock.setBackgroundColor(getResources().getColor(R.color.stock_tag_color));
        }else{
            txtStock.setText("Out of Stock");
            txtStock.setBackgroundColor(getResources().getColor(R.color.out_of_stock_tag_color));
        }

        if(singleProduct.getDescription()!=null){
            txtDescription.setText(singleProduct.getDescription());
        }

        if(singleProduct.getLocations()!=null && singleProduct.getLocations().size()>0){
            String locations="";
            for (int i=0;i<singleProduct.getLocations().size();i++){
                locations = locations+","+singleProduct.getLocations().get(i).getName();
                locationNames.add(singleProduct.getLocations().get(i).getName());
                locationLatitudes.add(singleProduct.getLocations().get(i).getLatitude());
                locationLongitudes.add(singleProduct.getLocations().get(i).getLongitude());
            }
            locations = locations.startsWith(",") ? locations.substring(1) : locations;
            txtLocations.setText(locations);
        }else{
            btnMap.setVisibility(View.GONE);
        }

        if (singleProduct.getSizes()!=null && singleProduct.getSizes().size()>0){
            for (int i=0;i<singleProduct.getSizes().size();i++){
                if(i==0){
                    txtSize1.setText(singleProduct.getSizes().get(i));
                    txtSize1.setVisibility(View.VISIBLE);
                }

                if(i==1){
                    txtSize2.setText(singleProduct.getSizes().get(i));
                    txtSize2.setVisibility(View.VISIBLE);
                }

                if(i==2){
                    txtSize3.setText(singleProduct.getSizes().get(i));
                    txtSize3.setVisibility(View.VISIBLE);
                }

                if(i==3){
                    txtSize4.setText(singleProduct.getSizes().get(i));
                    txtSize4.setVisibility(View.VISIBLE);
                }

                if(i==4){
                    txtSize5.setText(singleProduct.getSizes().get(i));
                    txtSize5.setVisibility(View.VISIBLE);
                }

                if(i==5){
                    txtSize6.setText(singleProduct.getSizes().get(i));
                    txtSize6.setVisibility(View.VISIBLE);
                }

                if(i==6){
                    txtSize7.setText(singleProduct.getSizes().get(i));
                    txtSize7.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //Set view pager fragments - Product Images
    private void setViewPagerFragments(List<String> images) {
        SingleProductsFragment singleProductsFragment;
        fList = new ArrayList<Fragment>();
        /*ArrayList<String> allImages = new ArrayList<String>();
        allImages = (ArrayList)images;*/

        for (int i=0;i<images.size();i++){

            Bundle args = new Bundle();
            args.putString("URL", images.get(i));
            args.putStringArrayList("ALL",(ArrayList)images);
            args.putInt("POSITION", i);
            singleProductsFragment = new SingleProductsFragment();
            singleProductsFragment.setArguments(args);
            fList.add(singleProductsFragment);

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

