package be.idevelop.fiber;

import org.junit.BeforeClass;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class ReflectionTest {

    private static final long ITERATIONS = 1000000000;

    private static Unsafe unsafe;

    private static int addressSize;

    private static long baseOffset;

    @BeforeClass
    public static void setUpOnce() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        unsafe = (Unsafe) field.get(null);

        baseOffset = unsafe.arrayBaseOffset(Object[].class);
        addressSize = unsafe.addressSize();
    }

    public static long addressOf(Object o) throws Exception {
        Object[] array = new Object[]{o};
        switch (addressSize) {
            case 4:
                return unsafe.getInt(array, baseOffset);
            case 8:
                return unsafe.getLong(array, baseOffset);
        }
        return 0;
    }

    @Test
    public void measureAddressLookup() throws Exception {
        doAddressLookup(1000);

        long start = System.nanoTime();
        doAddressLookup(ITERATIONS);
        long duration = System.nanoTime() - start;
        System.out.println("Address lookup");
        System.out.println("avg: " + duration / (3L * ITERATIONS));
    }

    @Test
    public void measureIdentityHashCode() {
        doIdentityHashCode(1000);

        long start = System.nanoTime();
        doIdentityHashCode(ITERATIONS);
        long duration = System.nanoTime() - start;
        System.out.println("Identity hash code");
        System.out.println("avg: " + duration / (3L * ITERATIONS));
    }

    private void doAddressLookup(long iterations) throws Exception {
        SimpleObject simpleObject_1 = new SimpleObject(1);
        SimpleObject simpleObject_2 = new SimpleObject(2);

        long address_1;
        long address_2;
        long address_3;
        for (int i = 0; i < iterations; i++) {
            address_1 = addressOf(simpleObject_1);
            address_2 = addressOf(simpleObject_2);
            address_3 = addressOf(simpleObject_1);
            if (address_1 == address_2 || address_1 != address_3) {
                System.out.println("Address 1: " + address_1);
                System.out.println("Address 2: " + address_2);
                System.out.println("Address 3: " + address_3);
            }
        }
    }

    private void doIdentityHashCode(long iterations) {
        SimpleObject simpleObject_1 = new SimpleObject(1);
        SimpleObject simpleObject_2 = new SimpleObject(2);

        for (int i = 0; i < iterations; i++) {
            int i1 = System.identityHashCode(simpleObject_1);
            int i2 = System.identityHashCode(simpleObject_2);
            int i3 = System.identityHashCode(simpleObject_1);
            if (i1 == i2 || i1 != i3) {
                System.out.println("Id 1: " + i1);
                System.out.println("Id 2: " + i2);
                System.out.println("Id 3: " + i3);
            }
        }
    }
}
