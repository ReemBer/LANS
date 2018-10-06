package by.bsuir.spolks.common.dto.request;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 22.09.2018 1:32
 */
public interface ClientRequest {

    void parse(String command);
    String getCommandName();
    RequestParameters getCommandParameters();
    String toString();
}
