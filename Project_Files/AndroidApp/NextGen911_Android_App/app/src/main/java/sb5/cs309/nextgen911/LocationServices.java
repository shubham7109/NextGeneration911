package sb5.cs309.nextgen911;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


import static sb5.cs309.nextgen911.MainMenu.idKey;

/**
 * Allows for access of location from any context.
 */

public class LocationServices {
    /**
     * Make a POST request to update last user location with most recent device location
     */
    public static void updateLocation(Context context) {
        String id = MainMenu.sharedPreferences.getString(idKey, "");

        if (id.equals(""))
            return;

        //Make get request
        com.android.volley.Response.Listener<JSONObject> listener = new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LocationTuple loc = getLocation(context);
                    response.put("latitude", loc.lat);
                    response.put("longitude", loc.lng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Networking.postPersonalInfo(response);
            }
        };
        Networking.getPersonalInfo(id, listener);
    }

    /**
     * Requests an updated location from the location system manager
     *
     * @return Most recent Lat and Long as a tuple
     */
    public static LocationTuple getLocation(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean locationPerm = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (locationPerm) {
            DecimalFormat decimalFormat = new DecimalFormat(".#####"); // 5 digits gives +- 100 m

            /* An empty listener is needed to update the location */
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };


            requestLocationUpdates(lm, locationListener, context);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            LocationTuple result = new LocationTuple();
            result.lat = latitude + "";
            result.lng = longitude + "";

            return result;
        }

        LocationTuple result = new LocationTuple();
        result.lat = "0";
        result.lng = "0";

        return result;
    }

    private static final void requestLocationUpdates(LocationManager locationManager, LocationListener locationListener, Context context) {
        boolean locationPerm = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (locationPerm) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public static class LocationTuple {
        public String lat;
        public String lng;
    }
}
