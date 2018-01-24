package com.mist.jahidulislam.toptailors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GentsTailorDetails extends AppCompatActivity {
    TextView txtTitle,txtBrand,txtCountry,txtCity,txtGenere,txtAddress,txtEmail,txtPhone,txtTotalRating,txtTotalUser;

    ImageButton imageButton ;
    Button button;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String TAG = this.getClass().getName();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gents_tailor_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tailor Details");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray_orange)));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        Log.d(TAG, pref.getString("Email", ""));
        Log.d(TAG, pref.getString("Password", ""));
        String email =pref.getString("Email","");
        //get data from GentsTailorList
        Bundle bundle = getIntent().getExtras();

        final String TailorTitle = bundle.getString("title");
        String TailorBrand = bundle.getString("brand");
        String TailorCountry = bundle.getString("country");
        String TailorCity = bundle.getString("city");
        String TailorGenere = bundle.getString("genere");
        String TailorAddress = bundle.getString("address");
        String TailorEmail = bundle.getString("email");
        final String TailorPhone = bundle.getString("phone");
        String TailorTotalRating = bundle.getString("totalRating");
        String TailorTotalUser = bundle.getString("totalUser");
        final String TailorLatitude = bundle.getString("latitude");
        final String TailorLongitude = bundle.getString("longitude");





        //Initialize ui
        txtTitle = (TextView)findViewById(R.id.textTitle);
        txtBrand = (TextView)findViewById(R.id.textViewBrand);
        txtCountry = (TextView)findViewById(R.id.textViewCountry);
        txtCity = (TextView)findViewById(R.id.textViewCity);
        txtGenere = (TextView)findViewById(R.id.textViewGenere);
        txtAddress = (TextView)findViewById(R.id.textViewAddreess);
        txtEmail = (TextView)findViewById(R.id.textViewEmail);
        txtPhone = (TextView)findViewById(R.id.textViewPhone);
        txtTotalRating = (TextView)findViewById(R.id.textViewTotalRating);
        txtTotalUser = (TextView)findViewById(R.id.textViewTotalUser);
        imageButton = (ImageButton)findViewById(R.id.imageGoogleMap);
        button = (Button)findViewById(R.id.rateMe);

        //set data in ui

        txtTitle.setText(TailorTitle);
        txtBrand.setText(TailorBrand);
        txtCountry.setText(TailorCountry);
        txtCity.setText(TailorCity);
        txtGenere.setText(TailorGenere);
        txtAddress.setText(TailorAddress);
        txtEmail.setText(TailorEmail);
        txtPhone.setText(TailorPhone);
        txtTotalRating.setText(TailorTotalRating);
        txtTotalUser.setText(TailorTotalUser);

        if(email.matches("")){
            button.setVisibility(View.INVISIBLE);
        }
        else {
            button.setVisibility(View.VISIBLE);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GentsTailorDetails.this, GoogleMapsActivity.class);
                intent.putExtra("title", TailorTitle);
                intent.putExtra("PlaceLatitude", TailorLatitude);
                intent.putExtra("PlaceLongitude", TailorLongitude);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GentsTailorDetails.this, RatingActivity.class);
                intent.putExtra("title", TailorTitle);

                startActivity(intent);
            }
        });


        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + TailorPhone));
                startActivity(intent);


            }
        });



    }
}
