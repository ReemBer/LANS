package by.bsuir.spolks.client;

import by.bsuir.spolks.Controller;
import by.bsuir.spolks.client.exception.CommandSendingException;
import by.bsuir.spolks.client.exception.ConnectionFailedException;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author v2.tarasevich
 * @since 14.11.18 12:23
 */
public class CommandService {

    private static final String TIME_TEMPLATE = "time\n";
    private static final String ECHO_TEMPLATE = "echo '%s'\n";
    private static final String UPLOAD_TEMPLATE = "upload %s\n";
    private static final String DOWNLOAD_TEMPLATE = "download '%s' --bufsize=%d\n";

    private SocketService socketService;

    public CommandService(String host, int port) throws ConnectionFailedException {
        try {
            this.socketService = new SocketService(host, port);
        } catch (IOException e) {
            throw new ConnectionFailedException();
        }
    }

    public String echo(String text) throws CommandSendingException {
        try {
            return socketService.sendCommandWithResponse(String.format(ECHO_TEMPLATE, text));
        } catch (IOException e) {
            throw new CommandSendingException();
        }
    }

    public String time() throws CommandSendingException {
        try {
            return socketService.sendCommandWithResponse(TIME_TEMPLATE);
        } catch (IOException e) {
            throw new CommandSendingException();
        }
    }

    public void close() throws CommandSendingException {
        try {
            socketService.closeConnection();
        } catch (IOException e) {
            throw new CommandSendingException();
        }
    }

    public void upload(String filePath) throws CommandSendingException {
        try {
            socketService.sendCommandWithResponse(String.format(UPLOAD_TEMPLATE, filePath));
            // do it
        } catch (IOException e) {
            throw new CommandSendingException();
        }
    }

    public void download(String filePath, int preferredBufSize, Controller controller) throws CommandSendingException {
        try {
            socketService.sendCommandWithoutResponse(String.format(DOWNLOAD_TEMPLATE, filePath, preferredBufSize));
            DataInputStream dataInputStream = socketService.getSocketDIS();
            boolean fileExists = dataInputStream.readBoolean();
            if (!fileExists) {
                controller.writeToConsole("Such file does not exists.\n");
                dataInputStream.close();
                return;
            }
            int chosenBufSize = dataInputStream.readInt();
            long fileSize = dataInputStream.readLong();
            byte[] portion = new byte[chosenBufSize];
            BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath.substring(filePath.lastIndexOf('/') + 1))));
            controller.writeToConsole(String.format("File size: %d ; Chosen buffer size: %d", chosenBufSize, fileSize));
            controller.writeToConsole("Downloading...");
            int in;
            while (fileSize > 0) {
                in = dataInputStream.read(portion);
                bos.write(portion, 0, in);
                fileSize -= in;
            }
            bos.flush();
            long totalTime = dataInputStream.readLong();
            controller.writeToConsole(String.format("File downloaded successfully in %d milliseconds.", totalTime));
        } catch (IOException e) {
            throw new CommandSendingException();
        }
    }

    public boolean isConnected() {
        return socketService.isConnected();
    }
}
