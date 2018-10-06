package by.bsuir.spolks.common.command.validator.impl;

import by.bsuir.spolks.common.command.Command;
import by.bsuir.spolks.common.command.validator.CommandValidator;
import by.bsuir.spolks.common.exception.command.validation.CommandValidationException;
import by.bsuir.spolks.common.exception.command.validation.IllegalEchoFormatException;

import static by.bsuir.spolks.common.command.Command.ECHO;

/**
 * @author v2.tarasevich
 * @since 6.10.18 21:52
 */
public class EchoCommandValidator extends CommandValidator {

    public static final String VALID_ECHO_FORMAT = "echo [string param]";

    private static final String STRING_LITERAL_REGEX = "((\\w+)|([\"\'][^\"\']+[\"\']))";
    private static final String ECHO_FORMAT_REGEX = String.join(
            "",
            S_OPTIONAL,
            ECHO.getName(),
            S_NECESSARY,
            STRING_LITERAL_REGEX,
            S_OPTIONAL
    );

    @Override
    public void validate(String command) throws CommandValidationException {
        validateCommandFormat(command, ECHO_FORMAT_REGEX, new IllegalEchoFormatException());
    }
}
