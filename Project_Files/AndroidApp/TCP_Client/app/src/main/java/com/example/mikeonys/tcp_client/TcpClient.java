package com.example.mikeonys.tcp_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;
import android.widget.TextView;

public class TcpClient  {

    private static final int SERVERPORT = 8082;
    private Socket socket;
    private String SendString;
    public boolean sendDone = true;
    public TextView debugTV;

    private void WriteTextView(String txt) {
        if (debugTV != null) {
            debugTV.append(txt);
        }
    }

    public TcpClient() {

        WriteTextView("Create TCP Client n");
        SendString = "Hello, this is an android client...";

        InetAddress serverAddr;
        try {
            serverAddr = InetAddress.getByName("proj-309-sb-5.cs.iastate.edu");

            Log.d("TCP", "C: Connecting...");

            try {
                socket = new Socket(serverAddr, SERVERPORT);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void TCPClientClose() {
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendString(String s) {
        WriteTextView("Send: "+s+" n");

        try {
            Log.d("TCP", "C: Sending: " + SendString);
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);

            out.println(s);
            Log.d("TCP", "C: Sent.");
            Log.d("TCP", "C: Done.");
        } catch (Exception e) {
            Log.e("TCP", "S: Error", e);
        }
    }

    public String readString() {
        BufferedReader bufferedReader;
        int anzahlZeichen = 0;
        String nachricht = "";
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            char[] buffer = new char[200];
            anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
            nachricht = new String(buffer, 0, anzahlZeichen);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return nachricht;
    }

}
