package by.bsuir.spolks.common.dto.response;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 22.09.2018 1:31
 */
public interface ServerResponse {

    void parse(String response);
    void setResponseStatus(ResponseStatus status);
    void setResponseParameters();
    String toString();
}
