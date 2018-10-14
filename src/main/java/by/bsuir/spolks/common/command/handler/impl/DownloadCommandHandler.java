package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;

import java.io.*;
import java.net.Socket;

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
            String filePath = context.getParams().getParam(0).toString();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            byte[] portion = new byte[8192];
            int in;
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
                long start = System.currentTimeMillis();
                while ((in = bis.read(portion)) != -1) {
                    bos.write(portion,0, in);
                    bos.flush();
                }
                long end = System.currentTimeMillis();
                bis.close();
                bos.close();
                System.out.println("File sent in : " + (end - start) + " milliseconds.");
            } catch (FileNotFoundException nfe) {
                out.write(nfe.getMessage().getBytes());
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
