package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.databinding.ActivityLokasiArahBinding;
import com.nurali.kampunggerabah.databinding.ActivityTransaksiDetailBinding;

public class LokasiArahActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityLokasiArahBinding binding;
    private GoogleMap mMap;

    String lat, longi, nama;
    private MarkerOptions place;

    MaterialButton arahBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_arah);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lat = getIntent().getStringExtra("lat");
        longi = getIntent().getStringExtra("longi");
        nama = getIntent().getStringExtra("nama");

        arahBtn = findViewById(R.id.arahBtn);

        place = new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(longi))).title(nama);

        arahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat + "," + longi + "&mode=1"));
                i.setPackage("com.google.android.apps.maps");

                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Marker ma = mMap.addMarker(place);
        ma.showInfoWindow();

        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(longi));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(15.0f);
    }
}