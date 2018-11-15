package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.params.CommandParams;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_BUFFER_SIZE;
import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_FILE_PATH;

/**
 * @author v2.tarasevich
 * @since 13.10.18 20:54
 */
public class DownloadCommandHandler implements CommandHandler {
    @Override
    public void handle(CommandContext context) {
        Socket socket = context.getClientSocket();
        OutputStream out = context.getSocketOutputStream();
        try {
            socket.setKeepAlive(true);
            String filePath = context.getParam(NAMED_PARAM_FILE_PATH).toString();
            int bufferSize = (Integer) context.getParam(NAMED_PARAM_BUFFER_SIZE);
            bufferSize = (int) Math.min(bufferSize, Files.size(Paths.get(filePath)));
            socket.setSendBufferSize(bufferSize);
            BufferedOutputStream bos = new BufferedOutputStream(out);
            DataOutputStream dos = new DataOutputStream(out);
            byte[] portion = new byte[bufferSize];
            int in;
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
                dos.writeBoolean(true);
                dos.writeInt(bufferSize);
                dos.writeLong(Files.size(Paths.get(filePath)));
                long start = System.currentTimeMillis();
                while ((in = bis.read(portion)) != -1) {
                    bos.write(portion,0, in);
                    bos.flush();
                }
                long end = System.currentTimeMillis();
                dos.writeLong((end - start));
                bis.close();
                dos.close();
                bos.close();
                System.out.println("File sent in : " + (end - start) + " milliseconds.");
            } catch (FileNotFoundException nfe) {
                dos.writeBoolean(false);
                dos.flush();
                out.write(nfe.getMessage().getBytes());
                out.flush();
                dos.close();
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
