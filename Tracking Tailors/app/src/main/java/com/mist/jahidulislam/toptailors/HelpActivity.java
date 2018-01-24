package com.mist.jahidulislam.toptailors;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    TextView txtResetPassword,txtChangeUserName,txtForGotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Help");
       getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray_orange)));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtResetPassword = (TextView) findViewById(R.id.textViewResentPassword);
        txtChangeUserName = (TextView) findViewById(R.id.textViewChangeUsename);
        txtForGotPassword = (TextView) findViewById(R.id.textViewRecoveryPassword);

        txtResetPassword.setText(getResources().getText(R.string.Help_favourite));
        txtChangeUserName.setText(getResources().getText(R.string.help_local_area));
        txtForGotPassword.setText(getResources().getText(R.string.help_forgot_password));
    }
}
