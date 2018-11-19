package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.params.CommandParams;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_BUFFER_SIZE;
import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_DOWNLOADED_SIZE;
import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_FILE_PATH;

/**
 * @author v2.tarasevich
 * @since 13.10.18 20:54
 */
public class DownloadCommandHandler implements CommandHandler {
    @Override
    public void handle(CommandContext context) {
        try {
            String filePath = context.getParam(NAMED_PARAM_FILE_PATH).toString();
            OutputStream out = context.getSocketOutputStream();
            DataOutputStream dos = new DataOutputStream(out);

            if (Files.notExists(Paths.get(filePath))) {
                dos.writeBoolean(false);
                return;
            }

            int bufferSize = (Integer) context.getParam(NAMED_PARAM_BUFFER_SIZE);
            long fileSize = Files.size(Paths.get(filePath));
            bufferSize = (int) Math.min(bufferSize, fileSize);

            context.getClientSocket().setSendBufferSize(bufferSize);
            bufferSize = context.getClientSocket().getSendBufferSize();

            Object totalReceivedStr = context.getParam(NAMED_PARAM_DOWNLOADED_SIZE);
            long totalReceived = 0;
            boolean isContinue = totalReceivedStr != null;
            if (isContinue) {
                totalReceived = Long.parseLong(totalReceivedStr.toString());
            }

            try {
                dos.writeBoolean(true);
                dos.writeInt(bufferSize);
                dos.writeLong(fileSize);
                long downloadTime = sendFileToClient(context.getClientSocket(), out, filePath, bufferSize, isContinue, totalReceived, fileSize / 100L);
                System.out.println(String.format("File sent in : %d milliseconds.", downloadTime));
            } catch (FileNotFoundException nfe) {
                dos.writeBoolean(false);
                dos.flush();
                out.write(nfe.getMessage().getBytes());
                out.flush();
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long sendFileToClient(Socket socket, OutputStream out, String filePath, int bufferSize, boolean isContinue, long totalReceived, long percentile) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(out);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
        int in;
        byte[] portion = new byte[bufferSize];
        long total = 0;
        byte percents = 1;
        if (isContinue) {
            while ((totalReceived -= bis.skip(totalReceived)) != 0);
        }
        long start = System.currentTimeMillis();
        while ((in = bis.read(portion)) != -1) {
            bos.write(portion,0, in);
            bos.flush();
            total += in;
            if (!isContinue) {
                if (total >= percentile * percents) {
                    socket.sendUrgentData(percents);
                    ++percents;
                }
            }
        }
        bos.write(portion,0,0);
        long end = System.currentTimeMillis();
        bis.close();
        return end - start;
    }
}
