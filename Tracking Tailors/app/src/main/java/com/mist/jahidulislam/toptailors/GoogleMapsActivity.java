package com.mist.jahidulislam.toptailors;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private  double Latitude ;
    private double Longitude;
    private String TailorTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        //String TailorLatitude = bundle.getString("latitude");
        //String TailorLongitude = bundle.getString("longitude");
        TailorTitle = bundle.getString("title");

        Latitude = Double.parseDouble(bundle.getString("PlaceLatitude"));
        Longitude = Double.parseDouble(bundle.getString("PlaceLongitude"));

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        mMap.getMaxZoomLevel();
        LatLng T = new LatLng(Latitude, Longitude);
        mMap.addMarker(new MarkerOptions().position(T).title(TailorTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(T));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(T,17));




    }

}
