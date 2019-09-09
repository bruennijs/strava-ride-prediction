package de.bruenni.rideprediction.identity.infrastructure;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AggregatorTest {

    @Test
    public void serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", "value1");
        map.put("2", "value2");

        Aggregator sut = new Aggregator();

        Assert.assertEquals("1=value1;2=value2;", sut.serialize(map));
    }
}
