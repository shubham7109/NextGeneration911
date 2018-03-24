package sb5.cs309.nextgen911.ChatServer;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;


public abstract class NetworkConnection {

    private ConnectionThread connThread = new ConnectionThread();
    private Consumer<Serializable> onRecieveCallback;

    public NetworkConnection(Consumer<Serializable> onRecieveCallback){
        this.onRecieveCallback = onRecieveCallback;
        connThread.setDaemon(true);
    }

    public void startConnection() throws Exception{
        connThread.start();
    }

    public void send(Serializable data) throws Exception{
        connThread.out.writeObject(data);
    }

    public void closeConnection() throws Exception{
        connThread.socket.close();
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();


    private class ConnectionThread extends Thread{

        private Socket socket;
        private  ObjectOutputStream out;

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run(){
            try(ServerSocket server = isServer() ? new ServerSocket(getPort()):null;
                Socket socket = isServer() ? server.accept() : new Socket(getIP(),getPort());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){

                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);

                while (true){
                    Serializable data = (Serializable) in.readObject();
                    onRecieveCallback.accept(data);
                }
            }
            catch (Exception e){
                onRecieveCallback.accept("Connection is closed!");
            }
        }
    }

}
