package be.idevelop.fiber;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StringSerializerTest {

    @Test
    public void testSerializeString() {
        Map<String, String> strings = new HashMap<String, String>();
        strings.put("test", "123");
        strings.put("123", "test");
        Fiber fiber = new Fiber();
        assertEquals(strings, fiber.deserialize(fiber.serialize(strings)));
    }

}
