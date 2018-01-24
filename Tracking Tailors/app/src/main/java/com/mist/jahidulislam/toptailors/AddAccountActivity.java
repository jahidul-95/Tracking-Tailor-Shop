package com.mist.jahidulislam.toptailors;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class AddAccountActivity extends AppCompatActivity implements View.OnClickListener {
    final String LOG ="AddAccountActivity";

    AutoCompleteTextView userName,userEmail;
    EditText userPassword ,conformPassWord;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setTitle("Add account");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Orange)));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        userName=(AutoCompleteTextView)findViewById(R.id.autoUserName);
        userEmail=(AutoCompleteTextView)findViewById(R.id.autoUserEmail);
        userPassword = (EditText)findViewById(R.id.password);
        conformPassWord=(EditText)findViewById(R.id.ConformPassword);

        btLogin= (Button)findViewById(R.id.user_register_in_button);

        btLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        HashMap postData =new HashMap();
        String Name = userName.getText().toString();
        String Email= userEmail.getText().toString();
        String Password = userPassword.getText().toString();
        String ConPassword=conformPassWord.getText().toString();


        if(validate()){

            postData.put("User_name", Name);
            postData.put("user_email", Email);
            postData.put("User_password", Password);


            PostResponseAsyncTask taskWrite = new PostResponseAsyncTask(AddAccountActivity.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    Log.d(LOG, s);
                    if (s.contains("success")) {
                        Toast.makeText(AddAccountActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddAccountActivity.this, "Check Your Information", Toast.LENGTH_LONG).show();
                    }

                }
            });
            taskWrite.execute("http://jahidul.netau.net/registration.php");
        }
        else {
            Toast.makeText(getBaseContext(), "SIGN UP FAILED", Toast.LENGTH_LONG).show();

        }



    }

    public boolean validate() {
        boolean valid = true;
        String Name = userName.getText().toString();
        String Email= userEmail.getText().toString();
        String Password = userPassword.getText().toString();
        String ConPassword=conformPassWord.getText().toString();

        if (Name.isEmpty() || Name.length() < 3) {
            userName.setError("at least 3 characters");
            valid = false;
        } else {
            userName.setError(null);
        }

        if (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            userEmail.setError("enter a valid email address");
            valid = false;
        } else {
            userEmail.setError(null);
        }

        if (Password.isEmpty() || Password.length() < 4 || Password.length() > 10) {
            userPassword.setError("between 4 and 10 alphanumeric characters");
            if (Password.matches(userPassword.getText().toString()) != ConPassword.matches(userPassword.getText().toString())) {
                conformPassWord.setError("Password don't match");
                valid = false;
            }
            valid = false;
        }else {
            userPassword.setError(null);
        }

        return valid;
    }

}
