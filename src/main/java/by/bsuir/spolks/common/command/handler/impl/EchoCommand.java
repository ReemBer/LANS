package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.params.EchoParams;
import by.bsuir.spolks.common.command.response.EchoResponse;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 25.09.2018 23:46
 */
public class EchoCommand extends CommandHandler<EchoResponse> {
    @Override
    public EchoResponse apply(CommandContext commandContext) {
        return null;
    }
}
