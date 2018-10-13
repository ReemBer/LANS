package by.bsuir.spolks.common.command.validator.impl;

import by.bsuir.spolks.common.command.validator.CommandValidator;
import by.bsuir.spolks.common.exception.command.CommandValidationException;
import by.bsuir.spolks.common.exception.command.format.IllegalDownloadFormatException;

import static by.bsuir.spolks.common.command.CommandNames.DOWNLOAD;

/**
 * @author v2.tarasevich
 * @since 13.10.18 20:18
 */
public class DownloadCommandValidator extends CommandValidator {

    public static final String VALID_DOWNLOAD_FORMAT = "download [path to file]";

    private static final String VALID_DOWNLOAD_FORMAN_REGEX = String.join(
            "",
            S_OPTIONAL,
            DOWNLOAD,
            S_NECESSARY,
            STRING_LITERAL_REGEX,
            S_OPTIONAL
    );

    @Override
    public void validate(String command) throws CommandValidationException {
        validateCommandFormat(command, VALID_DOWNLOAD_FORMAN_REGEX, new IllegalDownloadFormatException());
    }
}
