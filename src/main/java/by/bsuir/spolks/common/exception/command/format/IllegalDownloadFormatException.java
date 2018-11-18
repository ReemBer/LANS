package by.bsuir.spolks.common.exception.command.format;

import static by.bsuir.spolks.common.command.CommandNames.DOWNLOAD;
import static by.bsuir.spolks.common.command.validator.impl.DownloadUploadCommandValidator.VALID_DOWNLOAD_FORMAT;

/**
 * @author v2.tarasevich
 * @since 13.10.18 20:26
 */
public class IllegalDownloadFormatException extends IllegalCommandFormatException {

    public IllegalDownloadFormatException() {
        super(DOWNLOAD, VALID_DOWNLOAD_FORMAT);
    }
}
