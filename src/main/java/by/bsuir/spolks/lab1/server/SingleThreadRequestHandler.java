package by.bsuir.spolks.lab1.server;

import by.bsuir.spolks.common.RequestHandler;
import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.executor.CommandExecutor;
import by.bsuir.spolks.common.command.parser.CommandParser;
import by.bsuir.spolks.lab1.command.SingleThreadCommandExecutor;
import lombok.NoArgsConstructor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 1:12
 */
@NoArgsConstructor
public class SingleThreadRequestHandler extends RequestHandler {

    private CommandExecutor executor = SingleThreadCommandExecutor.getInstance();
    private CommandContext context = new CommandContext();

    public void start() {
        try {
            startDialog();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void startDialog() throws IOException {
        try(DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {
            context.setClientOutputStream(clientSocket.getOutputStream());
            context.setClientInputStream(clientSocket.getInputStream());
            while (!clientSocket.isClosed()) {
                String command = in.readUTF();
                context.setCommandComponents(CommandParser.parse(command));
                executor.exequte(context);
            }
        }
    }
}
