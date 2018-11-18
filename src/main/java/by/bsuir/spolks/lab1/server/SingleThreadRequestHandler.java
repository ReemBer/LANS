package by.bsuir.spolks.lab1.server;

import by.bsuir.spolks.common.RequestHandler;
import by.bsuir.spolks.common.command.Command;
import by.bsuir.spolks.common.command.context.CommandContext;
import by.bsuir.spolks.common.command.util.CommandUtils;
import by.bsuir.spolks.common.exception.command.CommandException;
import by.bsuir.spolks.common.exception.command.CommandNotFoundException;
import lombok.NoArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

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
            clientSocket.setKeepAlive(true);
            clientSocket.setOOBInline(true); // Out Of Band data will send together with simple data
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
                    System.out.println("Waiting for request...");
                    String request = dataIn.readLine();
                    if (request == null) {
                        break;
                    }
                    System.out.println(String.format("Received : %s", request));
                    System.out.println("Parsing Command name...");
                    String commandName = CommandUtils.parseName(request);
                    Command command = Optional.ofNullable(COMMAND_MAPPING.get(commandName))
                            .orElseThrow(() -> new CommandNotFoundException(commandName));
                    System.out.println(String.format("Validating %s...", command));
                    command.getValidator().validate(request);
                    context.setCommand(request);
                    System.out.println(String.format("Parsing parameters %s...", command));
                    context.setParams(command.getParser().parse(request));
                    System.out.println(String.format("Executing command '%s'", command));
                    command.getHandler().handle(context);
                } catch (CommandException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            clientSocket.close();
        }
    }
}
