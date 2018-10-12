package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.params.CloseParams;
import by.bsuir.spolks.common.command.response.CloseResponse;

import java.io.IOException;
import java.net.Socket;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 26.09.2018 0:05
 */
public class CloseCommandHandler implements CommandHandler<CloseResponse> {
    @Override
    public void handle(CommandContext commandContext) {
        try {
            Socket socket = commandContext.getClientSocket();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnknownError(e.getMessage());
        }
    }
}
