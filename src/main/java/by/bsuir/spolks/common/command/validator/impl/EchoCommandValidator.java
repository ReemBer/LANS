package by.bsuir.spolks.common.command.validator.impl;

import by.bsuir.spolks.common.command.validator.CommandValidator;
import by.bsuir.spolks.common.exception.command.CommandValidationException;
import by.bsuir.spolks.common.exception.command.format.IllegalEchoFormatException;

import static by.bsuir.spolks.common.command.CommandNames.ECHO;

/**
 * @author v2.tarasevich
 * @since 6.10.18 21:52
 */
public class EchoCommandValidator extends CommandValidator {

    public static final String VALID_ECHO_FORMAT = "echo [string param]";

    private static final String ECHO_FORMAT_REGEX = String.join(
            "",
            S_OPTIONAL,
            ECHO,
            S_NECESSARY,
            STRING_LITERAL_REGEX,
            S_OPTIONAL
    );

    @Override
    public void validate(String command) throws CommandValidationException {
        validateCommandFormat(command, ECHO_FORMAT_REGEX, new IllegalEchoFormatException());
    }
}
