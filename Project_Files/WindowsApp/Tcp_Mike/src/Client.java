import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client implements Runnable {

    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;
    private volatile boolean closed = false;
    private ArrayList<String> messages;


    public Client(int port, String host, String userName, String chatRoomName) {
        messages = new ArrayList<>();
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
            new Thread(this).start();
        }
        sendMessage(userName);
        sendMessage(chatRoomName);
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
}