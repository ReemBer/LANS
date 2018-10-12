package by.bsuir.spolks.common.command.config;

import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.handler.impl.CloseCommandHandler;
import by.bsuir.spolks.common.command.handler.impl.EchoCommandHandler;
import by.bsuir.spolks.common.command.handler.impl.TimeCommandHandler;
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
            put(CommandConstants.ECHO, new EchoCommandHandler());
            put(CommandConstants.TIME, new TimeCommandHandler());
            put(CommandConstants.CLOSE, new CloseCommandHandler());
        }
    };
}
