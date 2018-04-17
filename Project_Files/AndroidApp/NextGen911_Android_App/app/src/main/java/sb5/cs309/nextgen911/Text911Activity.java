package sb5.cs309.nextgen911;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;

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
            connection = new TcpClient(8082, "proj-309-sb-5.cs.iastate.edu", sharedPreferences.getString(idKey, "0"), serverIP);
        } catch (Exception e) {
            e.printStackTrace();
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
                serverIP = "proj-309-sb-5.cs.iastate.edu";
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

                LocationServices.updateLocation(getAppContext());
                try {
                    if (connected) {
                        connection.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}


