package by.bsuir.spolks.common.exception.command;

/**
 * @author v2.tarasevich
 * @since 13.10.18 16:12
 */
public class CommandNotFoundException extends CommandException {

    public CommandNotFoundException(String commandName) {
        super(String.format("Command '%s' not found.", commandName));
    }
}
