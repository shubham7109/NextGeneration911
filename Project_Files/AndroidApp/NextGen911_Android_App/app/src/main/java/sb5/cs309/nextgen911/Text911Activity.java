package sb5.cs309.nextgen911;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;
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
    TCP_Client connection;
    private EditText inputBox;
    private ListView list_of_messages;
    boolean connected;
    Handler m_handler;
    Runnable m_handlerTask;
    boolean stop;

    public static Context getAppContext() {
        return Text911Activity.context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text911);
        Text911Activity.context = getApplicationContext();
        connected = false;

        initMessages();
        getServerIP();

        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {
                if (!stop) {
                    String message = connection.getMessages();
                    if(!message.equals("")) {
                        adapter.add(message);
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    //m_handler.removeCallbacks(m_handlerTask); // cancel run
                }
                m_handler.postDelayed(m_handlerTask, 250);
            }
        };

    }

    private void createClient() {
            connection = new TCP_Client(6789, "proj-309-sb-5.cs.iastate.edu", sharedPreferences.getString(idKey, "0"), serverIP);
            connected = true;
            connection.startConnection();
            m_handlerTask.run();
    }


    @Override
    public void onBackPressed() {
        connection.stopConnection();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        connection.stopConnection();
        super.onDestroy();
    }

    /**
     * Find the IP of the chat server to connect to
     */
    public void getServerIP() {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                serverIP = response;
                serverIP = "test";
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

                LocationServices.updateLocation(getAppContext());
                try {
                    if (connected) {
                        String message = inputBox.getText().toString();
                        inputBox.setText("");
                        connection.send(message);
                    }
                    else{
                        Toast.makeText(getAppContext(), "No Connection", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}


