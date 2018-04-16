package sample;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class rishab_server {

    static final int PORT = 1234;
    public static ArrayList<EchoThread> echoThreads = new ArrayList<>();

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Responder r = new Responder();

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }


            echoThreads.add(new EchoThread(socket));
            // new thread for a client
            echoThreads.get(echoThreads.size()-1).start();
        }
    }

    public static void getMessage(String message) throws IOException {
        for (EchoThread echo: echoThreads) {
            echo.sendMessage(message);
        }
    }

    private static ArrayList<Socket> sList;

    public static class EchoThread extends Thread {
        protected Socket socket;

        public EchoThread(Socket clientSocket) {
            this.socket = clientSocket;
            sList.add(socket);
        }
        
        public void sendMessage(String line) throws IOException {
            DataOutputStream out = null;
                out = new DataOutputStream(socket.getOutputStream());
                out.writeBytes(line + "\n\r");
                out.flush();
        }

        public void run() {
            InputStream inp = null;
            BufferedReader brinp = null;
            DataOutputStream out = null;
            try {
                inp = socket.getInputStream();
                brinp = new BufferedReader(new InputStreamReader(inp));
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                return;
            }
            String line;
            while (true) {
                try {
                    line = brinp.readLine();
                    if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                        socket.close();
                        return;
                    } else {
                        getMessage(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private static class Responder{

        public Responder(){
            sList = new ArrayList<>();
        }

        public void addConnection(Socket s){
            sList.add(s);
        }
        public void removeConnection(Socket s){
            sList.remove(s);
        }

        public void echo(String line) throws IOException {
            
        }
    }
}
