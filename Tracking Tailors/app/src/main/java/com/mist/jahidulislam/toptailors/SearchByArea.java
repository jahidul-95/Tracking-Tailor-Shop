package com.mist.jahidulislam.toptailors;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.FunDapterFilter;
import com.amigold.fundapter.interfaces.ItemClickListener;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchByArea extends AppCompatActivity implements AsyncResponse{

    final String LOG = "SearchByArea";
    private ArrayList<Tailor> tailorList ;
    FunDapter<Tailor> funDapter;
    private ListView lvTailor;
    private EditText searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seaech_by_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search by location");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray_orange)));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView=(EditText)findViewById(R.id.searchViewAre);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        PostResponseAsyncTask taskRead= new PostResponseAsyncTask(SearchByArea.this,this);
        taskRead.execute("http://jahidul.netau.net/list.php");
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
        final String[] TailorTotalRating = new String[100];
        final String[] TailorLatitude = new String[100];
        final String[] TailorLongitude = new String[100];

        tailorList = new JsonConverter<Tailor>().toArrayList(s,Tailor.class);
        BindDictionary<Tailor> dictionary = new BindDictionary<Tailor>();

        dictionary.addStringField(R.id.textViewSearchTitle, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorTitle[position] =tailor.Title;
                return tailor.Title;
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
                TailorCountry[position] = tailor.Country;
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
        dictionary.addStringField(R.id.textViewSearchCity, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorCity[position] =tailor.City;
                return tailor.City;
            }
        });
        dictionary.addStringField(R.id.textViewSearchAddress, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                TailorAddress[position] =tailor.Address;
                return tailor.Address;
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
                TailorTotalUser[position] = "" + tailor.Total_user;
                return null;
            }
        });



        dictionary.addStringField(R.id.textViewSearchTotalRating, new StringExtractor<Tailor>() {
            @Override
            public String getStringValue(Tailor tailor, int position) {
                return ""+tailor.Total_rating;
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

        funDapter = new FunDapter<>(SearchByArea.this,tailorList,R.layout.search_are_list,dictionary);
        lvTailor = (ListView)findViewById(R.id.lvTailor);
        lvTailor.setAdapter(funDapter);
        lvTailor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchByArea.this, GentsTailorDetails.class);
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
       funDapter.setAlternatingBackground(R.color.gray_orange, R.color.gray_blue);


        initTextFilter(funDapter);

    }
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
                    if (tailor.Title.toLowerCase().contains(filterConstraint.toLowerCase()) || tailor.City.toLowerCase().contains(filterConstraint.toLowerCase()) || tailor.Address.toLowerCase().contains(filterConstraint.toLowerCase())) {
                        filtered.add(tailor);
                    }
                }

                return filtered;
            }
        });


        searchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                // now we can use the regular ListView API for filtering:
                adapter.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }


}
