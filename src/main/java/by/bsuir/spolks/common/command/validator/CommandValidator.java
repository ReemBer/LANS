package by.bsuir.spolks.common.command.validator;

import by.bsuir.spolks.common.exception.command.CommandValidationException;
import by.bsuir.spolks.common.exception.command.format.IllegalCommandFormatException;

import java.util.regex.Pattern;

/**
 * @author v2.tarasevich
 * @since 6.10.18 20:43
 */
public abstract class CommandValidator {

    public static final CommandValidator EMPTY_VALIDATOR = new CommandValidator() {
        @Override
        public void validate(String command) throws CommandValidationException {
            // Nothing to do.
        }
    };

    protected static final String S_OPTIONAL = "[\\s\\t]*";
    protected static final String S_NECESSARY = "[\\s\\t]+";
    protected static final String STRING_LITERAL_REGEX = "((\\w+)|(\"[^\"]+\")|('[^']+'))";

    protected void validateCommandFormat(String command, String validFormatRegex,
                                       IllegalCommandFormatException formatException)
            throws CommandValidationException {
        if (Pattern.compile(validFormatRegex).matcher(command).matches()) return;
        throw formatException;
    }

    public abstract void validate(String command) throws CommandValidationException;
}
