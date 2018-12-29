package by.bsuir;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 26.12.2018 3:48
 */
public class UdpClient {

    protected MulticastSocket socket;
    private final int port;

    public UdpClient(int port) throws IOException {
        this.port = port;
        this.socket = new MulticastSocket(new InetSocketAddress(port));
        this.socket.setReuseAddress(true);
    }

    public void joinGroup(InetAddress groutIpAddress) throws IOException {
        this.socket.joinGroup(groutIpAddress);
    }

    public void leaveGroup(InetAddress groupIpAddress) throws IOException {
        this.socket.leaveGroup(groupIpAddress);
    }

    public void turnOffLoopbackMode() throws SocketException {
        this.socket.setLoopbackMode(true);
    }

    public void turnOnLoopbackMode() throws SocketException {
        this.socket.setLoopbackMode(false);
    }

    public void send(String message, SocketAddress destination) throws IOException {
//        synchronized (sync) {
            byte[] byteMessage = message.getBytes();
            socket.send(new DatagramPacket(byteMessage, byteMessage.length, destination));
//        }
    }

    public DatagramPacket receive() throws IOException {
//        synchronized (sync) {
            byte[] buffer = new byte[2048];
            DatagramPacket received = new DatagramPacket(buffer, buffer.length);
            socket.receive(received);
            return received;
//        }
    }
}
