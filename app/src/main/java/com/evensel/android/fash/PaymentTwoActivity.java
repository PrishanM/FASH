package com.evensel.android.fash;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Prishan Maduka on 7/19/2016.
 */
public class PaymentTwoActivity extends AppCompatActivity {

    LinearLayout newAddressLayout;
    LinearLayout scrollView;
    FloatingActionButton actionButton;
    boolean actionButtonStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_two_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("PAYMENT DETAILS");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        scrollView = (LinearLayout)findViewById(R.id.idScroll);
        newAddressLayout = (LinearLayout)findViewById(R.id.newDelAddress);
        actionButton = (FloatingActionButton)findViewById(R.id.goNext);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionButtonStatus){
                    actionButtonStatus = false;
                    scrollView.setVisibility(View.VISIBLE);
                    newAddressLayout.setVisibility(View.GONE);
                    actionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_up));
                }else{
                    actionButtonStatus = true;
                    scrollView.setVisibility(View.GONE);
                    newAddressLayout.setVisibility(View.VISIBLE);
                    actionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_down));
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioOldAddres:
                if (checked)
                    newAddressLayout.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    actionButton.setVisibility(View.GONE);
                    break;
            case R.id.radioNewAddress:
                if (checked)
                    newAddressLayout.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    actionButton.setVisibility(View.VISIBLE);
                    break;
        }
    }
}
