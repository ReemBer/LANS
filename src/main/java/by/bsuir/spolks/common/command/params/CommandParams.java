package by.bsuir.spolks.common.command.params;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.util.Pair;

import java.util.*;

/**
 * @author v.tarasevich
 * @version 1.0
 * @since 25.09.2018 23:57
 */
public class CommandParams {

    private Map<String, Object> namedParams = Maps.newLinkedHashMap();
    private List<Object> orderedParams = Lists.newArrayList();

    public CommandParams(List<Pair<String, Object>> parameters) {
        parameters.forEach(
                parameter -> {
                    namedParams.put(
                            parameter.getKey(),
                            parameter.getValue()
                    );
                    orderedParams.add(parameter.getValue());
                }
        );
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
