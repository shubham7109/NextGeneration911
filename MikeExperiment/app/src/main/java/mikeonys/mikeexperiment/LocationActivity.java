package mikeonys.mikeexperiment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        CurrentLocationListener locationListener = new CurrentLocationListener();

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) locationListener);
            String locationProvider = LocationManager.GPS_PROVIDER;
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            TextView textView = findViewById(R.id.location);
            textView.setText(lastKnownLocation.toString());
        } catch (SecurityException e) {
            /* Don't know how to handle rejected location services */
        }
    }

    public void updateLocation(View view) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        CurrentLocationListener locationListener = new CurrentLocationListener();

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) locationListener);
            String locationProvider = LocationManager.GPS_PROVIDER;
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            TextView textView = findViewById(R.id.location);
            textView.setText(lastKnownLocation.toString());
        } catch (SecurityException e) {
            /* Don't know how to handle rejected location services */
        }
    }

    public class CurrentLocationListener implements  android.location.LocationListener{
        public void onLocationChanged(Location location) {
            // Dont Care just need to implement
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
            // Dont Care just need to implement
        }

        public void onProviderEnabled(String s) {
            // Dont Care just need to implement
        }

        public void onProviderDisabled(String s) {
            // Dont Care just need to implement
        }
    }
}

