package by.bsuir.spolks.lab1.server;

import by.bsuir.spolks.common.RequestHandler;
import by.bsuir.spolks.common.command.Command;
import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.util.CommandUtil;
import by.bsuir.spolks.common.exception.command.validation.CommandValidationException;
import lombok.NoArgsConstructor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static by.bsuir.spolks.common.command.Command.COMMAND_MAPPING;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 1:12
 */
@NoArgsConstructor
public class SingleThreadRequestHandler extends RequestHandler {

    private CommandContext context = new CommandContext();

    public void initAndStartDialog() {
        try {
            startDialog();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void startDialog() throws IOException {
        try(DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {
            context.setClientSocket(clientSocket);
            while (!clientSocket.isClosed()) {
                try {
                    String request = in.readUTF();
                    Command command = COMMAND_MAPPING.get(CommandUtil.parseName(request));
                    command.getValidator().validate(request);
                } catch (CommandValidationException e) {
                    out.write(e.getMessage().getBytes());
                }
            }
        }
    }
}
