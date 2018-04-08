package operator;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Class to handle the server's socket calls
 * @author Shubham Sharma
 */
public class Server extends NetworkConnection {

    private int port;

    /**
     * Constructor to initialize and set up the server
     * @param port The port connecting to the server
     * @param onRecieveCallback Serializable consumer
     */
    public Server(int port, Consumer<Serializable> onRecieveCallback) {
        super(onRecieveCallback);
        this.port =port;
    }

    /**
     * Check if Server is server
     * @return Always returns false
     */
    @Override
    protected boolean isServer() {
        return true;
    }

    /**
     * @return Returns the IP for the server
     */
    @Override
    protected String getIP() {
        return null;
    }

    /**
     * @return Returns the port of the server to connect to
     */
    @Override
    protected int getPort() {
        return port;
    }
}
