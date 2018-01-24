package com.mist.jahidulislam.toptailors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = this.getClass().getName();
    EditText feedback;
    Button btnFeedback;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray_orange)));




        feedback = (EditText)findViewById(R.id.etSendFeedBack);
        btnFeedback= (Button)findViewById(R.id.btnSendFeedback);
        pref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);



        Log.d(TAG, pref.getString("Email", ""));
        Log.d(TAG, pref.getString("Password", ""));

        btnFeedback.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        HashMap postData = new HashMap();
        String sendFeedback = feedback.getText().toString();
        String email =pref.getString("Email", "");
        postData.put("userEmail",email);
        postData.put("feedBack", sendFeedback);

            if(!(email.matches("") || sendFeedback.matches(""))) {
                postData.put("userEmail",email);
                postData.put("feedBack", sendFeedback);

                PostResponseAsyncTask taskFeedback = new PostResponseAsyncTask(FeedBackActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(TAG, s);
                        if (s.contains("success")) {
                            Toast.makeText(FeedBackActivity.this, "Your Feedback Successfully Send", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(FeedBackActivity.this, "Tray Again", Toast.LENGTH_LONG).show();
                        }


                    }
                });

                taskFeedback.execute("http://jahidul.netau.net/feedback.php");
            }


    }
}
