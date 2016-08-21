package com.evensel.android.fash.commons;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.evensel.android.fash.R;

/**
 * @author Prishanm
 * Common class used for notification
 * through out the app
 */

public class Notifications {

    //No network error dialog
    public AlertDialog showNetworkNotification(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.no_network_error));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();

        return dialog;
    }

    //Http request-response  error dialog
    public AlertDialog showHttpErrorDialog(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();

        return dialog;
    }

    //Input errors - HTTP Requests
    public AlertDialog showHttpInputErrorDialog(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Please fix these errors!");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();

        return dialog;
    }
}
