package com.evensel.android.fash.commons;

import android.app.Application;

import com.evensel.android.fash.util.Datum;
import com.evensel.android.fash.util.ShopDetail;

import java.util.List;

/**
 * @author Prishanm
 * Common class maintain constants in the application
 */
public class AppConstants extends Application {

    public static List<Datum> masterSearchList = null;
    public static ShopDetail shopDetail;

    public static List<Datum> getMasterSearchList() {
        return masterSearchList;
    }

    public static void setMasterSearchList(List<Datum> masterSearchList) {
        AppConstants.masterSearchList = masterSearchList;
    }

    public static ShopDetail getShopDetail() {
        return shopDetail;
    }

    public static void setShopDetail(ShopDetail shopDetail) {
        AppConstants.shopDetail = shopDetail;
    }
}
