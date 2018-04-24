package com.example.mike.tcp_demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Client client;
    EditText text;
    Button button;
    TextView messageBox;
    Handler m_handler;
    Runnable m_handlerTask;
    boolean stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stop = false;
        messageBox = findViewById(R.id.textView);
        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {
                if (!stop) {
                    String messages = messageBox.getText().toString();
                    messages += client.getMessages();
                    messageBox.setText(messages);

                } else {
                    m_handler.removeCallbacks(m_handlerTask); // cancel run
                }
                m_handler.postDelayed(m_handlerTask, 1000);
            }
        };


        client = new Client(6789, "proj-309-sb-5.cs.iastate.edu", "Mike", "test");
        client.startConnection();
        m_handlerTask.run();

        //client.stopConnection();
    }

    public void onClick(View view) {
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
