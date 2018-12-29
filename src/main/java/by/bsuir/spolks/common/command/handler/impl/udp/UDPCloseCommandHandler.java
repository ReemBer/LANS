package by.bsuir.spolks.common.command.handler.impl.udp;

import by.bsuir.spolks.common.command.context.UDPCommandContext;
import by.bsuir.spolks.common.command.handler.CommandHandler;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 30.11.2018 21:59
 */
public class CloseCommandHandler implements CommandHandler<UDPCommandContext> {
    @Override
    public void handle(UDPCommandContext context) {
        context.getSocket().close();
    }
}
