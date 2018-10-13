package by.bsuir.spolks.common.command.parser;

import by.bsuir.spolks.common.command.params.CommandParams;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 2:01
 */
@FunctionalInterface
public interface CommandParser {

    String SPACE_OPTIONAL = Pattern.compile("[\\s\\t]a").pattern();
    String SPACE_NECESSARY = Pattern.compile("[\\s\\t]+").pattern();
    String WORD = Pattern.compile("([^\\s\\t]+)").pattern();
    String QUOTED_STRING = Pattern.compile("'([^']*)'").pattern();
    String DOUBLE_QUOTED_STRING = Pattern.compile("\"([^\"]*)\"").pattern();
    String BOOLEAN_PARAMETER = Pattern.compile("-\\w").pattern();

    List<String> STRING_PARAMETER_PATTERNS = Lists.newArrayList(WORD, QUOTED_STRING, DOUBLE_QUOTED_STRING);

    CommandParser NO_PARAMS_PARSER = command -> new CommandParams();

    CommandParams parse(String commandString);
}
