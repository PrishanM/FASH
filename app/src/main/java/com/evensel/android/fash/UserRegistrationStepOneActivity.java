package com.evensel.android.fash;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.evensel.android.fash.commons.Notifications;
import com.evensel.android.fash.network.JsonRequestManager;
import com.evensel.android.fash.util.UserCreateResponse;
import com.evensel.android.fash.util.UserPersonalInfo;

/**
 * Created by Prishan Maduka on 7/9/2016.
 */
public class UserRegistrationStepOneActivity extends AppCompatActivity {

    private FloatingActionButton btnNext;
    private EditText txtFirstName,txtLastName,txtEmail,txtPassword,txtConfirmPassword;
    //private String[] errors = new String[]
    private ProgressDialog progress;
    private Notifications notifications;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_one);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText("USER REGISTRATION");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);

        initUI();
    }

    private void initUI() {

        btnNext = (FloatingActionButton)findViewById(R.id.goNext);

        notifications = new Notifications();

        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
        txtLastName = (EditText)findViewById(R.id.txtLastName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText)findViewById(R.id.txtConfirmPassword);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateRequestParams()){

                    UserPersonalInfo personalInfo = new UserPersonalInfo();
                    personalInfo.setFirstName(txtFirstName.getText().toString());
                    personalInfo.setLastName(txtLastName.getText().toString());
                    personalInfo.setEmail(txtEmail.getText().toString());
                    personalInfo.setPassword(txtPassword.getText().toString());
                    personalInfo.setConPassword(txtConfirmPassword.getText().toString());

                    registerUserStepOne(personalInfo);

                }

            }
        });
    }

    //Input Validation
    private boolean validateRequestParams() {
        boolean isValid = true;

        //Empty first name
        if(txtFirstName.getText().toString().isEmpty()){
            txtFirstName.setError(getResources().getString(R.string.empty_first_name));
            isValid = false;
        }
        //Empty last name
        if(txtLastName.getText().toString().isEmpty()){
            txtLastName.setError(getResources().getString(R.string.empty_last_name));
            isValid = false;
        }
        //Empty email address & Valid email address
        if(txtEmail.getText().toString().isEmpty()){
            txtEmail.setError(getResources().getString(R.string.empty_email));
            isValid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()){
            txtEmail.setError(getResources().getString(R.string.invalid_email));
            isValid = false;
        }
        //Empty Password & Confirm password match
        if(txtPassword.getText().toString().isEmpty()){
            txtPassword.setError(getResources().getString(R.string.empty_password));
            isValid = false;
        }else if(txtConfirmPassword.getText().toString().isEmpty()){
            txtConfirmPassword.setError(getResources().getString(R.string.empty_confirm_password));
            isValid = false;
        }else if(!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
            txtConfirmPassword.setError(getResources().getString(R.string.invalid_confirm_password));
            isValid = false;
        }

        return isValid;

    }

    private void registerUserStepOne(UserPersonalInfo info) {
        progress = ProgressDialog.show(this, null,
                "Saving Data", true);
        JsonRequestManager.getInstance(this).registerUser(getResources().getString(R.string.live_url) + "user",info, registerUserCallback);
    }

    //Response callback for "User Registration - Personal Info"
    JsonRequestManager.registerUserRequest registerUserCallback = new JsonRequestManager.registerUserRequest() {
        @Override
        public void onSuccess(UserCreateResponse response) {
            if(progress!=null)
                progress.dismiss();
            if(response.getSuccessful()){
                Intent intent = new Intent(UserRegistrationStepOneActivity.this,UserRegistrationStepTwoActivity.class);
                startActivity(intent);
            }else{
                String errors = response.getMessage().replaceAll("\\.","\n");
                alertDialog = notifications.showHttpErrorDialog(UserRegistrationStepOneActivity.this,errors);
                alertDialog.show();
            }
        }

        @Override
        public void onError(UserCreateResponse response) {
            if(progress!=null)
                progress.dismiss();
            String errors = response.getMessage().replaceAll("\\.","\n");
            alertDialog = notifications.showHttpInputErrorDialog(UserRegistrationStepOneActivity.this,errors);
            alertDialog.show();
        }
    };
}
