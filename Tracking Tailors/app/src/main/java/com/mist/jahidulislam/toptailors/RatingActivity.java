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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class RatingActivity extends AppCompatActivity implements View.OnClickListener {


    final String TAG = this.getClass().getName();

    TextView tailorTitle;
    Bundle bundle;
    RatingBar ratingBar;
    Button btnSubmit;
    private String TailorTitle;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
   // private String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray_orange)));


        pref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);



        Log.d(TAG, pref.getString("Email", ""));
        Log.d(TAG, pref.getString("Password", ""));
      //  String email =pref.getString("Email","");



        tailorTitle=(TextView)findViewById(R.id.textViewRatingTitle);
        ratingBar= (RatingBar)findViewById(R.id.ratingBar);
        btnSubmit= (Button)findViewById(R.id.btnRating);

         bundle = getIntent().getExtras();
        TailorTitle = bundle.getString("title");
        tailorTitle.setText(TailorTitle);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        HashMap postData = new HashMap();
        String rating = String.valueOf(ratingBar.getRating());
        String email =pref.getString("Email", "");


        postData.put("userEmail",email);
        postData.put("Title",bundle.getString("title"));
        postData.put("rate", rating);
        if(email!="" && rating!="") {

            PostResponseAsyncTask task1 = new PostResponseAsyncTask(RatingActivity.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    Log.d(TAG, s);
                    if (s.contains("First")) {
                        Toast.makeText(RatingActivity.this, "Your Rating was updated", Toast.LENGTH_LONG).show();
                    } else if (s.contains("second")) {
                        Toast.makeText(RatingActivity.this, "Rating was Successfully Given", Toast.LENGTH_LONG).show();
                    } else if (s.contains("Third")) {

                        Toast.makeText(RatingActivity.this, "Rating was Successfully Given", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(RatingActivity.this, "try again", Toast.LENGTH_LONG).show();


                    }


                }
            });


            task1.execute("http://jahidul.netau.net/rating.php");
        }
    }
}
