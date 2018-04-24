package sb5.cs309.nextgen911;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.ArrayList;

import static sb5.cs309.nextgen911.MainMenu.idKey;
import static sb5.cs309.nextgen911.MainMenu.requestCode;
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
    volatile boolean stop, hasChanged, firstExit,photoFlag;
    volatile String message;
    private EditText inputBox;
    private ListView list_of_messages;
    HandlerThread readThread;

    public static Context getAppContext() {
        return Text911Activity.context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text911);
        Text911Activity.context = getApplicationContext();
        readThread = new HandlerThread("");
        readThread.start();

        initMessages();
        startCamera();
    }

    @Override
    protected void onStart(){
        super.onStart();
        connected = false;
        stop = false;
        hasChanged = false;
        firstExit = true;
        photoFlag = false;
        message = "";

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
                    else if(photoFlag || new_message.contains("<Photo>")){
                        synchronized (this){
                            if(photoFlag == false) {
                                int end = new_message.indexOf("</Photo>");
                                if(end != -1){
                                    message = "<PHOTO>";
                                    photoFlag = false;
                                }
                            }
                            photoFlag = true;
                            int end = new_message.indexOf("</Photo>");
                            if(end != -1){
                                message = "";
                                photoFlag = false;
                            }

                        }
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
                background_handler.postDelayed(background_handlerTask, 25);
            }
        };

        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {
                if(!stop) {
                    synchronized (this) {
                        if (hasChanged) {
                            adapter.add(message);
                            message = "";
                            hasChanged = false;
                            adapter.notifyDataSetChanged();
                            list_of_messages.setSelection(adapter.getCount() - 1);
                        }
                    }
                }else{
                    m_handler.removeCallbacks(m_handlerTask);
                }
                m_handler.postDelayed(m_handlerTask, 50);
            }
        };


        getServerIP();
    }

    private void createClient() {
        connection = new TCP_Client(6789, "proj-309-sb-5.cs.iastate.edu", sharedPreferences.getString(idKey, "0"), serverIP);
        if(!serverIP.equals("-1") && !serverIP.equals("")) {
            connected = true;
            connection.startConnection();
            background_handlerTask.run();
            m_handlerTask.run();
        }
    }


    @Override
    public void onBackPressed(){
        if(connected)
            connection.stopConnection();
        stop = true;
        super.onBackPressed();
        finish();
    }

    /**
     * Find the IP of the chat server to connect to
     */
    public void getServerIP() {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                serverIP = response;
                if(serverIP.equals("-1") || serverIP.equals("")){
                    adapter.add("All Operators Busy");
                    adapter.notifyDataSetChanged();
                }else {
                    createClient();
                }
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

    private void startCamera(){
        Permissions.requestPermissions(this, requestCode);

        FloatingActionButton capturedImageButton = findViewById(R.id.photo_button);
        capturedImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            String photo = ImageHandler.encodeBase64(bitmap);
            connection.send("<Photo>\n" + photo + "</Photo>");
        }
    }
}


