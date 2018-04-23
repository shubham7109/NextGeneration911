package com.example.mike.tcp_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    Client client;
    EditText text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new Client(6789, "proj-309-sb-5.cs.iastate.edu", "Mike", "test");
        client.startConnection();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(client.getMessages());
        //client.stopConnection();
    }

    public void onClick(View view){
        text = findViewById(R.id.Text);
        String message = text.getText().toString();
        client.send(message);
    }

    @Override
    public void onBackPressed() {
        client.stopConnection();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        client.stopConnection();
        super.onDestroy();
    }
}
