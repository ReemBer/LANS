package by.bsuir.spolks.common.command.context;

import by.bsuir.spolks.common.command.params.CommandParams;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 06.10.2018 1:54
 */
@Getter
@Setter
public class CommandContext {

    private Socket clientSocket;
    private InputStream socketInputStream;
    private OutputStream socketOutputStream;
    private String command;
    private CommandParams params;

    public Object getParam(int index) {
        return params.getParam(index);
    }

    public Object getParam(String key) {
        return params.getParam(key);
    }
}
