package com.example.root.tcpapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Client client;
    private int portNUmber = 6789;
    private String host = "10.25.69.139";
    private Button setConnection;
    private EditText clientName;
    private EditText roomNumber;
    private Context view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = getApplicationContext();
        setConnection = findViewById(R.id.set_connection);
        clientName = findViewById(R.id.client_name);
        roomNumber = findViewById(R.id.room_number);
        Button button = findViewById(R.id.send_button);
        final EditText editText = findViewById(R.id.input);

        setConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConnection();
            }
        });
    }

    public void setViews(String message){

        TextView textView = findViewById(R.id.output);
        if(message.equals(""))
            textView.setText("Error connecting to the server");
        else
            textView.setText("From server: "+ message);

    }

    private void setConnection() {
        String name = String.valueOf(clientName.getText());
        String number = String.valueOf(roomNumber.getText());
        if(!name.equals("") || !number.equals(""))
        {
            client = new Client(portNUmber,host,name,number, view);
            client.execute();
        }
        else
            Toast.makeText(view, "Name or number is null", Toast.LENGTH_SHORT).show();
    }

}
