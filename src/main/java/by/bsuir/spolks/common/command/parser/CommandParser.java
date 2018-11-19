package by.bsuir.spolks.common.command.parser;

import by.bsuir.spolks.common.command.params.CommandParams;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 2:01
 */
@FunctionalInterface
public interface CommandParser {

    String SPACE_NECESSARY_REGEX = Pattern.compile("[\\s\\t]+").pattern();
    String WORD_REGEX = Pattern.compile("([^\\s\\t'\"]+)").pattern();
    String SINGLE_QUOTED_STRING_REGEX = Pattern.compile("'([^']*)'").pattern();
    String DOUBLE_QUOTED_STRING_REGEX = Pattern.compile("\"([^\"]*)\"").pattern();
    String QUOTED_STRING_REGEX = Pattern.compile("['\"]([^'\"]*)['\"]").pattern();
    String INTEGER_PARAMETER_REGEX = Pattern.compile("\\d+").pattern();
    String BOOLEAN_PARAMETER_REGEX = Pattern.compile("-\\w").pattern();

    List<String> STRING_PARAMETER_PATTERNS = Arrays.asList(WORD_REGEX, SINGLE_QUOTED_STRING_REGEX, DOUBLE_QUOTED_STRING_REGEX);

    CommandParser NO_PARAMS_PARSER = command -> new CommandParams();

    CommandParams parse(String commandString);
}
