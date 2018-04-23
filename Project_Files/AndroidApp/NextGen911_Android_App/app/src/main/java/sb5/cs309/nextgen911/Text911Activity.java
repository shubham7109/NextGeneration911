package sb5.cs309.nextgen911;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
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
    TCP_Client connection;
    boolean connected;
    Handler m_handler, background_handler;
    Runnable m_handlerTask, background_handlerTask;
    volatile boolean stop, hasChanged, firstExit;
    volatile String message;
    private EditText inputBox;
    private ListView list_of_messages;

    public static Context getAppContext() {
        return Text911Activity.context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text911);
        Text911Activity.context = getApplicationContext();
        connected = false;
        hasChanged = false;
        firstExit = true;
        message = "";


        HandlerThread readThread = new HandlerThread("");
        readThread.start();
        background_handler = new Handler(readThread.getLooper());
        background_handlerTask = new Runnable() {
            @Override
            public void run() {
                if (!stop) {
                    String new_message = connection.getMessages();
                    if (new_message.contains("$")) {
                        //
                    } else if (new_message.contains("has left") && firstExit) {
                        firstExit = false;
                    }
                    else if(new_message.contains("has left")){
                        connection.stopConnection();
                        stop = true;
                        message = message + "\n" + "***Disconnected***";
                        hasChanged = true;
                    }
                    else if (!new_message.equals("")) {
                        synchronized (this) {
                            message = message + "\n" + new_message;
                            hasChanged = true;
                        }
                    }

                } else {
                    background_handler.removeCallbacks(background_handlerTask); // cancel run
                }
                background_handler.postDelayed(background_handlerTask, 200);
            }
        };

        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    if (hasChanged) {
                        adapter.add(message);
                        message = "";
                        hasChanged = false;
                    }
                    adapter.notifyDataSetChanged();
                }
                m_handler.postDelayed(m_handlerTask, 200);
            }
        };

        initMessages();
        getServerIP();

    }

    private void createClient() {
        connection = new TCP_Client(6789, "proj-309-sb-5.cs.iastate.edu", sharedPreferences.getString(idKey, "0"), "1");
        connected = true;
        connection.startConnection();
        background_handlerTask.run();
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
                    } else {
                        Toast.makeText(getAppContext(), "No Connection", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}


