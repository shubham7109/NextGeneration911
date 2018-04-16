import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static ArrayList<clientThread> threadList;

    public static void main(String args[]) {
        int portNumber = 2222;
        threadList = new ArrayList<>();


        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }


        while (true) {
            try {
                clientSocket = serverSocket.accept();
                clientThread process = new clientThread(clientSocket, threadList);
                threadList.add(process);
                process.start();

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}


class clientThread extends Thread {

    private static ArrayList<clientThread> threadList;
    private String clientName = null;
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;

    public clientThread(Socket clientSocket, ArrayList<clientThread> threadList) {
        this.clientSocket = clientSocket;
        this.threadList = threadList;
    }

    public void run() {

        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            String name;
            while (true) {
                os.println("Enter your name.");
                name = is.readLine().trim();
                if (name.indexOf('@') == -1) {
                    break;
                } else {
                    os.println("The name should not contain '@' character.");
                }
            }

            /* Welcome the new the client. */
            os.println("Welcome " + name
                    + " to our chat room.\nTo leave enter /quit in a new line.");
            synchronized (this) {
                clientName = "@" + name;

                for (clientThread c : threadList) {
                    if (c != this)
                        c.os.println("*** " + name + "has entered the chat room !!! ***");
                }
            }

            while (true) {
                String line = is.readLine();
                if (line.startsWith("/quit")) {
                    break;
                }
                /* Broadcast it to all other clients. */
                synchronized (this) {
                    for (clientThread c : threadList) {
                        c.os.println("<" + name + "> " + line);
                    }
                }
            }

            synchronized (this) {
                for (clientThread c : threadList) {
                    c.os.println("*** " + name + " is leaving the chat room !!! ***");
                }
            }

            synchronized (this) {
                threadList.remove(this);
            }


            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}