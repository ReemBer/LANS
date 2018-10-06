package by.bsuir.spolks.lab1.command;

import by.bsuir.spolks.common.command.component.CommandComponents;
import by.bsuir.spolks.common.command.config.CommandConfigurer;
import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.executor.CommandExecutor;
import by.bsuir.spolks.common.dto.request.ClientRequest;
import by.bsuir.spolks.common.dto.response.ServerResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static by.bsuir.spolks.common.command.config.CommandConfigurer.COMMANDS;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 01.10.2018 14:21
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SingleThreadCommandExecutor implements CommandExecutor {

    private static SingleThreadCommandExecutor instance = null;

    public static SingleThreadCommandExecutor getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (SingleThreadCommandExecutor.class) {
                if (Objects.isNull(instance)) {
                    instance = new SingleThreadCommandExecutor();
                }
            }
        }
        return instance;
    }

    public ServerResponse exequte(CommandContext context) {
        CommandComponents components = context.getCommandComponents();
        return COMMANDS.get(components.getName()).apply();
    }
}
