package by.bsuir.spolks.client;

import by.bsuir.spolks.Controller;
import by.bsuir.spolks.client.exception.CommandSendingException;
import by.bsuir.spolks.client.exception.ConnectionFailedException;
import by.bsuir.spolks.client.exception.FileSendingInterruptedException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author v2.tarasevich
 * @since 14.11.18 12:23
 */
public class CommandService {

    private static final String TIME_TEMPLATE = "time\n";
    private static final String ECHO_TEMPLATE = "echo '%s'\n";
    private static final String UPLOAD_TEMPLATE = "upload '%s' --bufsize=%d\n";
    private static final String DOWNLOAD_TEMPLATE = "download '%s' --bufsize=%d\n";
    private static final String DOWNLOAD_CONTINUE_TEMPLATE = "continueDownload '%s' --bufsize=%d --downloadedSize=%d\n";

    private static final String TEMP_FILE_TILL_SENDING_IN_ERROR = "tmpTillDownloadInError.txt";

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
            socketService = null;
        } catch (IOException e) {
            throw new CommandSendingException();
        }
    }

    public void upload(String filePath, int preferredBufSize, Controller controller) throws CommandSendingException {
        try {
            sendCommandWithoutResponse(String.format(UPLOAD_TEMPLATE, filePath, preferredBufSize));
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

    public void download(String filePath, int preferredBufSize, Controller controller, boolean isContinue, long totalReceived) {

        if (isContinue) {
            sendCommandWithoutResponse(String.format(DOWNLOAD_CONTINUE_TEMPLATE, filePath, preferredBufSize, totalReceived));
        } else {
            sendCommandWithoutResponse(String.format(DOWNLOAD_TEMPLATE, filePath, preferredBufSize));
        }

        DataInputStream dataInputStream = socketService.getSocketDIS();
        int chosenBufSize;
        long fileSize;

        try {
            if (!dataInputStream.readBoolean()) {
                System.out.println(String.format("File %s does not exists", filePath));
                controller.writelnToConsole("Such file does not exists.");
                return;
            }
            chosenBufSize = dataInputStream.readInt();
            socketService.setReceiveBufferSize(chosenBufSize);
            fileSize = dataInputStream.readLong();
        } catch (IOException e) {
            controller.writeToConsole(e.getMessage());
            return;
        }

        long percentile = fileSize / 100L;
        byte percents = 1;
        long total = isContinue ? totalReceived : 0;
        byte[] portion = new byte[chosenBufSize];

        controller.writelnToConsole(String.format("File size: %d ; Chosen buffer size: %d", fileSize, chosenBufSize));
        controller.writelnToConsole("Downloading...");
        System.out.println("Downloading...");

        int in;
        long start;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(
                    isContinue ? Files.newOutputStream(Paths.get(filePath.substring(filePath.lastIndexOf('/') + 1)), StandardOpenOption.APPEND)
                            : Files.newOutputStream(Paths.get(filePath.substring(filePath.lastIndexOf('/') + 1)))
            );
            start = System.currentTimeMillis();
            while (total < fileSize) {
                in = dataInputStream.read(portion);
                if (in == 0 || in == -1) {
                    break;
                }
                bos.write(portion, 0, in);
                bos.flush();
                total += in;
                if(!isContinue) {
                    if (total >= percentile * percents) {
                        String space = (percents % 10 == 0 ? "\n" : (percents < 10 ? "   " : " "));
                        controller.writeToConsole(Integer.valueOf(dataInputStream.readByte()).toString() + space);
                        ++percents;
                    }
                }
            }
            long end = System.currentTimeMillis();
            long totalTime = end - start;
            String msg = String.format("\nFile downloaded successfully in %d milliseconds.", totalTime);
            controller.writelnToConsole(msg);
            System.out.println(msg);
        } catch (IOException e) {
            try {
                DataOutputStream dos2 = new DataOutputStream( new FileOutputStream(new File(TEMP_FILE_TILL_SENDING_IN_ERROR)));
                dos2.writeLong(total);
                dos2.writeInt(chosenBufSize);
                dos2.writeUTF(filePath);
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                controller.writeToConsole("\nConnection with server was lost during downloading file.");
                socketService.closeSocket();
                throw new FileSendingInterruptedException();
            } catch (IOException ex) {
                throw new FileSendingInterruptedException();
            }
        }
    }

    public void continueDownload(Controller controller) {
        long downloadedTotal;
        int bufferSize;
        String filePath;
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(new File(TEMP_FILE_TILL_SENDING_IN_ERROR)));
            downloadedTotal = dis.readLong();
            bufferSize = dis.readInt();
            filePath = dis.readUTF();
        } catch (IOException e) {
            controller.writeToConsole("Error till reading download context from file.");
            e.printStackTrace();
            return;
        }
        download(filePath, bufferSize, controller, true, downloadedTotal);
    }

    public boolean isConnected() {
        return socketService.isConnected();
    }

    private void sendCommandWithoutResponse(String command) throws CommandSendingException {
        try {
            System.out.println(String.format("Sending %s to server", command));
            socketService.sendCommandWithoutResponse(command);
        }catch (IOException e) {
            throw new CommandSendingException();
        }
    }
}
