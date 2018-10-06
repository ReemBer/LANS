package by.bsuir.spolks.common.command.util;

import com.sun.istack.internal.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author v2.tarasevich
 * @since 6.10.18 22:51
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandUtil {

    public static String parseName(@NotNull String command) {
        String trimmedCommand = command.trim();
        return trimmedCommand.substring(0, trimmedCommand.indexOf(' ') + 1);
    }
}
