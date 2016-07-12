package com.evensel.android.fash;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * @author Prishanm
 * Used handle Product locations
 */
public class LocationActivity extends AppCompatActivity {

    private GoogleMap googleMap;
    ArrayList<String> locationNames = new ArrayList<String>();
    ArrayList<String> locationLatitudes = new ArrayList<String>();
    ArrayList<String> locationLongitudes = new ArrayList<String>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("LOCATIONS");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        locationNames = getIntent().getStringArrayListExtra("NAME");
        locationLongitudes = getIntent().getStringArrayListExtra("LONGITUDE");
        locationLatitudes = getIntent().getStringArrayListExtra("LATITUDE");

        try {
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }else{
                for(int i=0;i<locationNames.size();i++){
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(locationLatitudes.get(i)), Double.parseDouble(locationLongitudes.get(i)))).title(locationNames.get(i));
                    googleMap.addMarker(marker);
                }

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(Double.parseDouble(locationLatitudes.get(0)), Double.parseDouble(locationLongitudes.get(0)))).zoom(10).build();

                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));


            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
