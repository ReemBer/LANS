package by.bsuir.spolks.common.command;

import by.bsuir.spolks.common.command.handler.CommandHandler;
import by.bsuir.spolks.common.command.handler.impl.*;
import by.bsuir.spolks.common.command.parser.CommandParser;
import by.bsuir.spolks.common.command.parser.impl.ContinueDownloadParser;
import by.bsuir.spolks.common.command.parser.impl.DownloadUploadCommandParser;
import by.bsuir.spolks.common.command.parser.impl.EchoCommandParser;
import by.bsuir.spolks.common.command.validator.CommandValidator;
import by.bsuir.spolks.common.command.validator.impl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    ),

    DOWNLOAD(
            CommandNames.DOWNLOAD,
            new DownloadCommandHandler(),
            new DownloadUploadCommandValidator(),
            new DownloadUploadCommandParser()
    ),

    CONTINUE_DOWNLOAD(
            CommandNames.CONTINUE_DOWNLOAD,
            new ContinueDownloadCommandHandler(),
            new ContinueDownloadCommandValidator(),
            new ContinueDownloadParser()
    ),

    UPLOAD(
            CommandNames.UPLOAD,
            new UploadCommandHandler(),
            new DownloadUploadCommandValidator(),
            new DownloadUploadCommandParser()
    );


    private String name;
    private CommandHandler handler;
    private CommandValidator validator;
    private CommandParser parser;


    @Override
    public String toString() {
        return name;
    }
}
