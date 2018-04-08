package operator;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.function.Consumer;

/**
 * Class to handle the client's socket calls
 * @author Shubham Sharma
 */
public class Client extends NetworkConnection {

    private String ip;
    private int port;

    /**
     * Constructor to initialize and set up the client
     * @param ip The IP of the server
     * @param port The port connecting to the server
     * @param onRecieveCallback Serializable consumer
     */
    public Client(String ip, int port, Consumer<Serializable> onRecieveCallback) {
        super(onRecieveCallback);
        this.ip = ip;
        this.port = port;
    }

    /**
     * Check if client is server
     * @return Always returns false
     */
    @Override
    protected boolean isServer() {
        return false;
    }

    /**
     * @return Returns the IP for the server to connect to.
     */
    @Override
    protected String getIP() {
        return ip;
    }

    /**
     * @return Returns the port to connect to.
     */
    @Override
    protected int getPort() {
        return port;
    }
}