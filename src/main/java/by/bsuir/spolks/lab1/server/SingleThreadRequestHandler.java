package by.bsuir.spolks.lab1.server;

import by.bsuir.spolks.common.RequestHandler;
import by.bsuir.spolks.common.command.Command;
import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.util.CommandUtils;
import by.bsuir.spolks.common.exception.command.execution.CommandExecutionException;
import by.bsuir.spolks.common.exception.command.parsing.CommandParsingException;
import by.bsuir.spolks.common.exception.command.validation.CommandValidationException;
import lombok.NoArgsConstructor;

import java.io.*;
import java.net.Socket;

import static by.bsuir.spolks.common.command.util.CommandUtils.COMMAND_MAPPING;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 1:12
 */
@NoArgsConstructor
public class SingleThreadRequestHandler extends RequestHandler {

    private CommandContext context = new CommandContext();

    @Override
    public void initAndStartDialog(Socket clientSocket) {
        super.initAndStartDialog(clientSocket);
        try {
            startDialog();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void startDialog() throws IOException {
        try(OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream()) {
            context.setClientSocket(clientSocket);
            context.setSocketInputStream(in);
            context.setSocketOutputStream(out);
            BufferedReader dataIn = new BufferedReader(new InputStreamReader(in));
            while (!clientSocket.isClosed()) {
                try {
                    String request = dataIn.readLine();
                    Command  command = COMMAND_MAPPING.get(CommandUtils.parseName(request));
                    command.getValidator().validate(request);
                    context.setCommand(request);
                    context.setParams(command.getParser().parse(request));
                    command.getHandler().handle(context);
                } catch (CommandValidationException | CommandParsingException | CommandExecutionException e) {
                    out.write(e.getMessage().getBytes());
                }
            }
        } finally {
            clientSocket.close();
        }
    }
}
