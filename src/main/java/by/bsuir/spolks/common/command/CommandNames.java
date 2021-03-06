package by.bsuir.spolks.common.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author v2.tarasevich
 * @since 12.10.18 23:49
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandNames {
    public static final String EMPTY = "";
    public static final String ECHO = "echo";
    public static final String TIME = "time";
    public static final String CLOSE = "close";
    public static final String DOWNLOAD = "download";
    public static final String UPLOAD = "upload";
    public static final String CONTINUE_DOWNLOAD = "continueDownload";
}
