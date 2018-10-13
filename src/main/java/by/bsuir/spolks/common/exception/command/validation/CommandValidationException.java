package by.bsuir.spolks.common.exception.command.validation;

import by.bsuir.spolks.common.exception.command.CommandException;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 2:17
 */
public class CommandValidationException extends CommandException {

    public CommandValidationException(String command) {
        super(command);
    }
}
