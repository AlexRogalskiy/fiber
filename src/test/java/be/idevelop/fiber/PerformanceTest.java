package be.idevelop.fiber;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.nio.ByteBuffer;

public class PerformanceTest {

    private static final int WARM_UP = 1000;

    private static final int ITERATIONS = 1000;

    private static final int REPEAT = 1000;

    private static Fiber fiber;

    private static Object o;

    private static SimpleObject simpleObject;

    private static ComplexClassWithDefaultConstructorAndReferences complexObject;

    @BeforeClass
    public static void setUpPerformanceTest() {
        System.out.println(String.format("%10s\t%8s\t%8s\t%8s\t%11s", "Name", "Min", "Avg", "Max", "Total"));
        System.gc();

        fiber = new Fiber();
        fiber.register(SimpleObject.class);
        fiber.register(ComplexClassWithDefaultConstructorAndReferences.class);
    }

    @Before
    public void setUp() {
        o = new Object();
        simpleObject = new SimpleObject(15);
        complexObject = ComplexClassWithDefaultConstructorAndReferences.createNewInstance();

        warmUp(o);
        warmUp(simpleObject);
        warmUp(complexObject);

    }

    @After
    public void cleanUp() {
        System.gc();
    }

    @org.junit.Test
    public void testConstructor() {
        new Test().execute(new ConstructorTest(), "construct", REPEAT);
    }

    @org.junit.Test
    public void testSimpleObjectSerialize() {
        new Test().execute(new SimpleObjectSerializeTest(), "simple S.", REPEAT);
    }

    @org.junit.Test
    public void testSimpleObjectDeserialize() {
        new Test().execute(new SimpleObjectDeserializeTest(), "simple D.", REPEAT);
    }

    @org.junit.Test
    public void testComplexObjectSerialize() {
        new Test().execute(new ComplexObjectSerializeTest(), "complex S.", REPEAT);
    }

    @org.junit.Test
    public void testComplexObjectDeserialize() {
        new Test().execute(new ComplexObjectDeserializeTest(), "complex D.", REPEAT);
    }

    private void warmUp(Object o) {
        for (int i = 0; i < WARM_UP; i++) {
            fiber.deserialize(fiber.serialize(o));
        }
    }

    static class Test {

        private long totalInNano = 0;

        private long minInNano = Long.MAX_VALUE;

        private long maxInNano = Long.MIN_VALUE;

        void execute(TestCase testCase, String name, int repeat) {
            System.gc();

            for (int i = 0; i < repeat; i++) {
                testCase.execute();
                totalInNano += testCase.getTotalInNano();
                minInNano = Math.min(minInNano, testCase.getMinInNano());
                maxInNano = Math.max(maxInNano, testCase.getMaxInNano());
                testCase.resetTime();
            }

            System.out.println(String.format("%10s\t%8d\t%8d\t%8d\t%11d", name, minInNano, totalInNano / (testCase.iterations * repeat), maxInNano, totalInNano));
        }
    }

    static abstract class TestCase {

        private long totalInNano = 0;

        private int iterations;

        private long minInNano = Long.MAX_VALUE;

        private long maxInNano = Long.MIN_VALUE;

        protected TestCase(int iterations) {
            this.iterations = iterations;
        }

        public void execute() {
            long start = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                test();
            }
            this.totalInNano = System.nanoTime() - start;
            long timePerIterationInNano = totalInNano / iterations;
            minInNano = Math.min(minInNano, timePerIterationInNano);
            maxInNano = Math.max(maxInNano, timePerIterationInNano);
        }

        long getTotalInNano() {
            return totalInNano;
        }

        public abstract void test();

        public void resetTime() {
            totalInNano = 0;
        }

        public long getMinInNano() {
            return minInNano;
        }

        public long getMaxInNano() {
            return maxInNano;
        }
    }

    private static class ConstructorTest extends TestCase {

        private final ByteBuffer byteBuffer;

        public ConstructorTest() {
            super(PerformanceTest.ITERATIONS);
            byteBuffer = fiber.serialize(o);
        }

        @Override
        public void test() {
            fiber.deserialize(byteBuffer);
        }
    }

    private static class SimpleObjectSerializeTest extends TestCase {

        public SimpleObjectSerializeTest() {
            super(PerformanceTest.ITERATIONS);
        }

        @Override
        public void test() {
            fiber.serialize(simpleObject);
        }
    }

    private static class SimpleObjectDeserializeTest extends TestCase {

        private final ByteBuffer byteBuffer;

        public SimpleObjectDeserializeTest() {
            super(PerformanceTest.ITERATIONS);
            byteBuffer = fiber.serialize(simpleObject);
        }

        @Override
        public void test() {
            fiber.deserialize(byteBuffer);
        }
    }

    private static class ComplexObjectSerializeTest extends TestCase {

        public ComplexObjectSerializeTest() {
            super(PerformanceTest.ITERATIONS);
        }

        @Override
        public void test() {
            fiber.serialize(complexObject);
        }
    }

    private static class ComplexObjectDeserializeTest extends TestCase {

        private final ByteBuffer byteBuffer;

        public ComplexObjectDeserializeTest() {
            super(PerformanceTest.ITERATIONS);
            byteBuffer = fiber.serialize(complexObject);
        }

        @Override
        public void test() {
            fiber.deserialize(byteBuffer);
        }
    }
}
