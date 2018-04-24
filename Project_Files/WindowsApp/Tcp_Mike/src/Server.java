import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Runs a text echo server, expects first message to be username, second to be name of chatroom to join
 * /listall typed before specifying a chatroom lists all chat rooms with at least one person
 * /who type in a chatroom lists everyone currently in the chatroom
 */
public class Server {
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static ArrayList<clientThread> threadList;
    private static HashMap<String, ArrayList<clientThread>> chatRooms;

    public static void main(String args[]) {
        int portNumber = 6789;
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

    private volatile static ArrayList<clientThread> threadList;
    private volatile static HashMap<String, ArrayList<clientThread>> chatRooms;
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
                name = is.readLine().trim();
                if (name.length() != 0) {
                    break;
                }
            }

            while (true) {
                roomID = is.readLine().trim();
                if (roomID.length() != 0 && roomID.charAt(0) != '/') {
                    break;
                }
                if (roomID.equals("/listall")){
                    synchronized (this) {
                        String result = "";
                        for (Map.Entry<String, ArrayList<clientThread>> entry : chatRooms.entrySet()) {
                            String key = entry.getKey();

                            result = result + key + "\n";
                            // do what you have to do here
                            // In your case, another loop.
                        }
                        os.println(result);
                    }
                }
            }

            /* Welcome the new the client. */
            os.println("Connected to Emergency Services\nPlease type your message below\nTo leave enter /quit in a new line");
            synchronized (this) {
                clientName = name;
                chatRoomName = roomID;

                if(chatRooms.containsKey(roomID))
                    chatRooms.get(roomID).add(this);
                else{
                    chatRooms.put(roomID, new ArrayList<clientThread>());
                    chatRooms.get(roomID).add(this);
                }

                for (clientThread c : chatRooms.get(roomID)) {
                    if (c != this)
                        c.os.println("*** " + name + " has entered the chat room ***");
                }
            }

            while (true) {
                String line = is.readLine();
                if (line == null || line.startsWith("/quit")) {
                    break;
                }


                if (line.startsWith("/who")){
                    synchronized (this){
                        String result = "Currently connected: ";
                        for(clientThread c: chatRooms.get(roomID))
                            result = result + " " + c.clientName;
                        os.println(result);
                    }
                    continue;
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
                    if(c != this)
                        c.os.println("*** " + name + " has left ***");
                    else
                        c.os.println("***disconnected***");
                }

                chatRooms.get(roomID).remove(this);
                if(chatRooms.get(roomID).size() == 0)
                    chatRooms.remove(roomID);
            }

            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}