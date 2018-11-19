package by.bsuir.spolks.common.command.params;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.util.Pair;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 25.09.2018 23:57
 */
@NoArgsConstructor
public class CommandParams {

    public static final String NAMED_PARAM_FILE_PATH = "filePath";
    public static final String NAMED_PARAM_BUFFER_SIZE = "bufsize";
    public static final String NAMED_PARAM_DOWNLOADED_SIZE = "downloadedSize";

    private Map<String, Object> namedParams = new LinkedHashMap<>();
    private List<Object> orderedParams = new ArrayList<>();

    public CommandParams(List<Object> parameters) {
        orderedParams.addAll(parameters);
        IntStream
                .range(0, parameters.size())
                .forEach(i -> namedParams
                                .put(
                                        String.valueOf(i),
                                        orderedParams.get(i)
                                )
                );
    }

    public CommandParams(Object parameter) {
        orderedParams.add(parameter);
        namedParams.put("0", parameter);
    }

    public Object getParam(String key) {
        return namedParams.get(key);
    }

    public Object getParam(int index) {
        return orderedParams.get(index);
    }

    public void addNamedParam(String key, Object value) {
        namedParams.put(key, value);
    }
}
