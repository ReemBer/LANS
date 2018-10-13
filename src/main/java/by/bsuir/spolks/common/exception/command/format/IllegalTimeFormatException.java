package by.bsuir.spolks.common.exception.command.format;

import static by.bsuir.spolks.common.command.CommandNames.TIME;
import static by.bsuir.spolks.common.command.validator.impl.TimeCommandValidator.VALID_TIME_FORMAT;

/**
 * @author v2.tarasevich
 * @since 7.10.18 00:06
 */
public class IllegalTimeFormatException extends IllegalCommandFormatException {

    public IllegalTimeFormatException() {
        super(TIME, VALID_TIME_FORMAT);
    }
}
