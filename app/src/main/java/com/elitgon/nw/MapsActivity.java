package com.elitgon.nw;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.elitgon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.elitgon.nw.network.model.Event;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        event = (Event) getIntent().getSerializableExtra("event");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng eventLocation = new LatLng(event.getPlace().getLatitude(), event.getPlace().getLongitude());
        mMap.addMarker(new MarkerOptions().position(eventLocation).title(event.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 15.0f));
    }
}
