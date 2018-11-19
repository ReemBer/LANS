package by.bsuir.spolks.client;

import by.bsuir.spolks.Controller;
import by.bsuir.spolks.client.exception.CommandSendingException;
import by.bsuir.spolks.client.exception.ConnectionFailedException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * @author v2.tarasevich
 * @since 14.11.18 12:23
 */
public class CommandService {

    private static final String TIME_TEMPLATE = "time\n";
    private static final String ECHO_TEMPLATE = "echo '%s'\n";
    private static final String UPLOAD_TEMPLATE = "upload '%s' --bufsize=%d\n";
    private static final String DOWNLOAD_TEMPLATE = "download '%s' --bufsize=%d\n";

    private SocketService socketService;

    public CommandService(String host, int port) throws ConnectionFailedException {
        try {
            this.socketService = new SocketService(host, port);
        } catch (IOException e) {
            throw new ConnectionFailedException(e);
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

    public void upload(String filePath, int preferredBufSize, Controller controller) throws CommandSendingException {
        try {
            String command = String.format(UPLOAD_TEMPLATE, filePath, preferredBufSize);
            System.out.println(String.format("Sending %s to server", command));
            socketService.sendCommandWithoutResponse(command);
            DataInputStream dis = socketService.getSocketDIS();
            DataOutputStream dos = socketService.getSocketDOS();
            long fileSize;
            try {
                fileSize = Files.size(Paths.get(filePath));
            } catch (NoSuchFileException e) {
                controller.writelnToConsole("Such file does not exists.");
                dos.writeBoolean(false);
                return;
            }
            dos.writeBoolean(true);
            BufferedOutputStream bos = new BufferedOutputStream(socketService.getSocketOutputStream());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            dos.writeLong(fileSize);
            int chosenBufSize = dis.readInt();
            controller.writelnToConsole(String.format("File size: %d ; Chosen buffer size: %d", fileSize, chosenBufSize));
            int in;
            byte[] portion = new byte[chosenBufSize];
            long start = System.currentTimeMillis();
            while ((in = bis.read(portion)) != -1) {
                bos.write(portion, 0, in);
                bos.flush();
            }
            bos.write(portion,0,0);
            long end = System.currentTimeMillis();
            controller.writelnToConsole(String.format("File sent successfully in : %d milliseconds.", (end - start)));
        } catch (IOException e) {
            throw new CommandSendingException();
        }
    }

    public void download(String filePath, int preferredBufSize, Controller controller) throws CommandSendingException {
        try {
            String command = String.format(DOWNLOAD_TEMPLATE, filePath, preferredBufSize);
            System.out.println(String.format("Sending %s to server", command));
            socketService.sendCommandWithoutResponse(command);
            DataInputStream dataInputStream = socketService.getSocketDIS();
            boolean fileExists = dataInputStream.readBoolean();
            if (!fileExists) {
                System.out.println(String.format("File %s does not exists", filePath));
                controller.writelnToConsole("Such file does not exists.");
                return;
            }
            int chosenBufSize = dataInputStream.readInt();
            socketService.setReceiveBuferSize(chosenBufSize);
            long fileSize = dataInputStream.readLong();
            long percentile = fileSize / 100L;
            byte percents = 1;
            long total = 0;
            byte[] portion = new byte[chosenBufSize];
            BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath.substring(filePath.lastIndexOf('/') + 1))));
            controller.writelnToConsole(String.format("File size: %d ; Chosen buffer size: %d", fileSize, chosenBufSize));
            controller.writelnToConsole("Downloading...");
            System.out.println("Downloading...");
            int in;
            long start = System.currentTimeMillis();
            while (fileSize > 0) {
                in = dataInputStream.read(portion);
                if (in == 0 || in == -1) {
                    break;
                }
                bos.write(portion, 0, in);
                bos.flush();
                fileSize -= in;
                total += in;
                if (total >= percentile * percents) {
                    String receivedPercents = Integer.valueOf(dataInputStream.readByte()).toString();
                    controller.writeToConsole(receivedPercents + (percents % 10 == 0 ? "\n" : (percents < 10 ? "   " : " ")));
                    ++percents;
                }
            }
            bos.close();
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            String msg = String.format("\nFile downloaded successfully in %d milliseconds.", totalTime);
            controller.writelnToConsole(msg);
            System.out.println(msg);
        } catch (IOException e) {
            throw new CommandSendingException();
        }
    }

    public boolean isConnected() {
        return socketService.isConnected();
    }
}
