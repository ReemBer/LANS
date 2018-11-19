package by.bsuir.spolks.common.command.validator.impl;

import by.bsuir.spolks.common.command.validator.CommandValidator;
import by.bsuir.spolks.common.exception.command.CommandValidationException;
import by.bsuir.spolks.common.exception.command.format.IllegalDownloadFormatException;

import static by.bsuir.spolks.common.command.CommandNames.CONTINUE_DOWNLOAD;
import static by.bsuir.spolks.common.command.CommandNames.DOWNLOAD;
import static by.bsuir.spolks.common.command.CommandNames.UPLOAD;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 19.11.2018 0:57
 */
public class ContinueDownloadCommandValidator extends CommandValidator {
    private static final String VALID_CONTINUE_DOWNLOAD_FORMAT_REGEX = String.join(
            "",
            S_OPTIONAL,
            "(" + CONTINUE_DOWNLOAD + ")",
            S_NECESSARY,
            STRING_LITERAL_REGEX,
            S_NECESSARY + "(--bufsize=" + INTEGER_LITERAL_REGEX + ")?",
            S_NECESSARY + "--downloadedSize=" + INTEGER_LITERAL_REGEX
    );

    @Override
    public void validate(String command) throws CommandValidationException {
        validateCommandFormat(command, VALID_CONTINUE_DOWNLOAD_FORMAT_REGEX, new IllegalDownloadFormatException());
    }
}
