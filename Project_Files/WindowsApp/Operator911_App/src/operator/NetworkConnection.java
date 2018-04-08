package operator;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Abstract class so setup a network connection
 * @author Shubham Sharma
 */
public abstract class NetworkConnection {

    private ConnectionThread connThread = new ConnectionThread();
    private Consumer<Serializable> onRecieveCallback;

    /**
     * Constructor to set up the connection and
     * start the daemon thread
     * @param onRecieveCallback The onRecieveCallback method
     */
    public NetworkConnection(Consumer<Serializable> onRecieveCallback){
        this.onRecieveCallback = onRecieveCallback;
        connThread.setDaemon(true);
    }

    /**
     * Starts the connection
     * @throws Exception
     */
    public void startConnection() throws Exception{
        connThread.start();
    }

    /**
     * Send data over the connection
     * @param data The data to send
     * @throws Exception
     */
    public void send(Serializable data) throws Exception{
        connThread.out.writeObject(data);
    }

    /**
     * Closes the connection
     * @throws Exception
     */
    public void closeConnection() throws Exception{
        connThread.socket.close();
    }

    /**
     *
     * @return Returns if the connection is a server
     */
    protected abstract boolean isServer();

    /**
     *
     * @return Returns the IP of the given connection
     */
    protected abstract String getIP();

    /**
     *
     * @return Returns the port of the given connection
     */
    protected abstract int getPort();


    private class ConnectionThread extends Thread{

        private Socket socket;
        private  ObjectOutputStream out;

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
