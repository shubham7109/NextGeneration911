package com.example.root.tcpapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client extends AsyncTask {

    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;
    private volatile boolean closed = false;
    private ArrayList<String> messages;
    private int port;
    private String host;
    private String userName;
    private String chatRoomName;
    private Context mainView;

    public Client(int port, String host, String userName, String chatRoomName, Context mainView) {
        this.port = port;
        this.host = host;
        this.userName = userName;
        this.chatRoomName = chatRoomName;
        this.mainView = mainView;
        messages = new ArrayList<>();
    }

    public void run() {
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                messages.add(responseLine);
                if (responseLine.indexOf("***disconnected***") != -1)
                    break;

            }
            closed = true;
            os.close();
            is.close();
            clientSocket.close();

        } catch (IOException e) {
            System.err.println("IOException in RUN:  " + e);
        }
    }

    public void sendMessage(String message) {
        if (closed) {
            closeConnection();
            System.err.println("Connection closed");
        } else {
            os.println(message);
        }
    }

    public void closeConnection() {
        sendMessage("/quit");
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            clientSocket = new Socket(host, port);
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Failed to resolve hostname");
        } catch (IOException e) {
            System.err.println("I/O Error");
        }

        if (clientSocket != null && os != null && is != null) {
            new Thread().start();
        }
        sendMessage(userName);
        sendMessage(chatRoomName);

        UiThread runOnUiThread;
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(mainView, "Connection established", Toast.LENGTH_SHORT).show();
            }
        });




        return null;
    }

    private void runOnUiThread(Runnable runnable) {
    }
}