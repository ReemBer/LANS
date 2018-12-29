package by.bsuir;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 29.12.2018 1:16
 */
public class UdpBroadcastClient extends UdpClient {

    private SocketAddress broadcastIp = null;

    public UdpBroadcastClient(int port) throws IOException {
        super(port);
        this.socket.setBroadcast(true);
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast != null) {
                        this.broadcastIp = new InetSocketAddress(broadcast, port);
                        break;
                    }
                }
            }
        }
    }

    public void send(String message) throws IOException {
        send(message, broadcastIp);
    }
}
