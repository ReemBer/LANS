package by.bsuir.spolks.common;

import by.bsuir.spolks.common.exception.ServerException;

import java.net.SocketException;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 22.09.2018 1:05
 */
public interface SpolksServer {
    void start() throws ServerException;
}
