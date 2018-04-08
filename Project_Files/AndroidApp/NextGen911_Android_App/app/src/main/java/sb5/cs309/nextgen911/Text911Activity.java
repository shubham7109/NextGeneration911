package sb5.cs309.nextgen911;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static sb5.cs309.nextgen911.MainMenu.idKey;
import static sb5.cs309.nextgen911.MainMenu.sharedPreferences;

/**
 * Contains the controller to send text messages to 911
 */

public class Text911Activity extends AppCompatActivity {

    static Context context;
    public String serverIP;
    ArrayList<String> messageList;
    ArrayAdapter<String> adapter;
    TcpClient connection;
    private EditText inputBox;
    private ListView list_of_messages;
    private boolean connected;

    public static Context getAppContext() {
        return Text911Activity.context;
    }

    /**
     * Initialize message list and chat client connection
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text911);
        Text911Activity.context = getApplicationContext();
        connected = false;

        initMessages();
        getServerIP();
    }

    //TODO
    private void createClient() {
        try {
            connection = new TcpClient(null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Make a POST request to update last user location with most recent device location
     */
    public void updateLocation() {
        String id = sharedPreferences.getString(idKey, "");

        if (id.equals(""))
            return;

        //Make get request
        com.android.volley.Response.Listener<JSONObject> listener = new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LocationTuple loc = getLocation();
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
    public LocationTuple getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean locationPerm = ContextCompat.checkSelfPermission(Text911Activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (locationPerm) {
            DecimalFormat decimalFormat = new DecimalFormat(".###"); // 5 digits gives +- 100 m

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


            requestLocationUpdates(lm, locationListener);
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

    private final void requestLocationUpdates(LocationManager locationManager, LocationListener locationListener) {
        boolean locationPerm = ContextCompat.checkSelfPermission(Text911Activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (locationPerm) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    /**
     * close server on exit
     */
    @Override
    public void onBackPressed() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

    /**
     * Find the IP of the chat server to connect to
     */
    public void getServerIP() {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getAppContext(), response + "", Toast.LENGTH_LONG).show(); //Todo
                serverIP = response;
                serverIP = "10.26.47.247";
                createClient();
            }
        };
        Networking.getOperatorIP(listener);

    }

    private void initMessages() {
        messageList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(Text911Activity.this,
                android.R.layout.simple_list_item_1, messageList);
        inputBox = findViewById(R.id.input);
        list_of_messages = findViewById(R.id.list_of_messages);
        list_of_messages.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = sharedPreferences.getString(idKey, "");
                message += ": ";
                message += inputBox.getText().toString();
                inputBox.setText("");

                messageList.add(message);
                adapter.notifyDataSetChanged();

                updateLocation();
                try {
                    if (connected) {
                        connection.send(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private static class LocationTuple {
        public String lat;
        public String lng;
    }
}


