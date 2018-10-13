package by.bsuir.spolks.common.exception.command;

/**
 * @author v2.tarasevich
 * @since 12.10.18 21:16
 */
public class CommandExecutionException extends CommandException {
    public CommandExecutionException() {
        super("Error during writing answer to socket.");
    }
}
