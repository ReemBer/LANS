package by.bsuir.spolks.common.command.context;

import by.bsuir.spolks.common.command.component.CommandComponents;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 06.10.2018 1:54
 */
@Getter
@Setter
public class CommandContext {
    private OutputStream clientOutputStream;
    private InputStream clientInputStream;
    private CommandComponents commandComponents;
}
