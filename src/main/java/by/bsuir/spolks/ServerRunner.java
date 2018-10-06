package by.bsuir.spolks;

import by.bsuir.spolks.common.SpolksServer;
import by.bsuir.spolks.common.exception.ServerException;
import by.bsuir.spolks.lab1.server.SingleConnectionTCPServer;

import java.io.IOException;
import java.net.Socket;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 06.10.2018 1:30
 */
public class ServerRunner {

    public static void main(String ... args) {
        try {
            SpolksServer server = new SingleConnectionTCPServer();
            server.start();
        } catch (IOException | ServerException e) {
            e.printStackTrace();
        }
    }
}
