package com.example.root.tcpapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = this.findViewById(android.R.id.content).getRootView();

        Button button = findViewById(R.id.send_button);
        final EditText editText = findViewById(R.id.input);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TCPsend(v).execute(String.valueOf(editText.getText()));
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

    class TCPsend extends AsyncTask<String, Void, String> {



        private View mainView;
        private String message="";

        public TCPsend(View mainActivity) {
            this.mainView = mainActivity;

        }

        protected String doInBackground(String... send) {

            String sentence;
            String modifiedSentence="";
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = null;
            try {
                clientSocket = new Socket("10.25.69.139", 6789);
                //Socket clientSocket = new Socket(InetAddress.getLocalHost().getHostAddress(), 1234);

                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Sending string...");
                sentence = send[0];
                outToServer.writeBytes(sentence + '\n');
                modifiedSentence = inFromServer.readLine();
                System.out.println("FROM SERVER: " + modifiedSentence);

            } catch (IOException e) {
                e.printStackTrace();
            }

            message = modifiedSentence;

            return modifiedSentence;

        }

        protected void onPostExecute(String message) {
            setViews(this.message);
        }
    }

}
