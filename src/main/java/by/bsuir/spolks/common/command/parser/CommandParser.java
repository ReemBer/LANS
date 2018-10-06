package by.bsuir.spolks.common.command.parser;

import by.bsuir.spolks.common.command.component.CommandComponents;
import by.bsuir.spolks.common.exception.command.validation.CommandValidationException;

import java.util.Arrays;
import java.util.List;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 2:01
 */
public class CommandParser {

    public static CommandComponents parse(String commmand) {
        CommandComponents parseResult = new CommandComponents();
        String[] components = commmand.split("[\\s\\t]+");
        parseResult.setName(components[0]);
        List<String> params = Arrays.asList(components);
        params = params.subList(1, params.size());
        return null;
    }
}
