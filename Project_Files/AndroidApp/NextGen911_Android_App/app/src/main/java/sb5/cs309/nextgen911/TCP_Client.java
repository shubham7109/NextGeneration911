package sb5.cs309.nextgen911;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TCP_Client {

    public static Socket clientSocket = null;
    public static PrintStream os = null;
    public static DataInputStream is = null;
    public volatile boolean closed = false;
    public int port;
    public String host;
    public String userName;
    public String chatRoomName;
    public ArrayList<String> messages;

    public TCP_Client(int port, String host, String userName, String chatRoomName) {
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

    public void stopConnection() {
        StopConnection conn = new StopConnection();
        conn.execute(this);
    }

    public void send(String message) {
        Message m = new Message(this, message);
        new SendMessage().execute(m);
    }

    public String getMessages() {
        GetMessageTask task = new GetMessageTask();
        task.execute(this);


        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "Failed in client";

    }
}

class StartConnection extends AsyncTask<TCP_Client, Void, Void> {

    @Override
    protected Void doInBackground(TCP_Client... clients) {
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

class StopConnection extends AsyncTask<TCP_Client, Void, Void> {

    @Override
    protected Void doInBackground(TCP_Client... clients) {


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

class SendMessage extends AsyncTask<Message, Void, Void> {

    @Override
    protected Void doInBackground(Message... messages) {
        TCP_Client client = messages[0].c;
        String message = messages[0].m;

        if (message.equals("/quit"))
            return null;

        if (client.closed == false) {
            client.os.println(message);
        }

        return null;
    }
}


class Message {
    public TCP_Client c;
    public String m;

    public Message(TCP_Client client, String message) {
        c = client;
        m = message;
    }
}

class GetMessageTask extends AsyncTask<TCP_Client, Void, String> {

    @Override
    protected String doInBackground(TCP_Client... clients) {
        try {
            return clients[0].is.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed in Async";
    }
}

