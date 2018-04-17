package operator;

import operator.Models.OperatorModel;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

/**
 * Class to handle the client's socket calls
 * @author Shubham Sharma
 */
public class Client implements Runnable {

    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private static boolean closed = false;
    private int portNumber = 2222;
    private String host = "localhost";

    public Client(OperatorModel operator) {

        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Failed to resolve hostname");
        } catch (IOException e) {
            System.err.println("I/O Error");
        }


        if (clientSocket != null && os != null && is != null) {
            try {
                new Thread(new Client()).start();
                os.println(operator.getFirstName()); // Name of the client
                os.println(operator.getUserName()); //  Name of the room to join
                while (!closed) {
                    os.println(inputLine.readLine().trim());
                }
                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    public Client() {
        // Required constructor
    }

    public void run() {
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
                if (responseLine.indexOf("*** Bye") != -1)
                    break;
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}