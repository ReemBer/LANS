package by.bsuir.spolks.common.command.parser;

import by.bsuir.spolks.common.command.params.CommandParams;
import org.checkerframework.checker.nullness.Opt;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author v2.tarasevich
 * @since 13.10.18 16:26
 */
public class EchoCommandParser implements CommandParser {
    @Override
    public CommandParams parse(String commandString) {
        final String echoParam = commandString.trim().substring(4).trim();
        return STRING_PARAMETER_PATTERNS
                .stream()
                .filter(echoParam::matches)
                .findAny()
                .map(Pattern::compile)
                .map(param -> param.matcher(echoParam))
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1))
                .map(CommandParams::new)
                .orElse(new CommandParams("Something went wrong"));
    }
}
