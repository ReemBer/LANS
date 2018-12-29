package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;

import java.io.*;
import java.net.Socket;

import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_BUFFER_SIZE;
import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_FILE_PATH;

/**
 * @author v2.tarasevich
 * @since 17.11.18 11:47
 */
public class UploadCommandHandler implements CommandHandler {
    @Override
    public void handle(CommandContext context) {
        Socket socket = context.getClientSocket();
        OutputStream out = context.getSocketOutputStream();
        InputStream inp = context.getSocketInputStream();
        try {
            String[] filePaths = context.getParam(NAMED_PARAM_FILE_PATH).toString().split("/");
            String filePath = filePaths[filePaths.length - 1];
            int bufferSize = (Integer) context.getParam(NAMED_PARAM_BUFFER_SIZE);
            socket.setKeepAlive(true);
            DataOutputStream dos = new DataOutputStream(out);
            DataInputStream dis = new DataInputStream(inp);
            boolean clientReady = dis.readBoolean();
            if (!clientReady) {
                return;
            }
            long fileSize = dis.readLong();
            bufferSize = (int) Math.min(bufferSize, fileSize);
            socket.setReceiveBufferSize(bufferSize);
            bufferSize = socket.getReceiveBufferSize();
            dos.writeInt(bufferSize);
            BufferedInputStream bis = new BufferedInputStream(inp);
            byte[] portion = new byte[bufferSize];
            int in;
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            long start = System.currentTimeMillis();
            while (fileSize > 0) {
                in = bis.read(portion);
                if (in == 0 || in == -1) {
                    break;
                }
                bos.write(portion,0, in);
                bos.flush();
                fileSize -= in;
            }
            bos.close();
            long end = System.currentTimeMillis();
            System.out.println("File sent in : " + (end - start) + " milliseconds.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
