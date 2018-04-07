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

    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIP() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
