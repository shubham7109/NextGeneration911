import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Server {
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static ArrayList<clientThread> threadList;
    private static HashMap<String, ArrayList<clientThread>> chatRooms;

    public static void main(String args[]) {
        int portNumber = 2222;
        threadList = new ArrayList<>();
        chatRooms = new HashMap<>();

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }


        while (true) {
            try {
                clientSocket = serverSocket.accept();
                clientThread process = new clientThread(clientSocket, threadList, chatRooms);
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
    private static HashMap<String, ArrayList<clientThread>> chatRooms;
    private String clientName = null;
    private String chatRoomName = null;
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;

    public clientThread(Socket clientSocket, ArrayList<clientThread> threadList, HashMap<String, ArrayList<clientThread>> chatRooms) {
        this.clientSocket = clientSocket;
        this.threadList = threadList;
        this.chatRooms = chatRooms;
    }

    public void run() {

        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            String name;
            String roomID;
            while (true) {
                os.println("Enter your name.");
                name = is.readLine().trim();
                if (name.indexOf('@') == -1) {
                    break;
                } else {
                    os.println("The name should not contain '@' character.");
                }
            }

            while (true) {
                os.println("Enter a chat room name");
                roomID = is.readLine().trim();
                if (roomID.length() != 0) {
                    break;
                } else {
                    os.println("Must enter a room name");
                }
            }

            /* Welcome the new the client. */
            os.println("Welcome " + name
                    + " to chat room: " + roomID + ".\nTo leave enter /quit in a new line.");
            synchronized (this) {
                clientName = "@" + name;
                chatRoomName = roomID;

                if(chatRooms.containsKey(roomID))
                    chatRooms.get(roomID).add(this);
                else{
                    chatRooms.put(roomID, new ArrayList<clientThread>());
                    chatRooms.get(roomID).add(this);
                }

                for (clientThread c : chatRooms.get(roomID)) {
                    if (c != this)
                        c.os.println("*** " + name + " has entered the chat room !!! ***");
                }
            }

            while (true) {
                String line = is.readLine();
                if (line.startsWith("/quit")) {
                    break;
                }
                /* Broadcast it to all other clients. */
                synchronized (this) {
                    for (clientThread c : chatRooms.get(roomID)) {
                        c.os.println("<" + name + "> " + line);
                    }
                }
            }

            synchronized (this) {
                for (clientThread c : chatRooms.get(roomID)) {
                    c.os.println("*** " + name + " is leaving the chat room !!! ***");
                }
            }

            synchronized (this) {
                chatRooms.get(roomID).remove(this);
            }


            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}