package by.bsuir.spolks.common.exception.command;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 2:17
 */
public class CommandValidationException extends Exception {

    public CommandValidationException(String command) {
        super(command);
    }
}
