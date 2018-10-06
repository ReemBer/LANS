package by.bsuir.spolks.common.exception.command.validation;

/**
 * @author v2.tarasevich
 * @since 6.10.18 23:18
 */
public class IllegalCommandFormatException extends CommandValidationException {

    private static final String ILLEGAL_FORMAT_MESSAGE_PATTERN = "Illegal '%s' format.\nLegal is '%s'";

    public IllegalCommandFormatException(String commandName, String validFormat) {
        super(String.format(ILLEGAL_FORMAT_MESSAGE_PATTERN, commandName, validFormat));
    }
}
