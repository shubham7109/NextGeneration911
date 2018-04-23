package com.example.mike.tcp_demo;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    public static Socket clientSocket = null;
    public static PrintStream os = null;
    public static DataInputStream is = null;
    public volatile boolean closed = false;
    public int port;
    public String host;
    public String userName;
    public String chatRoomName;
    public ArrayList<String> messages;

    public Client(int port, String host, String userName, String chatRoomName) {
        this.port = port;
        this.host = host;
        this.userName = userName;
        this.chatRoomName = chatRoomName;
        messages = new ArrayList<>();
    }

    public void startConnection() {
        StartConnection conn = new StartConnection();
        conn.execute(this);
    }

    public void stopConnection(){
        StopConnection conn = new StopConnection();
        conn.execute(this);
    }

}

class StartConnection extends AsyncTask<Client, Void, Void> {

    @Override
    protected Void doInBackground(Client... clients) {
        try {
            clients[0].clientSocket = new Socket(clients[0].host, clients[0].port);
            clients[0].os = new PrintStream(clients[0].clientSocket.getOutputStream());
            clients[0].is = new DataInputStream(clients[0].clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Failed to resolve hostname");
        } catch (IOException e) {
            System.err.println("I/O Error");
        }

        clients[0].os.println(clients[0].userName);
        clients[0].os.println(clients[0].chatRoomName);

        return null;
    }
}

class StopConnection extends AsyncTask<Client, Void, Void> {

    @Override
    protected Void doInBackground(Client... clients) {


        clients[0].os.println("/quit");
        clients[0].closed = true;
        clients[0].os.close();

        try {
            clients[0].is.close();
            clients[0].clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

