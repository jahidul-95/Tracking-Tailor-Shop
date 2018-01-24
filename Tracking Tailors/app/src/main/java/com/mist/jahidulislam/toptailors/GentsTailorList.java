package com.mist.jahidulislam.toptailors;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.BooleanExtractor;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.FunDapterFilter;
import com.amigold.fundapter.interfaces.ItemClickListener;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.channels.AsynchronousCloseException;
import java.util.ArrayList;
import java.util.HashMap;

public class GentsTailorList extends AppCompatActivity implements AsyncResponse{

    final String LOG = "GentsTailorList";
    private ArrayList<Tailor>tailorList ;
    FunDapter<Tailor>funDapter;
    private ListView lvTailor;







    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gents_tailor_list);
        getSupportActionBar().setTitle("Gents Tailor");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray_orange)));


        PostResponseAsyncTask taskRead= new PostResponseAsyncTask(GentsTailorList.this,this);

        taskRead.execute("http://jahidul.netau.net/gents.php");


    }

    @Override
    public void processFinish(String s) {
        final String[] TailorTitle = new String[100];
        final String[] TailorBrand = new String[100];
        final String[] TailorCity = new String[100];
        final String[] TailorCountry = new String[100];
        final String[] TailorGenere = new String[100];
        final String[] TailorAddress = new String[100];
        final String[] TailorEmail = new String[100];
        final String[] TailorPhone = new String[100];
        final String[] TailorTotalUser = new String[100];
        final String[] TailorTotalRating = new String[50];
        final String[] TailorLatitude = new String[50];
        final String[] TailorLongitude = new String[50];


        tailorList = new JsonConverter<Tailor>().toArrayList(s,Tailor.class);
        BindDictionary<Tailor>dictionary = new BindDictionary<Tailor>();

      dictionary.addStringField(R.id.txtTitle, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
             TailorTitle[position] =tailor.Title;
                return tailor.Title;
            }
        }).onClick(new ItemClickListener<Tailor>() {
            @Override
            public void onClick(Tailor tailor, int position, View view) {

                Intent intent = new Intent(GentsTailorList.this, GentsTailorDetails.class);
                intent.putExtra("title", TailorTitle[position]);
                intent.putExtra("brand", TailorBrand[position]);
                intent.putExtra("country", TailorCountry[position]);
                intent.putExtra("city", TailorCity[position]);
                intent.putExtra("genere", TailorGenere[position]);
                intent.putExtra("address", TailorAddress[position]);
                intent.putExtra("phone", TailorPhone[position]);
                intent.putExtra("email", TailorEmail[position]);
                intent.putExtra("totalRating", TailorTotalRating[position]);
                intent.putExtra("totalUser", TailorTotalUser[position]);
                intent.putExtra("latitude", TailorLatitude[position]);
                intent.putExtra("longitude", TailorLongitude[position]);
                startActivity(intent);

            }
        });

        dictionary.addStringField(R.id.tTBrand, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorBrand[position] = tailor.Brand;

                return null;
            }
        });
        dictionary.addStringField(R.id.tTCountry, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorCountry[position] =tailor.Country;
                return null;
            }
        });
        dictionary.addStringField(R.id.tTCity, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorCity[position] =tailor.City;
                return null;
            }
        });
        dictionary.addStringField(R.id.tTGenere, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorGenere[position] =tailor.Genere;

                return null;
            }
        });
        dictionary.addStringField(R.id.tTAddress, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorAddress[position] =tailor.Address;
                return null;
            }
        });
        dictionary.addStringField(R.id.tTEmail, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorEmail[position] =tailor.Email;
                return null;
            }
        });
        dictionary.addStringField(R.id.tTPhone, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorPhone[position] =tailor.Phone;
                return null;
            }
        });
        dictionary.addStringField(R.id.tTTotalRating, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorTotalRating[position] =""+tailor.Average_rating;
                return null;
            }
        });
        dictionary.addStringField(R.id.tTTotalUser, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorTotalUser[position] =""+tailor.Total_user;
                return null;
            }
        });

        dictionary.addStringField(R.id.txtRating, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {

                return ""+ tailor.Total_rating;
            }
        });

        dictionary.addStringField(R.id.tTLatitude, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorLatitude[position] = ""+tailor.Latitude;


                return null;
            }
        });
        dictionary.addStringField(R.id.tTLongitude, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorLongitude[position] = ""+tailor.Longitude;


                return null;
            }
        });

       funDapter = new FunDapter<>(GentsTailorList.this,tailorList,R.layout.custom_list,dictionary);
        lvTailor = (ListView)findViewById(R.id.lvTailor);
        lvTailor.setAdapter(funDapter);
        funDapter.setAlternatingBackground(R.color.gray_orange, R.color.Orange);
        initTextFilter(funDapter);
    }

/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tailor selectTailor = tailorList.get(position);
        Intent in = new Intent(GentsTailorList.this, GentsTailorDetails.class);
        in.putExtra("tailor", (Serializable) selectTailor);
        startActivity(in);
    }*/
private void initTextFilter(final FunDapter<Tailor> adapter) {

    // init the filter in the adapter
    adapter.initFilter(new FunDapterFilter<Tailor>() {

        @Override
        public ArrayList<Tailor> filter(String filterConstraint,
                                         ArrayList<Tailor> originalList) {

            ArrayList<Tailor> filtered = new ArrayList<>();

            for (int i = 0; i < originalList.size(); i++) {
                Tailor tailor = originalList.get(i);
                Log.d(LOG, filterConstraint.toLowerCase());
                if (tailor.Title.toLowerCase().contains(filterConstraint.toLowerCase())) {
                    filtered.add(tailor);
                }
            }

            return filtered;
        }
    });


}


}
