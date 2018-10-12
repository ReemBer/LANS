package by.bsuir.spolks.common.command.parser;

import by.bsuir.spolks.common.command.params.CommandParams;

import java.util.regex.Pattern;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 2:01
 */
@FunctionalInterface
public interface CommandParser {

    Pattern SPACE_OPTIONAL = Pattern.compile("[\\s\\t]*");
    Pattern SPACE_NECESSARY = Pattern.compile("[\\s\\t]+");
    Pattern STRING_PARAMETER = Pattern.compile("([^\\s\\t]+)");
    Pattern BOOLEAN_PARAMETER = Pattern.compile("-\\w");

    CommandParser NO_PARAMS_PARSER = command -> new CommandParams();

    CommandParams parse(String commandString);
}
