package by.bsuir.spolks.common.exception.command.format;

import static by.bsuir.spolks.common.command.CommandNames.ECHO;
import static by.bsuir.spolks.common.command.validator.impl.EchoCommandValidator.VALID_ECHO_FORMAT;

/**
 * @author v2.tarasevich
 * @since 6.10.18 23:48
 */
public class IllegalEchoFormatException extends IllegalCommandFormatException {
    public IllegalEchoFormatException() {
        super(ECHO, VALID_ECHO_FORMAT);
    }
}
