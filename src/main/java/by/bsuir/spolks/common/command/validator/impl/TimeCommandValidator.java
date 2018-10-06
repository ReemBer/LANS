package by.bsuir.spolks.common.command.validator.impl;

import by.bsuir.spolks.common.command.Command;
import by.bsuir.spolks.common.command.validator.CommandValidator;
import by.bsuir.spolks.common.exception.command.validation.CommandValidationException;
import by.bsuir.spolks.common.exception.command.validation.IllegalTimeFormatException;

import static by.bsuir.spolks.common.command.Command.TIME;

/**
 * @author v2.tarasevich
 * @since 6.10.18 21:56
 */
public class TimeCommandValidator extends CommandValidator {

    public static final String VALID_TIME_FORMAT = "time <No parameters>";

    private static final String VALID_TIME_FORMAT_REGEX = String.join(
            "",
            S_OPTIONAL,
            TIME.getName(),
            S_OPTIONAL
    );

    @Override
    public void validate(String command) throws CommandValidationException {
        validateCommandFormat(TIME.getName(), VALID_TIME_FORMAT_REGEX, new IllegalTimeFormatException());
    }
}
