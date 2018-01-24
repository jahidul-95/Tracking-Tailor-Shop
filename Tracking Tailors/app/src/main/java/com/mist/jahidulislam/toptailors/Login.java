package com.mist.jahidulislam.toptailors;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {

    final String LOG =this.getClass().getName();
    Button btnLogin;
    EditText etEmail,etPassword;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("Sign in");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Orange)));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        btnLogin = (Button) findViewById(R.id.btSing_in);
        etEmail = (EditText) findViewById(R.id.userEmail);
        etPassword = (EditText) findViewById(R.id.userPassword);
        btnLogin.setOnClickListener(this);
        etEmail.setError(null);
        etPassword.setError(null);

        pref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        editor = pref.edit();
        String email=pref.getString("Email","");
        String password=pref.getString("Password","");
        HashMap postData = new HashMap();

        postData.put("userEmail", email);
        postData.put("userPassword", password);


        if(!(email.equals("") && password.equals(""))){

            PostResponseAsyncTask task1 = new PostResponseAsyncTask(Login.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    Log.d(LOG, s);
                    if (s.contains("success")) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Tray Again ", Toast.LENGTH_LONG).show();
                    }


                }
            });

            task1.execute("http://jahidul.netau.net/login.php");



        }



    }


    @Override
    public void onClick(View v){
        HashMap postData = new HashMap();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));

        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));

        }
        if(!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))){

            postData.put("userEmail", email);
            postData.put("userPassword", password);


            PostResponseAsyncTask task1 = new PostResponseAsyncTask(Login.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    Log.d(LOG, s);
                    if (s.contains("success")) {
                        Toast.makeText(Login.this, "Successfully Login ", Toast.LENGTH_LONG).show();
                        editor.putString("Email", etEmail.getText().toString());
                        editor.putString("Password", etPassword.getText().toString());
                        editor.apply();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Wrong password ", Toast.LENGTH_LONG).show();
                    }


                }
            });

            task1.execute("http://jahidul.netau.net/login.php");
        }


    }
}
