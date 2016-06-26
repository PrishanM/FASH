package com.evensel.android.fash.network;

import android.content.Context;
import android.net.ConnectivityManager;

public class DetectNetwork {

	private static Context mContext;
    
    public DetectNetwork(Context context){
        setmContext(context);
    }
    
	public static Context getmContext() {
		return mContext;
	}

	public static void setmContext(Context mContext) {
		DetectNetwork.mContext = mContext;
	}
	
	public static boolean isConnected(){
        ConnectivityManager conManager = (ConnectivityManager) getmContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if(conManager.getActiveNetworkInfo()!=null && conManager.getActiveNetworkInfo().isAvailable() && conManager.getActiveNetworkInfo().isConnected()){
			return true;
		}else{
			return false;
		}
    }
	
}
