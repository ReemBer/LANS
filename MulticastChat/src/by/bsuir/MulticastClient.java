package by.bsuir;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 26.12.2018 4:14
 */
public class MulticastClient {

    protected UdpClient udpClient;
    public SocketAddress multicast;
    public int port;

    public MulticastClient() {}

    public MulticastClient(byte[] multicastGroupAddress, int port) throws IOException, UnknownHostException {
        this.udpClient = new UdpClient(port);
        this.port = port;
        InetAddress multicastGroup = InetAddress.getByAddress(multicastGroupAddress);
        this.multicast = new InetSocketAddress(multicastGroup, port);
        this.udpClient.joinGroup(multicastGroup);
    }

    public void join(InetAddress address) throws IOException {
        udpClient.joinGroup(address);
    }

    public void leave(InetAddress address) throws IOException {
        udpClient.leaveGroup(address);
    }

    public void turnOffLoopbackMode() throws SocketException {
        udpClient.turnOffLoopbackMode();
    }

    public void turnOnLoopbackMode() throws SocketException {
        udpClient.turnOnLoopbackMode();
    }

    public void send(String message) throws IOException {
        udpClient.send(message, multicast);
    }

    public void listen() throws IOException {
        DatagramPacket received = null;
        while (true) {
            if ((received = udpClient.receive()) != null) {
                String receivedMessage = new String(received.getData(), 0, received.getLength());
                if ("--ping".equals(receivedMessage)) {
                    udpClient.send("--echo", received.getSocketAddress());
                    continue;
                }
                if ("--echo".equals(receivedMessage)) {
                    System.out.println("User online: " + received.getAddress().getHostAddress());
                    continue;
                }
                System.out.println(String.format("%s $ %s", received.getAddress().getHostAddress(), receivedMessage));
            }
        }
    }
}
