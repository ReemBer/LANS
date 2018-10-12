package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.params.EchoParams;
import by.bsuir.spolks.common.command.response.EchoResponse;
import by.bsuir.spolks.common.exception.command.execution.CommandExecutionException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 25.09.2018 23:46
 */
public class EchoCommandHandler implements CommandHandler<EchoResponse> {

    @Override
    public void handle(CommandContext context) {
        OutputStream socketOut = context.getSocketOutputStream();
        try {
            socketOut.write(
                    context
                            .getParam(0)
                            .toString()
                            .getBytes()
            );
        } catch (IOException e) {
            throw new CommandExecutionException();
        }
    }
}
