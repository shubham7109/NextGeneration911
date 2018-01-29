package mikeonys.mikeexperiment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

public class LocationActivity extends AppCompatActivity {
    private static final int requestCode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        TextView textView = findViewById(R.id.location);
        textView.setText("Lat: 0.000 Long: 0.000");

    }

    public void getLocation(View view){
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            DecimalFormat decimalFormat = new DecimalFormat(".###"); // 5 digits gives +- 100 m

            /* An empty listener is needed to update the location */
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) { }
                public void onStatusChanged(String provider, int status, Bundle extras) {}
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled(String provider) {}
            };


            requestLocationUpdates(lm, locationListener);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            TextView textView = findViewById(R.id.location);
            textView.setText("Lat: " + decimalFormat.format(latitude) + " Long: " + decimalFormat.format(longitude));

        } else{ // Location Permissions not granted
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
        }
    }

    public final void requestLocationUpdates(LocationManager locationManager, LocationListener locationListener) {
        if (locationManager != null && ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, locationListener);
        }
    }
}

