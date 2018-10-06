package by.bsuir.spolks.common.command.config;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.handler.impl.CloseCommand;
import by.bsuir.spolks.common.command.handler.impl.EchoCommand;
import by.bsuir.spolks.common.command.handler.impl.TimeCommand;
import by.bsuir.spolks.common.command.params.CommandParams;
import by.bsuir.spolks.common.command.response.CommandResponse;

import java.util.*;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 22.09.2018 2:23
 */
public final class CommandConfigurer {

    public static final Map<String, CommandHandler<? extends CommandResponse>> COMMANDS =
            new HashMap<String, CommandHandler<? extends CommandResponse>>() {
        {
            put(CommandConstants.ECHO, new EchoCommand());
            put(CommandConstants.TIME, new TimeCommand());
            put(CommandConstants.CLOSE, new CloseCommand());
        }
    };
}
