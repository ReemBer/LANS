package by.bsuir.spolks.common.command.parser.impl;

import by.bsuir.spolks.common.command.params.CommandParams;
import by.bsuir.spolks.common.command.parser.CommandParser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_BUFFER_SIZE;
import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_DOWNLOADED_SIZE;
import static by.bsuir.spolks.common.command.params.CommandParams.NAMED_PARAM_FILE_PATH;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 19.11.2018 1:01
 */
public class ContinueDownloadParser implements CommandParser {

    @Override
    public CommandParams parse(String commandString) {
        CommandParams parameters = new CommandParams();
        String[] tokens = commandString.split(SPACE_NECESSARY_REGEX);
        Optional.of(
                Pattern.compile(QUOTED_STRING_REGEX)
                        .matcher(tokens[1])
        ).filter(Matcher::matches)
         .ifPresent(
                 matcher -> parameters.addNamedParam(NAMED_PARAM_FILE_PATH, matcher.group(1))
         );
        Optional.of(tokens[2])
                .filter(token -> token.matches("--" + NAMED_PARAM_BUFFER_SIZE + "=" + INTEGER_PARAMETER_REGEX))
                .ifPresent(bufSize ->
                        parameters.addNamedParam(
                                NAMED_PARAM_BUFFER_SIZE,
                                Integer.valueOf(bufSize.split("=")[1])
                        )
                );
        Optional.of(tokens[3])
                .filter(token -> token.matches("--" + NAMED_PARAM_DOWNLOADED_SIZE + "=" + INTEGER_PARAMETER_REGEX))
                .ifPresent(bufSize ->
                        parameters.addNamedParam(
                                NAMED_PARAM_DOWNLOADED_SIZE,
                                Integer.valueOf(bufSize.split("=")[1])
                        )
                );
        return parameters;
    }
}
