package sb5.cs309.nextgen911;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Implements a basic TCP client to send text messages and images
 */

public class TcpClient extends WebSocketClient {

    /**
     * @param url  URL to connect to
     * @param port Port to connect to
     * @throws URISyntaxException Ensure proper URL format
     */
    public TcpClient(String url, int port) throws URISyntaxException {
        super(new URI(url + ":" + port));
    }

    /**
     * On connection notify chat interface of open connection
     *
     * @param handshakedata Server connection response code
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    /**
     * Update chat UI on receipt of a message
     *
     * @param message Message received
     */
    @Override
    public void onMessage(String message) {

    }

    /**
     * Notify user that server has closed
     *
     * @param code   Server return code
     * @param reason Optional exit string
     * @param remote
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    /**
     * Reattempt connection on error, but notify user
     *
     * @param ex Exception causing error
     */
    @Override
    public void onError(Exception ex) {

    }
}
