package by.bsuir.spolks.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.Socket;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 05.10.2018 1:09
 */
@NoArgsConstructor
public abstract class RequestHandler {

    @Getter protected Socket clientSocket;

    public void initAndStartDialog(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
