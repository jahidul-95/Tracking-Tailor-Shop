package com.mist.jahidulislam.toptailors;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.FunDapterFilter;
import com.amigold.fundapter.interfaces.ItemClickListener;
import com.google.gson.GsonBuilder;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse, View.OnClickListener {
    //for list view
    final String TAG = this.getClass().getName();
    private ArrayList<Tailor> tailorList ;
    private ListView lvTailor;
    private EditText searchView;
    //private DrawerLayout mDrawerLayout;
    private TextView txtViewEmail;
    private Button btnLogOut;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
   private String email;
    NavigationView navigationView;
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtViewEmail=(TextView)findViewById(R.id.textViewLoginEmail);
        btnLogOut=(Button)findViewById(R.id.buttonLOgOut);
        searchView=(EditText)findViewById(R.id.searchViewMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tracking Tailors");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray_orange)));

        pref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        Log.d(TAG, pref.getString("Email", ""));
        Log.d(TAG, pref.getString("Password", ""));
        email =pref.getString("Email","");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();


        if(email.matches("")){
            p.setAnchorId(View.NO_ID);
            fab.setLayoutParams(p);
            fab.setVisibility(View.INVISIBLE);
        }
        else {
            fab.setVisibility(View.VISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent in = new Intent(MainActivity.this,AddFavouriteActivity.class);
                startActivity(in);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(getResources().getColor(R.color.WhiteSmoke));
        //set email for login
        View header= navigationView.getHeaderView(0);

        txtViewEmail=(TextView)header.findViewById(R.id.textViewLoginEmail);
        btnLogOut=(Button)header.findViewById(R.id.buttonLOgOut);

        //set email for login

        txtViewEmail.setText(email);
        if(email.matches("")){
            btnLogOut.setText("Sign In");
        }


        btnLogOut.setOnClickListener(this);




        // For List View
        if(isNetworkAvailable()) {

    /* DO WHATEVER YOU WANT IF INTERNET IS AVAILABLE */
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            PostResponseAsyncTask taskRead= new PostResponseAsyncTask(MainActivity.this,this);

            taskRead.execute("http://jahidul.netau.net/list.php");

        } else {
             builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("No Internet");
            builder.setMessage("Internet is required. Please Retry.");

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });

            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    builder.setCancelable(false);

                }
            });
            AlertDialog dialog = builder.create(); // calling builder.create after adding buttons
            dialog.show();
            Toast.makeText(this, "Network Unavailable!", Toast.LENGTH_LONG).show();
        }



        if(email.matches("")){
            hideItem();
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent= new Intent(MainActivity.this,SearchByArea.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_top) {
            getSupportActionBar().setTitle("Top Tailors");

        } else if (id == R.id.nav_gents) {
            Intent intent = new Intent(MainActivity.this, GentsTailorList.class);
            startActivity(intent);

        } else if (id == R.id.nav_ladies) {

            Intent intent = new Intent(MainActivity.this,LadiesTailorList.class);
            startActivity(intent);


        } else if (id == R.id.nav_favourite) {
            Intent intent = new Intent(MainActivity.this,FavouriteActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(MainActivity.this,AddAccountActivity.class);
            startActivity(intent);


        }else if (id == R.id.nav_share) {
            Intent intent = new Intent(MainActivity.this,ShareActivity.class);
            startActivity(intent);


        }else if (id == R.id.nav_help) {
            Intent intent = new Intent(MainActivity.this,HelpActivity.class);
            startActivity(intent);


        }else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(MainActivity.this,FeedBackActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //for List View

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
                Intent intent= new Intent(MainActivity.this,GentsTailorDetails.class);
                intent.putExtra("title", TailorTitle[position]);
                intent.putExtra("brand", TailorBrand[position]);
                intent.putExtra("country", TailorCountry[position]);
                intent.putExtra("city", TailorCity[position]);
                intent.putExtra("genere", TailorGenere[position]);
                intent.putExtra("address",TailorAddress[position]);
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
                TailorBrand[position] =tailor.Brand;

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


        FunDapter<Tailor>funDapter = new FunDapter<>(MainActivity.this,tailorList,R.layout.custom_list,dictionary);
        lvTailor = (ListView) findViewById(R.id.lvTailor);
        lvTailor.setAdapter(funDapter);
        initTextFilter(funDapter);
        funDapter.setAlternatingBackground(R.color.gray_orange, R.color.Orange);
    }

    @Override
    public void onClick(View v) {

        if (email.matches("")) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        } else {
            editor = pref.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);

        }


    }

    private void hideItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_favourite).setVisible(false);
        nav_Menu.findItem(R.id.nav_feedback).setVisible(false);
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
                    Log.d(TAG, filterConstraint.toLowerCase());
                    if (tailor.Title.toLowerCase().contains(filterConstraint.toLowerCase())) {
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
