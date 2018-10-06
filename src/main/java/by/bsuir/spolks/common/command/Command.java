package by.bsuir.spolks.common.command;

import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.handler.impl.CloseCommand;
import by.bsuir.spolks.common.command.handler.impl.EchoCommand;
import by.bsuir.spolks.common.command.handler.impl.TimeCommand;
import by.bsuir.spolks.common.command.response.CommandResponse;
import by.bsuir.spolks.common.command.validator.CommandValidator;
import by.bsuir.spolks.common.command.validator.impl.CloseCommandValidator;
import by.bsuir.spolks.common.command.validator.impl.EchoCommandValidator;
import by.bsuir.spolks.common.command.validator.impl.TimeCommandValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author v2.tarasevich
 * @since 6.10.18 20:48
 */
@Getter
@AllArgsConstructor
public enum Command {

    CLOSE("close", new CloseCommand(), new CloseCommandValidator()),
    TIME("time", new TimeCommand(), new TimeCommandValidator()),
    ECHO("echo", new EchoCommand(), new EchoCommandValidator());

    public static final Map<String, Command> COMMAND_MAPPING = Arrays
            .stream(Command.values())
            .collect(
                    Collectors.toMap(
                            Command::getName,
                            command -> command
                    )
            );

    private String name;
    private CommandHandler<? extends CommandResponse> handler;
    private CommandValidator validator;
}
