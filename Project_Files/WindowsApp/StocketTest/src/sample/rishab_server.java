package sample;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class rishab_server {

    static final int PORT = 1978;

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

            ArrayList<EchoThread> echoThreads = new ArrayList<>();
            r.addConnection(socket);
            // new thread for a client
            new EchoThread(socket,r).start();
        }
    }

    private static ArrayList<Socket> sList;

    public static class EchoThread extends Thread {
        protected Socket socket;
        protected Responder r;

        public EchoThread(Socket clientSocket,Responder r) {
            this.socket = clientSocket;
            sList.add(socket);
            this.r = r;
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
                        r.removeConnection(socket);
                        return;
                    } else {
                        r.echo(line);
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

            DataOutputStream out = null;
            for(Socket s: sList){
                out = new DataOutputStream(s.getOutputStream());
                out.writeBytes(line + "\n\r");
                out.flush();
            }
        }
    }
}
