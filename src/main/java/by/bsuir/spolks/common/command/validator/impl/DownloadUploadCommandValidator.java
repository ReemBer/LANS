package by.bsuir.spolks.common.command.validator.impl;

import by.bsuir.spolks.common.command.validator.CommandValidator;
import by.bsuir.spolks.common.exception.command.CommandValidationException;
import by.bsuir.spolks.common.exception.command.format.IllegalDownloadFormatException;

import static by.bsuir.spolks.common.command.CommandNames.DOWNLOAD;
import static by.bsuir.spolks.common.command.CommandNames.UPLOAD;

/**
 * @author v2.tarasevich
 * @since 13.10.18 20:18
 */
public class DownloadUploadCommandValidator extends CommandValidator {

    public static final String VALID_DOWNLOAD_FORMAT = "download [path to file]";

    private static final String VALID_DOWNLOAD_UPLOAD_FORMAT_REGEX = String.join(
            "",
            S_OPTIONAL,
            "(" + DOWNLOAD + "|" + UPLOAD + ")",
            S_NECESSARY,
            STRING_LITERAL_REGEX,
            S_OPTIONAL + "(--bufsize=" + INTEGER_LITERAL_REGEX + ")?"
    );

    @Override
    public void validate(String command) throws CommandValidationException {
        validateCommandFormat(command, VALID_DOWNLOAD_UPLOAD_FORMAT_REGEX, new IllegalDownloadFormatException());
    }
}
