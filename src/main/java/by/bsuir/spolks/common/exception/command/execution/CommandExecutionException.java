package by.bsuir.spolks.common.exception.command.execution;

/**
 * @author v2.tarasevich
 * @since 12.10.18 21:16
 */
public class CommandExecutionException extends RuntimeException {
    public CommandExecutionException() {
        super("Error during writing answer to socket.");
    }
}
