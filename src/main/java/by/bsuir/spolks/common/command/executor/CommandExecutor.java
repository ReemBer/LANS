package by.bsuir.spolks.common.command.executor;

import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.dto.response.ServerResponse;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 22.09.2018 1:19
 */
public interface CommandExecutor {

    public ServerResponse exequte(CommandContext context);
}
