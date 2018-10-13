package by.bsuir.spolks.common.exception.command.parsing;

import by.bsuir.spolks.common.exception.command.CommandParsingException;

/**
 * @author v2.tarasevich
 * @since 13.10.18 20:51
 */
public class DownloadParsingException extends CommandParsingException {
    public DownloadParsingException() {
        super("Can't parse file path.");
    }
}
