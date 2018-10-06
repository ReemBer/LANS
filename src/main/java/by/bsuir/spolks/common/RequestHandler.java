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

    @Getter @Setter protected Socket clientSocket;

    public abstract void start();
}
