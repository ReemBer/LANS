package by.bsuir.spolks.client;

import lombok.Getter;
import org.checkerframework.checker.nullness.Opt;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

/**
 * @author v2.tarasevich
 * @since 14.11.18 11:57
 */
public class SocketService {

    private static final String CLOSE = "close\n";

    private Socket socket;
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;
    private DataInputStream socketDIS;

    @Getter
    private boolean connected;

    public SocketService(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.socketWriter = Optional.of(socket.getOutputStream())
                .map(OutputStreamWriter::new)
                .map(BufferedWriter::new)
                .get();
        this.socketReader = Optional.of(socket.getInputStream())
                .map(InputStreamReader::new)
                .map(BufferedReader::new)
                .get();
        this.socketDIS = Optional.of(socket.getInputStream())
                .map(DataInputStream::new)
                .get();
        connected = true;
    }

    public String sendCommandWithResponse(String command) throws IOException {
        sendCommandWithoutResponse(command);
        return socketReader.readLine();
    }

    public void sendCommandWithoutResponse(String command) throws IOException {
        socketWriter.write(command);
        socketWriter.flush();
    }

    public DataInputStream getSocketDIS() {
        return socketDIS;
    }

    public void closeConnection() throws IOException {
        if (!socket.isClosed()) {
            sendCommandWithoutResponse(CLOSE);
            socketDIS.close();
            socketWriter.close();
            socketReader.close();
            socket.close();
            connected = false;
        }
    }
}
