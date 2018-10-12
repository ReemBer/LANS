package by.bsuir.spolks.common.command.validator.impl;

import by.bsuir.spolks.common.command.CommandNames;
import by.bsuir.spolks.common.command.validator.CommandValidator;
import by.bsuir.spolks.common.exception.command.validation.CommandValidationException;
import by.bsuir.spolks.common.exception.command.validation.IllegalCloseFormatException;

import static by.bsuir.spolks.common.command.Command.CLOSE;

/**
 * @author v2.tarasevich
 * @since 6.10.18 21:56
 */
public class CloseCommandValidator extends CommandValidator {

    public static final String VALID_CLOSE_FORMAT = "close <No parameters>";

    private final String VALID_CLOSE_FORMAT_REGEX = String.join(
            "",
            S_OPTIONAL,
            CommandNames.CLOSE,
            S_OPTIONAL
    );

    @Override
    public void validate(String command) throws CommandValidationException {
        validateCommandFormat(CLOSE.getName(), VALID_CLOSE_FORMAT_REGEX, new IllegalCloseFormatException());
    }
}
