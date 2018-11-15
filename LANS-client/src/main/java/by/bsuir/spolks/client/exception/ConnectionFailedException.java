package by.bsuir.spolks.client.exception;

/**
 * @author v2.tarasevich
 * @since 14.11.18 12:31
 */
public class ConnectionFailedException extends Exception {
    public ConnectionFailedException() {
        super("Connection with server didn't established.");
    }
}
