package com.evensel.android.fash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Prishan Maduka on 7/9/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView forgotPassword,register;
    Button btnLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_layout);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("LOGIN");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);

        initUI();
    }

    private void initUI() {

        forgotPassword = (TextView)findViewById(R.id.txtForgotPassword);
        register = (TextView)findViewById(R.id.txtRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        register.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){
            Intent loginIntent = new Intent(LoginActivity.this,ProfileActivity.class);
            startActivity(loginIntent);
        }else if(v.getId()==R.id.txtRegister){
            Intent intent = new Intent(LoginActivity.this,UserRegistrationStepOneActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.txtForgotPassword){

        }
    }
}
