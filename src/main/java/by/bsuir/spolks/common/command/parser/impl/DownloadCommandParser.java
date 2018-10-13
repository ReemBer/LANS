package by.bsuir.spolks.common.command.parser.impl;

import by.bsuir.spolks.common.command.params.CommandParams;
import by.bsuir.spolks.common.command.parser.CommandParser;
import by.bsuir.spolks.common.exception.command.parsing.DownloadParsingException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author v2.tarasevich
 * @since 13.10.18 20:39
 */
public class DownloadCommandParser implements CommandParser {
    @Override
    public CommandParams parse(String commandString) {
        final String downloadPath = commandString.trim().substring(8).trim();
        return STRING_PARAMETER_PATTERNS
                .stream()
                .filter(downloadPath::matches)
                .findAny()
                .map(Pattern::compile)
                .map(pattern -> pattern.matcher(downloadPath))
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1))
                .map(CommandParams::new)
                .orElseThrow(DownloadParsingException::new);
    }
}
