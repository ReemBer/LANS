package by.bsuir.spolks.common.exception.command.format;

import static by.bsuir.spolks.common.command.CommandNames.CLOSE;
import static by.bsuir.spolks.common.command.validator.impl.CloseCommandValidator.VALID_CLOSE_FORMAT;

/**
 * @author v2.tarasevich
 * @since 7.10.18 00:16
 */
public class IllegalCloseFormatException extends IllegalCommandFormatException {

    public IllegalCloseFormatException() {
        super(CLOSE, VALID_CLOSE_FORMAT);
    }
}
