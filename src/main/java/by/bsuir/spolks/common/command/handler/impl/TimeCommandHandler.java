package by.bsuir.spolks.common.command.handler.impl;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.exception.command.CommandExecutionException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 26.09.2018 0:00
 */
public class TimeCommandHandler implements CommandHandler {

    private static final String DEFAULT_TIME_FORMATTER = "yyy-MM-dd HH:mm:ss";

    @Override
    public void handle(CommandContext context) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMATTER);
            context.getSocketOutputStream().write(LocalDateTime.now().format(formatter).getBytes());
        } catch (IOException e) {
            throw new CommandExecutionException();
        }
    }
}
