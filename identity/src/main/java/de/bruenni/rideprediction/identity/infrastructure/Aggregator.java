package de.bruenni.rideprediction.identity.infrastructure;

import java.util.Map;

/**
 *
 *
 * @author Oliver Brüntje
 */
public class Aggregator {
    public String serialize(Map<String, Object> map) {
        return map.entrySet().stream()
                .reduce("", (aggregate, entry) -> aggregate + serilizeEntry(entry) + ";", (agg1, agg2) -> agg1 + "" + agg2);
    }

    private String serilizeEntry(Map.Entry<String, Object> entry) {
        return entry.getKey() + "=" + entry.getValue();
    }
}
