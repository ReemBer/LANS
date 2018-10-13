package by.bsuir.spolks.common.command;

import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.handler.impl.CloseCommandHandler;
import by.bsuir.spolks.common.command.handler.impl.EchoCommandHandler;
import by.bsuir.spolks.common.command.handler.impl.TimeCommandHandler;
import by.bsuir.spolks.common.command.parser.CommandParser;
import by.bsuir.spolks.common.command.parser.EchoCommandParser;
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

import static by.bsuir.spolks.common.command.handler.CommandHandler.EMPTY_HANDLER;
import static by.bsuir.spolks.common.command.parser.CommandParser.NO_PARAMS_PARSER;
import static by.bsuir.spolks.common.command.validator.CommandValidator.EMPTY_VALIDATOR;

/**
 * @author v2.tarasevich
 * @since 6.10.18 20:48
 */
@Getter
@AllArgsConstructor
public enum Command {

    EMPTY(
            CommandNames.EMPTY,
            EMPTY_HANDLER,
            EMPTY_VALIDATOR,
            NO_PARAMS_PARSER
    ),

    CLOSE(
            CommandNames.CLOSE,
            new CloseCommandHandler(),
            new CloseCommandValidator(),
            NO_PARAMS_PARSER
    ),

    TIME(
            CommandNames.TIME,
            new TimeCommandHandler(),
            new TimeCommandValidator(),
            NO_PARAMS_PARSER
    ),

    ECHO(
            CommandNames.ECHO,
            new EchoCommandHandler(),
            new EchoCommandValidator(),
            new EchoCommandParser()
    );

    private String name;
    private CommandHandler<? extends CommandResponse> handler;
    private CommandValidator validator;
    private CommandParser parser;
}
