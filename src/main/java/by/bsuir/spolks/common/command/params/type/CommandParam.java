package by.bsuir.spolks.common.command.params.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author v2.tarasevich
 * @since 12.10.18 19:44
 */
@Getter
@RequiredArgsConstructor
public class CommandParam<R> {

    private final String paramName;
    private final R value;
}
