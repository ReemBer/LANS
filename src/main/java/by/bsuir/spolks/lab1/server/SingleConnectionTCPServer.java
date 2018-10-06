package by.bsuir.spolks.lab1.server;

import by.bsuir.spolks.common.RequestHandler;
import by.bsuir.spolks.common.SpolksServer;
import by.bsuir.spolks.common.exception.ServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 01.10.2018 14:15
 */
public class SingleConnectionTCPServer implements SpolksServer {

    private final ServerSocket socket;
    private final int port;
    private final RequestHandler requestHandler;

    public SingleConnectionTCPServer() throws IOException {
        port = 1488;
        socket = new ServerSocket(port);
        requestHandler = new SingleThreadRequestHandler();
    }

    public void start() throws ServerException {
        try {
            while (true) {
                Socket client = socket.accept();
                requestHandler.setClientSocket(client);
                requestHandler.start();
                client.bind();

            }
        } catch (IOException e) {
            throw new ServerException("Exception during waiting client connection.", e);
        }
    }
}
