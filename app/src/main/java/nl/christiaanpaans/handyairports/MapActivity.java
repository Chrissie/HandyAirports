package nl.christiaanpaans.handyairports;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Airports airport;
    private String flag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int index = intent.getIntExtra("AIRPORT_INDEX", -1);
        if(index != -1) {
            try {
                flag = intent.getStringExtra("AIRPORT_FLAG");
                airport = AirportFactory.getInstance().getAirports().get(index);
            } catch (Error e) {
                Log.e("INDEX_ERROR", "Error indexing or parsing Person on the ArrayList");
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(flag + " " + airport.getMunicipality() + " - " + airport.getIcao());
        setContentView(R.layout.activity_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                navigateUpFromSameTask does not restore previous view
//                NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng myAirportLocation = new LatLng(airport.getLatitude(), airport.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(myAirportLocation)
                .title(airport.getName()));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myAirportLocation));
    }
}
