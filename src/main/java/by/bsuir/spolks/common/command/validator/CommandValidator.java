package by.bsuir.spolks.common.command.validator;

import by.bsuir.spolks.common.exception.command.validation.CommandValidationException;
import by.bsuir.spolks.common.exception.command.validation.IllegalCommandFormatException;

import java.util.regex.Pattern;

/**
 * @author v2.tarasevich
 * @since 6.10.18 20:43
 */
public abstract class CommandValidator {

    protected static final String S_OPTIONAL = "\\s*";
    protected static final String S_NECESSARY = "\\s+";

    protected void validateCommandFormat(String command, String validFormatRegex,
                                       IllegalCommandFormatException formatException)
            throws CommandValidationException {
        if (Pattern.compile(validFormatRegex).matcher(command).matches()) return;
        throw formatException;
    }

    public abstract void validate(String command) throws CommandValidationException;
}
