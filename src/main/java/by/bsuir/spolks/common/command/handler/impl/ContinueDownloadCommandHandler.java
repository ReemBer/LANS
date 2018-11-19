package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 19.11.2018 1:05
 */
public class ContinueDownloadCommandHandler implements CommandHandler {

    private CommandHandler downloadCommandHandler = new DownloadCommandHandler();

    @Override
    public void handle(CommandContext context) {
        downloadCommandHandler.handle(context);
    }
}
