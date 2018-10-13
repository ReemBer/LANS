package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;

import java.io.IOException;

/**
 * @author v2.tarasevich
 * @since 13.10.18 20:54
 */
public class DownloadCommandHandler implements CommandHandler {
    @Override
    public void handle(CommandContext context) {
        try {
            context.getSocketOutputStream().write("PIS'KA".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
