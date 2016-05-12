package com.project.markodobrinic1gmailcom.kupnjam.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.markodobrinic1gmailcom.kupnjam.R;

import java.util.HashMap;
import java.util.Map;

public class MainMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Map<String, LatLng> mLocations = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mLocations.put("Billa, Riječka ul. 12", new LatLng(45.7235869, 16.056554499999947));
        mLocations.put("Billa, Banjavčićeva ul. 13", new LatLng(45.8099376, 16.000608300000067));
        mLocations.put("Billa, Savska cesta 58", new LatLng(45.7987687, 15.96141769999997));

        mLocations.put("Konzum, Maksimirska cesta 1", new LatLng(45.8152842, 15.997341699999993));
        mLocations.put("Konzum, Kaptol ul. 1", new LatLng(45.814367, 15.97836940000002));
        mLocations.put("Konzum Av. Marina Držića 4", new LatLng(45.8036237, 15.993598799999972));

        mLocations.put("Spar, Jurišićeva ul. 13", new LatLng(45.812435, 15.980993500000068));
        mLocations.put("Spar, Ul. Ivana Šibla 11", new LatLng(45.77862409999999, 15.988238799999976));
        mLocations.put("Spar, Horvatovac ul. 58", new LatLng(45.82168730000001, 15.991179699999975));

        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(45.8150108,15.981919000000062) , 13) );

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MainMaps.this, marker.getTitle() +  " \n" + marker.getSnippet(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        for (Map.Entry<String, LatLng> entry : mLocations.entrySet()) {
            LatLng value = entry.getValue();
            createMarker(value, entry.getKey());
        }



//        Marker billa2 = mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(45.8099376, 16.000608300000067))
//                .anchor(0.5f, 0.5f)
//                .title("Billa, Banjavčićeva ul. 13")
//                .snippet("Radno vrijeme 07:00-23:00"));
//
//        Marker billa3 = mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(45.7987687, 15.96141769999997))
//                .anchor(0.5f, 0.5f)
//                .title("Billa, Savska cesta 58")
//                .snippet("Radno vrijeme 07:00-23:00"));

    }

    private void createMarker(LatLng value, String title) {
        mMap.addMarker(new MarkerOptions()
                .position(value)
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet("Radno vrijeme 07:00-23:00")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        animateMarker(value);
    }

    public void animateMarker(LatLng latLng)   {
        CameraPosition position = new CameraPosition.Builder()
                .target(latLng)
                .zoom(14)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

    }


}
