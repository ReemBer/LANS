package by.bsuir.spolks.common.command.util;

import by.bsuir.spolks.common.command.Command;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author v2.tarasevich
 * @since 6.10.18 22:51
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandUtils {

    public static final Map<String, Command> COMMAND_MAPPING = Arrays
            .stream(Command.values())
            .collect(
                    Collectors.toMap(
                            Command::getName,
                            command -> command
                    )
            );

    public static String parseName(String command) {
        return Optional
                .ofNullable(command)
                .map(String::trim)
                .map(cmd -> {
                    int firstSpace = cmd.indexOf(' ');
                    firstSpace = firstSpace == -1 ? cmd.length() : firstSpace;
                    return cmd.substring(0, firstSpace);
                }).orElse("");
    }
}
