package com.mist.jahidulislam.toptailors;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.ItemClickListener;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

public class AddFavouriteActivity extends AppCompatActivity implements AsyncResponse {

    final String TAG = this.getClass().getName();
    private ArrayList<Tailor> tailorList ;

    private ListView lvTailor;

    private StringBuffer result;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    CheckBox checkBox;
    private String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFavouriteActivity.this, FavouriteActivity.class);
                startActivity(intent);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        Log.d(TAG, pref.getString("Email", ""));
        Log.d(TAG, pref.getString("Password", ""));
        email =pref.getString("Email","");



        PostResponseAsyncTask taskRead= new PostResponseAsyncTask(AddFavouriteActivity.this,this);


        taskRead.execute("http://jahidul.netau.net/list.php");

    }


    @Override
    public void processFinish(String s) {
        final String[] TailorTitle = new String[100];
        tailorList = new JsonConverter<Tailor>().toArrayList(s,Tailor.class);
        BindDictionary<Tailor> dictionary = new BindDictionary<Tailor>();

        dictionary.addStringField(R.id.txtTitle, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorTitle[position] =tailor.Title;
                return tailor.Title;
            }
        }).onClick(new ItemClickListener<Tailor>() {
            @Override
            public void onClick(Tailor tailor, int position, View view) {
                HashMap postData = new HashMap();

                postData.put("userEmail", email);
                postData.put("tailorTitle", TailorTitle[position]);
                if (!email.matches("")) {

                    PostResponseAsyncTask task1 = new PostResponseAsyncTask(AddFavouriteActivity.this, postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            Log.d(TAG, s);
                            if (s.contains("success")) {
                                Toast.makeText(AddFavouriteActivity.this, "Your Favourite Tailor Was Added  ", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(AddFavouriteActivity.this, "Tray Again ", Toast.LENGTH_LONG).show();
                            }


                        }
                    });

                    task1.execute("http://jahidul.netau.net/addfavourite.php");
                }

            }
        });


        dictionary.addStringField(R.id.txtRating, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {

                return "" + tailor.Total_rating;
            }
        });

        FunDapter<Tailor> funDapter = new FunDapter<>(AddFavouriteActivity.this,tailorList,R.layout.favourite_list,dictionary);
        lvTailor = (ListView)findViewById(R.id.lvTailor);
        lvTailor.setAdapter(funDapter);
        funDapter.setAlternatingBackground(R.color.Blue_violate, R.color.DarkRed);

    }

}
