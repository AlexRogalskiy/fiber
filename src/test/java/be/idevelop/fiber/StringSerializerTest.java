package be.idevelop.fiber;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StringSerializerTest {

    @BeforeClass
    @AfterClass
    public static void setUpOnce() {
        System.gc();
    }

    @Test
    public void testSerializeString() {
        Map<String, String> strings = new HashMap<String, String>();
        strings.put("test", "123");
        strings.put("123", "test");
        Fiber fiber = new Fiber();
        assertEquals(strings, fiber.deserialize(fiber.serialize(strings)));
    }

}
