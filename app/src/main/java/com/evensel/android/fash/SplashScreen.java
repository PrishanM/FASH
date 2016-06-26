package com.evensel.android.fash;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author Prishanm
 * Splash Activity
 */
public class SplashScreen extends Activity {

    //timeout timer
    private static int SPLASH_TIMEOUT_TIME = 3000;
    int phoneLocationCoarseState = 0;
    int phoneAccessFineLocationState = 0;
    private static int REQUEST_CODE = 1100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //Start next activity
                    Intent mainActivity = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
            }, SPLASH_TIMEOUT_TIME);
        }else{
            phoneAccessFineLocationState = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            phoneLocationCoarseState = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

            if(phoneAccessFineLocationState!= PackageManager.PERMISSION_GRANTED && phoneLocationCoarseState!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            }else if(phoneAccessFineLocationState== PackageManager.PERMISSION_GRANTED && phoneLocationCoarseState!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            }else if(phoneAccessFineLocationState!= PackageManager.PERMISSION_GRANTED && phoneLocationCoarseState== PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            }else{
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //Start next activity
                        Intent mainActivity = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(mainActivity);
                        finish();
                    }
                }, SPLASH_TIMEOUT_TIME);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            //Start next activity
                            Intent mainActivity = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(mainActivity);
                            finish();
                        }
                    }, SPLASH_TIMEOUT_TIME);
                } else {
                    // Permission Denied
                    System.exit(1);
                }
                break;
            default:
                System.exit(1);
                break;
        }
    }
}
