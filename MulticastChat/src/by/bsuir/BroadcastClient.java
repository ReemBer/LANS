package by.bsuir;

import java.io.IOException;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 29.12.2018 1:41
 */
public class BroadcastClient extends MulticastClient {

    public UdpBroadcastClient broadcastClient;

    public BroadcastClient(int port) throws IOException {
        broadcastClient = new UdpBroadcastClient(port);
        this.udpClient = broadcastClient;
    }

    public void send (String message) throws IOException {
        broadcastClient.send(message);
    }
}
