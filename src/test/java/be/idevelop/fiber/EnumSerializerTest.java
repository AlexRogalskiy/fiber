package be.idevelop.fiber;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class EnumSerializerTest {

    @Test
    public void testWriteAndRead() {
        Fiber fiber = new Fiber();
        fiber.register(new EnumSerializer<SomeEnum>(SomeEnum.class));

        SomeEnum[] test = new SomeEnum[]{SomeEnum.CONSTANT_2, null, SomeEnum.CONSTANT_1};
        SomeEnum[] result = fiber.deserialize(fiber.serialize(test));
        assertArrayEquals(test, result);
    }

    enum SomeEnum {
        CONSTANT_1, CONSTANT_2
    }
}
