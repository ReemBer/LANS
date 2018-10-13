package by.bsuir.spolks.common.command.params;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 25.09.2018 23:57
 */
public class CommandParams {

    private Map<String, Object> namedParams = Maps.newLinkedHashMap();
    private List<Object> orderedParams = Lists.newArrayList();

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

    public CommandParams() {
        namedParams = Collections.emptyMap();
    }

    public Object getParam(String key) {
        return namedParams.get(key);
    }

    public Object getParam(int index) {
        return orderedParams.get(index);
    }
}
