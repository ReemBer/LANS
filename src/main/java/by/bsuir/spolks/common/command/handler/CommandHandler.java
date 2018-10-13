package by.bsuir.spolks.common.command.handler;

import by.bsuir.spolks.common.command.context.CommandContext;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 25.09.2018 23:33
 */
@FunctionalInterface
public interface CommandHandler {

    CommandHandler EMPTY_HANDLER = $ -> {};

    void handle(CommandContext context);
}
