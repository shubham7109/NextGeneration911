package sb5.cs309.nextgen911;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

import sb5.cs309.nextgen911.ChatServer.Client;

import static sb5.cs309.nextgen911.MainMenu.idKey;
import static sb5.cs309.nextgen911.MainMenu.sharedPreferences;

public class Text911Activity extends AppCompatActivity {

    private EditText inputBox;
    static Context context;
    private ListView list_of_messages;
    ArrayList<String> messageList;
    ArrayAdapter<String> adapter;
    Client clientConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text911);
        Text911Activity.context = getApplicationContext();

        messageList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(Text911Activity.this,
                android.R.layout.simple_list_item_1, messageList);

        inputBox = findViewById(R.id.input);
        list_of_messages = findViewById(R.id.list_of_messages);
        list_of_messages.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        clientConnection = createClient();

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
            }
        });


    }

    public static Context getAppContext() {
        return Text911Activity.context;
    }


    private Client createClient() {
        Consumer<Serializable> onRecieveCallback = new myConsumer();
        Client c = new Client("10.64.25.147", 5555, onRecieveCallback);
        return c;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public class myConsumer implements Consumer<Serializable> {

        @Override
        public void accept(Serializable serializable) {
            messageList.add(serializable.toString());
            adapter.notifyDataSetChanged();
        }
    }

    public void updateLocation() {
        String id = sharedPreferences.getString(idKey, "");

        if (id.equals(""))
            return;

        //Make get request
        AppController.VolleyResponseListener listener = new AppController.VolleyResponseListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LocationTuple loc = getLocation();
                    response.put("latitude", loc.lat);
                    response.put("longitude", loc.lng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Networking.postPersonalInfo(response, AppController.getInstance().getRequestQueue());
            }
        };
        Networking.getPersonalInfo(id, listener, AppController.getInstance().getRequestQueue());
    }

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

    public final void requestLocationUpdates(LocationManager locationManager, LocationListener locationListener) {
        boolean locationPerm = ContextCompat.checkSelfPermission(Text911Activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (locationPerm) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private static class LocationTuple {
        public String lat;
        public String lng;
    }
}


