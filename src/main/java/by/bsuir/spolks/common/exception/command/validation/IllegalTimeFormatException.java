package by.bsuir.spolks.common.exception.command.validation;

import static by.bsuir.spolks.common.command.Command.TIME;
import static by.bsuir.spolks.common.command.validator.impl.TimeCommandValidator.VALID_TIME_FORMAT;

/**
 * @author v2.tarasevich
 * @since 7.10.18 00:06
 */
public class IllegalTimeFormatException extends IllegalCommandFormatException {

    public IllegalTimeFormatException() {
        super(TIME.getName(), VALID_TIME_FORMAT);
    }
}
