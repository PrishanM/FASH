package com.evensel.android.fash;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.evensel.android.fash.adapters.ProductListAdapter;
import com.evensel.android.fash.commons.AppConstants;
import com.evensel.android.fash.util.Datum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Prishanm
 * Activity used to display search result
 */
public class SearchResultActivity extends AppCompatActivity {

    ListView listView;
    ProductListAdapter productListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("SEARCH RESULTS");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);


        listView = (ListView)findViewById(R.id.listSearchData);

        setData();
    }

    private void setData() {
        HashMap<Integer,List<Datum>> listHashMap = new HashMap<Integer,List<Datum>>();
        List<Datum> dataList = AppConstants.getMasterSearchList();
        int arraySize = (int)Math.ceil(AppConstants.getMasterSearchList().size()/3.0);
        for (int i=0;i<arraySize;i++){
            int position = (i*3);
            List<Datum> tmpList = new ArrayList<Datum>();

            if((position+0)<dataList.size()){
                tmpList.add(dataList.get(position+0));
            }
            if((position+1)<dataList.size()){
                tmpList.add(dataList.get(position+1));
            }
            if((position+2)<dataList.size()){
                tmpList.add(dataList.get(position+2));
            }
            listHashMap.put(i,tmpList);
        }

        productListAdapter = new ProductListAdapter(listHashMap,SearchResultActivity.this);
        listView.setAdapter(productListAdapter);
    }

}
